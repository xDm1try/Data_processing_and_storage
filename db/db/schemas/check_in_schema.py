from pydantic import BaseModel


class CheckInSchema(BaseModel):
    booking_id: str
    flight_id: int
