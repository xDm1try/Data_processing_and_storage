from fastapi import APIRouter, Depends
from starlette import status

from db.database.session import Session, get_database
from db.database.repository.city_repository import get_all_cities

router = APIRouter()

@router.get("/all_cities", response_model=list[str], status_code=status.HTTP_200_OK)
def all_cities_route(db: Session = Depends(get_database)):
    result = get_all_cities(db)
    return result