from typing import Any

from pydantic import BaseModel


class AirportSchema(BaseModel):
    airport_code: str
    airport_name: str
    timezone: str
    coordinates: str

    class Config:
        from_attributes = True

