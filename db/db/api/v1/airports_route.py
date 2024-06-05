from fastapi import APIRouter
from fastapi import Depends
from sqlalchemy.orm import Session
from starlette import status

from db.database.session import get_database
from db.database.repository.city_repository import get_airports
from db.database.repository.airport_repository import get_all_airports
from db.schemas.airport_schema import AirportSchema

router = APIRouter()


@router.get("/{city}/airports", response_model=list[AirportSchema], status_code=status.HTTP_200_OK)
def get_prices(city: str, db: Session = Depends(get_database)):
    prices = get_airports(city, db)
    return prices

@router.get("/all_airports", response_model=list[str], status_code=status.HTTP_200_OK)
def get_all_airports_route(db: Session = Depends(get_database)):
    airports = get_all_airports(db)
    return airports

