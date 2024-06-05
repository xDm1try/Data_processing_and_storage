from sqlalchemy import text

from db.database.session import Session


def get_all_airports(db: Session):
    airports = db.execute(text('''SELECT DISTINCT a.airport_name 
FROM airports as a''')).all()
    airports = list(map(lambda tup: tup[0], airports))
    return airports