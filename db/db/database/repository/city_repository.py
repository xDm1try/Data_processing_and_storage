from db.database.models.airport_model import AirportModel
from db.database.session import Session


def get_airports(city: str, db: Session):
    result = db.query(AirportModel).filter(AirportModel.city == city).all()
    return result
