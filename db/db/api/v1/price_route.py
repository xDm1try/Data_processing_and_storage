from fastapi import APIRouter
from fastapi import Depends
from sqlalchemy.orm import Session
from starlette import status

from db.database.repository.prices_repository import get_saved_prices, collect_prices
from db.database.session import get_database
from db.schemas.price_schema import PriceShow, PriceShowExtend

router = APIRouter()


@router.post("/collect_prices", response_model=None, status_code=status.HTTP_200_OK)
def collect_prices_route(db: Session = Depends(get_database)):
    collect_prices(db=db)


@router.get("/get_saved_prices", response_model=list[PriceShow | PriceShowExtend], status_code=status.HTTP_200_OK)
def get_prices(db: Session = Depends(get_database)):
    prices = get_saved_prices(db)
    result = []
    for price in prices:
        if price.max_amount is not None:
            result.append(PriceShowExtend(flight_no=price.flight_no, fare_condition=price.fare_condition,
                                          min_amount=price.min_amount, max_amount=price.max_amount))
        else:
            result.append(
                PriceShow(flight_no=price.flight_no, fare_condition=price.fare_condition, min_amount=price.min_amount))

    return result
