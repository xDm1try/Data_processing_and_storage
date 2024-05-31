from db.database.session import Session


def get_all_airports_of_city(city: str, db: Session):
    airports = []
    result = db.query()
