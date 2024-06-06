import datetime
import uuid

from fastapi import HTTPException
from sqlalchemy import text
from starlette import status

from db.database.entity.booking import Booking
from db.database.entity.ticket import Ticket
from db.database.models.flight_model import FlightModel
from db.database.models.ticket_flight_model import TicketFlightModel
from db.database.session import Session
from db.schemas.booking_schema import BookingSchema


def get_flight_by_id(flight_id: int, db: Session):
    response = db.execute(text('''SELECT *
FROM bookings.flights as f
WHERE f.flight_id = {}'''.format(flight_id))).all()
    data = response[0]
    return FlightModel(flight_id=data[0],
                       flight_no=data[1],
                       scheduled_departure=data[2],
                       scheduled_arrival=data[3],
                       departure_airport=data[4],
                       arrival_airport=data[5],
                       status=data[6],
                       aircraft_code=data[7],
                       actual_departure=data[8],
                       actual_arrival=data[9]
                       )


def get_prices_by_booking_schema(booking_schema: BookingSchema, db: Session):
    prices = []
    for id in booking_schema.flights_id:
        response = db.execute(text("""SELECT p.amount
FROM bookings.flights as f
JOIN bookings.ticket_flights as p ON f.flight_id = p.flight_id
WHERE f.flight_id = {flight_id} AND p.fare_conditions = {fare_conditions}"""
                                      .format(flight_id=id,
                                              fare_conditions="'" + booking_schema.booking_class + "'"
                                              )),
                                 ).all()
        prices.append(min(response)[0])
    return prices


def save_booking(booking: Booking, db: Session):
    response = db.execute(text("""
INSERT INTO bookings.bookings (book_ref, book_date, total_amount)
VALUES (:book_ref, :book_date, :total_amount)
"""), {"book_ref": booking.book_ref, "book_date": booking.book_date, "total_amount": booking.total_amount})


def save_ticket(ticket: Ticket, db: Session):
    response = db.execute(text("""INSERT INTO bookings.tickets (ticket_no, book_ref, passenger_id, passenger_name, contact_data)
VALUES (:ticket_no, :book_ref, :passenger_id, :passenger_name, null)"""),
                          {"ticket_no": ticket.ticket_no[:13], "book_ref": ticket.book_ref,
                           "passenger_id": ticket.passenger_id, "passenger_name": ticket.passenger_name})


def get_all_seats(aircraft_code: str, fare_conditions: str, db: Session):
    seats = db.execute(text("""SELECT s.seat_no
FROM bookings.seats as s
WHERE s.aircraft_code = :aircraft_code AND s.fare_conditions = :fare_conditions
"""), {"aircraft_code": aircraft_code, "fare_conditions": fare_conditions}).all()
    return seats


def get_taken_seats(flight_id: int, db: Session):
    seats = db.execute(text(f"""SELECT bp.seat_no
FROM bookings.boarding_passes as bp
WHERE bp.flight_id = {flight_id}""")).all()
    return seats


def save_ticket_flight(ticket: TicketFlightModel, db: Session):
    db.add(ticket)
    db.commit()


def create_booking(booking_schema: BookingSchema, db: Session):
    flights = []
    for flight_id in booking_schema.flights_id:
        flights.append(get_flight_by_id(int(flight_id), db))
    prices = get_prices_by_booking_schema(booking_schema, db)
    booking = Booking(str(uuid.uuid4())[:6], datetime.datetime.now(), sum(prices))
    save_booking(booking, db)
    ticket = Ticket(booking.book_ref, str(uuid.uuid4()), booking_schema.passenger, booking_schema.passenger)
    save_ticket(ticket, db)
    print("Saved ticket:\n", ticket)
    for flight, price in zip(flights, prices):
        all_seats = get_all_seats(flight.aircraft_code, booking_schema.booking_class, db)
        taken_seats = get_taken_seats(flight.flight_id, db)
        free_seats = list(set(all_seats) - set(taken_seats))
        if len(free_seats) == 0:
            raise HTTPException(
                status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                detail="Has no free seats"
            )
        ticket_flight_model = TicketFlightModel(ticket_no=ticket.ticket_no[:13], flight_id=flight.flight_id,
                                             fare_conditions=booking_schema.booking_class, amount=price)
        save_ticket_flight(ticket_flight_model, db)
        print("SAVED ticket_flight:\n", ticket_flight_model)
