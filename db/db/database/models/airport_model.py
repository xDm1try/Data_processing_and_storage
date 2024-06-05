from sqlalchemy import Column, String, Float

from db.database.base_model import Base


class AirportModel(Base):
    __tablename__ = "airports"

    airport_code = Column(String, primary_key=True)
    airport_name = Column(String)
    city = Column(String)
    coordinates = Column(String)
    timezone = Column(String)
