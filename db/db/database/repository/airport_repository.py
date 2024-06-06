from sqlalchemy import text

from db.database.repository.utils.day_of_week import replace_int_2_day
from db.database.session import Session
from db.schemas.shedules.inbound_shema import InboundSchema
from db.schemas.shedules.outbound_shema import OutboundSchema


def get_all_airports(db: Session):
    airports = db.execute(text('''SELECT DISTINCT a.airport_name 
FROM airports as a''')).all()
    airports = list(map(lambda tup: tup[0], airports))
    return airports

def get_inbound_shedules(airport: str, db: Session):
    response = db.execute(text('''SELECT DISTINCT r.days_of_week, f.scheduled_arrival, r.flight_no, r.departure_airport_name
FROM routes as r
LEFT JOIN flights as f 
ON r.flight_no = f.flight_no
WHERE r.arrival_airport_name = {}'''.format("'" + airport + "'"))).all()
    results = []
    for row in response:
        data = list(row._data)
        data[0] = replace_int_2_day(data[0])
        data[1] = data[1].strftime("%d-%b-%Y %H:%M")
        results.append(InboundSchema(days_of_week=data[0], arrival_time=data[1], flight_no=data[2], origin=data[3]))
    return results
def get_outbound_shedules(airport: str, db: Session):
    response = db.execute(text('''SELECT DISTINCT r.days_of_week, f.scheduled_departure, r.flight_no, r.arrival_airport_name
FROM routes as r
LEFT JOIN flights as f 
ON r.flight_no = f.flight_no
WHERE r.departure_airport_name = {}'''.format("'" + airport + "'"))).all()
    results = []
    for row in response:
        data = list(row._data)
        data[0] = replace_int_2_day(data[0])
        data[1] = data[1].strftime("%d-%b-%Y %H:%M")
        results.append(OutboundSchema(days_of_week=data[0], departure_time=data[1], flight_no=data[2], destination=data[3]))
    return results