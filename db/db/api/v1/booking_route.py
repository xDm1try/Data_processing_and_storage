from fastapi import Depends
from fastapi.routing import APIRouter

from db.database.repository.booking_repository import create_booking
from db.database.session import Session, get_database
from db.schemas.booking_schema import BookingSchema

router = APIRouter()


@router.post("/booking")
def create_booking_route(booking_schema: BookingSchema, db: Session = Depends(get_database)):
    result = create_booking(booking_schema, db)
