from sqlalchemy.orm import Session
from sqlalchemy import text


def get_prices(db: Session):
    prices = db.execute(text("""SELECT DISTINCT f.flight_no as flight_no, tf.fare_conditions as fare_condition, min(tf.amount) as min_amount, max(tf.amount) as max_amount
FROM flights as f
JOIN bookings.ticket_flights tf on f.flight_id = tf.flight_id
GROUP BY f.flight_no, tf.fare_conditions
ORDER BY flight_no"""))
    return prices
