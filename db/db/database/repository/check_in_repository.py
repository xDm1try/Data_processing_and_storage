import random

from fastapi import HTTPException
from sqlalchemy import text
from starlette import status

from db.database.models.boarding_pass_nodel import BoardingPassModel
from db.database.repository.utils.booking_ids import BookingIds
from db.database.session import Session
from db.schemas.check_in_schema import CheckInSchema


def save_boarding_pass(boarding_pass_model: BoardingPassModel, db: Session):
    db.add(boarding_pass_model)
    db.commit()


def get_booking_ids_checking_schema(check_in_schema: CheckInSchema, db: Session):
    response = db.execute(text("""SELECT t.ticket_no, tf.fare_conditions, f.aircraft_code
FROM bookings.tickets as t
JOIN bookings.ticket_flights tf on t.ticket_no = tf.ticket_no
JOIN bookings.flights f on f.flight_id = tf.flight_id
WHERE tf.flight_id = :flight_id AND t.book_ref = :book_ref
"""), {"flight_id": check_in_schema.flight_id, "book_ref": check_in_schema.booking_id}).all()
    data = response[0]._data
    return BookingIds(data[0], data[1], data[2])


def get_all_seats(aircraft_code: str, fare_conditions: str, db: Session):
    response = db.execute(text("""SELECT s.seat_no
FROM bookings.seats as s
WHERE s.aircraft_code = :aircraft_code AND s.fare_conditions = :fare_conditions
"""), {"aircraft_code": aircraft_code, "fare_conditions": fare_conditions}).all()
    return response


def get_taken_seats(flight_id: int, db: Session):
    response = db.execute(text(f"""
SELECT bp.seat_no
FROM bookings.boarding_passes as bp
WHERE bp.flight_id = {flight_id}
""")).all()
    return response


def check_in(check_in_schema: CheckInSchema, db: Session):
    booking_id = get_booking_ids_checking_schema(check_in_schema, db)
    all_seats = get_all_seats(booking_id.aircraft_code, booking_id.fare_conditions, db)
    taken_seats = get_taken_seats(check_in_schema.flight_id, db)
    free_seats = list(set(all_seats) - set(taken_seats))
    if len(free_seats) == 0:
        raise HTTPException(
            status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
            detail="Has no free seats"
        )
    boarding_no = random.randint(0, 100)
    boarding_pass_model = BoardingPassModel(flight_id=check_in_schema.flight_id, ticket_no=booking_id.ticket_no,
                                            boarding_no=boarding_no, seat_no=free_seats[0][0])
    save_boarding_pass(boarding_pass_model, db)
    print("Saved \n", boarding_pass_model)
