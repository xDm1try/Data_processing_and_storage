from sqlalchemy.orm import Session
from db.database.models.flight_price import FlightPriceModel
from sqlalchemy import text, Result


def collect_prices(db: Session):
    prices = db.execute(text("""SELECT DISTINCT f.flight_no as flight_no, tf.fare_conditions as fare_condition, min(tf.amount) as min_amount, max(tf.amount) as max_amount
FROM flights as f
JOIN bookings.ticket_flights tf on f.flight_id = tf.flight_id
GROUP BY f.flight_no, tf.fare_conditions
ORDER BY flight_no"""))
    _set_prices(prices, db)


def _set_prices(prices: Result, db: Session):
    models = []
    for row in prices.all():
        row_tuple = row._data
        models.append(FlightPriceModel(flight_no=row_tuple[0], fare_condition=row_tuple[1], min_amount=row_tuple[2],
                                       max_amount=row_tuple[3]))
    db.add_all(models)
    db.commit()


def get_saved_prices(db: Session):
    prices = db.query(FlightPriceModel).all()
    print(len(prices))
    for record in prices:
        if record.min_amount == record.max_amount:
            record.max_amount = None

    return prices
