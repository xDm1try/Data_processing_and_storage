from pydantic import BaseModel
from decimal import Decimal

class PriceSchema(BaseModel):
    flight_no: str
    fare_condition: str
    min_amount: Decimal
    max_amount: Decimal

class PriceShow(BaseModel):
    flight_no: str
    fare_condition: str
    min_amount: Decimal
    max_amount: Decimal

    class Config:
        from_attributes = True