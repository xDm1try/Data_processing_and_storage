from fastapi import APIRouter
from fastapi import Depends
from sqlalchemy.orm import Session
from starlette import status

from db.database.session import get_database
from db.database.repository.city_repository import get_airports
from db.database.repository.airport_repository import get_all_airports, get_inbound_shedules, get_outbound_shedules
from db.schemas.airport_schema import AirportSchema
from db.schemas.shedules.inbound_shema import InboundSchema
from db.schemas.shedules.outbound_shema import OutboundSchema

router = APIRouter()


@router.get("/{city}/airports", response_model=list[AirportSchema], status_code=status.HTTP_200_OK)
def get_prices(city: str, db: Session = Depends(get_database)):
    prices = get_airports(city, db)
    return prices


@router.get("/all_airports", response_model=list[str], status_code=status.HTTP_200_OK)
def get_all_airports_route(db: Session = Depends(get_database)):
    airports = get_all_airports(db)
    return airports


@router.get("/inbound/{airport}", response_model=list[InboundSchema], status_code=status.HTTP_200_OK)
def inbound_schedules_route(airport: str, db: Session = Depends(get_database)):
    schemas = get_inbound_shedules(airport, db)
    return schemas


@router.get("/outbound/{airport}", response_model=list[OutboundSchema], status_code=status.HTTP_200_OK)
def outbound_schedules_route(airport: str, db: Session = Depends(get_database)):
    schemas = get_outbound_shedules(airport, db)
    return schemas
