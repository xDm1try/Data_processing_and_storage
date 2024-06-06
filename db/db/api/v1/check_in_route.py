from fastapi import APIRouter, Depends

from db.database.session import Session, get_database
from db.schemas.check_in_schema import CheckInSchema
from db.database.repository.check_in_repository import check_in

router = APIRouter()

@router.post("/check_in")
def check_in_router(check_in_schema: CheckInSchema, db: Session = Depends(get_database)):
    return check_in(check_in_schema, db)