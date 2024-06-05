from sqlalchemy import text

from db.database.models.airport_model import AirportModel
from db.database.session import Session


def get_airports(city: str, db: Session):
    result = db.query(AirportModel).filter(AirportModel.city == city).all()
    return result

def get_all_cities(db: Session):
    cities = db.execute(text('''SELECT DISTINCT a.city
FROM airports as a''')).all()
    cities = list(map(lambda tup: tup[0], cities))
    return cities
