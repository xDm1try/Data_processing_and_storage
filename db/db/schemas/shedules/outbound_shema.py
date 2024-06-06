from pydantic import BaseModel


class OutboundSchema(BaseModel):
    days_of_week: list[str]
    departure_time: str
    flight_no: str
    destination: str
