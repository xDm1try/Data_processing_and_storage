from sqlalchemy import Column, String, Numeric

from db.database.base_model import Base


class FlightPriceModel(Base):
    __tablename__ = "prices"

    flight_no = Column(String, primary_key=True)
    fare_condition = Column(String)
    minAmount = Column(Numeric)
    maxAmount = Column(Numeric)
