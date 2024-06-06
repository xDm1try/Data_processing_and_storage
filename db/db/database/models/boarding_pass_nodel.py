from pydantic import BaseModel
from rich.columns import Columns
from sqlalchemy import String, Integer, Column

from db.database.base_model import Base


class BoardingPassModel(Base):
    __tablename__ = "boarding_passes"

    ticket_no = Column(Integer, primary_key=True)
    flight_id = Column(Integer)
    boarding_no = Column(Integer)
    seat_no = Column(String)

    def __str__(self):
        return f"""
        BoardingPassModel:
            ticket_no = {self.ticket_no}
            flight_id = {self.flight_id}
            boarding_no = {self.boarding_no}
            seat_no = {self.seat_no}
        """
