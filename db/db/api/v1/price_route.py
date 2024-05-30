from typing import List

from fastapi import APIRouter

from sqlalchemy.orm import Session
from fastapi import Depends
from starlette import status

from db.schemas.price_schema import PriceShow, PriceSchema
from db.database.models.flight_price import FlightPriceModel
from db.database.session import get_database
from db.database.repository.prices_repository import get_prices

router = APIRouter()


@router.post("/get_prices", response_model=list[PriceShow], status_code=status.HTTP_200_OK)
def create_worker(db: Session = Depends(get_database)):
    prices = get_prices(db=db)
    return prices
