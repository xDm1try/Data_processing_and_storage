from fastapi import APIRouter
from fastapi import Depends
from sqlalchemy.orm import Session
from starlette import status

from db.database.session import get_database

router = APIRouter()


@router.get("/{city}/airports", response_model=list[str], status_code=status.HTTP_200_OK)
def get_prices(city: str, db: Session = Depends(get_database)):
    # prices = get_saved_prices(db)
    # return prices
    ...
