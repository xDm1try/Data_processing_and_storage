from pydantic import BaseModel

class InboundSchema(BaseModel):
    days_of_week: list[str]
    arrival_time: str
    flight_no: str
    origin: str
