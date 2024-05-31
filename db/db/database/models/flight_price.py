import uuid

from sqlalchemy import Column, String, Numeric, Integer, UUID

from db.database.base_model import Base


class FlightPriceModel(Base):
    __tablename__ = "prices"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    flight_no = Column(String)
    fare_condition = Column(String)
    min_amount = Column(Numeric)
    max_amount = Column(Numeric)
