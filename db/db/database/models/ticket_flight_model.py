from sqlalchemy import Column, Integer, String

from db.database.base_model import Base


class TicketFlightModel(Base):
    __tablename__ = "ticket_flights"

    ticket_no = Column(String, primary_key=True)
    flight_id = Column(Integer)
    fare_conditions = Column(String)
    amount = Column(Integer)

    def __str__(self):
        return f"""
        Ticket:
            ticket_no = {self.ticket_no}
            flight_id = {self.flight_id}
            fare_conditions = {self.fare_conditions}
            amount = {self.amount}
        """