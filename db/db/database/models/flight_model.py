from pydantic import BaseModel
from sqlalchemy import Column, String, Integer, Any

from db.database.base_model import Base


class FlightModel(Base):
    __tablename__ = "flights"

    flight_id = Column(Integer, primary_key=True)
    flight_no = Column(String)
    scheduled_departure = Column(String)
    scheduled_arrival = Column(String)
    departure_airport = Column(String)
    arrival_airport = Column(String)
    status = Column(String)
    aircraft_code = Column(String)
    actual_departure = Column(String)
    actual_arrival = Column(String)
