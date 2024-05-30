from fastapi import APIRouter
from .v1 import price_route

api_router = APIRouter()
api_router.include_router(price_route.router, prefix="", tags=["price"])
