from typing import List

from fastapi import APIRouter

from sqlalchemy.orm import Session
from fastapi import Depends
from starlette import status

from db.schemas.price_schema import PriceShow, PriceSchema
from db.database.session import get_database
from db.database.repository.prices_repository import get_saved_prices, collect_prices

router = APIRouter()


@router.post("/collect_prices", response_model=None, status_code=status.HTTP_200_OK)
def collect_prices_route(db: Session = Depends(get_database)):
    collect_prices(db=db)

@router.get("/get_saved_prices", response_model=list[PriceShow], status_code=status.HTTP_200_OK)
def get_prices(db: Session = Depends(get_database)):
    prices = get_saved_prices(db)
    return prices

