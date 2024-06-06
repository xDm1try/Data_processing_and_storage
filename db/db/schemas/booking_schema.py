from pydantic import BaseModel
from decimal import Decimal


class BookingSchema(BaseModel):
    flights_id: list[str]
    passenger: str
    booking_class: str

