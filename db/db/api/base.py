from fastapi import APIRouter
from .v1 import price_route, airports_route, cities_route

api_router = APIRouter()
api_router.include_router(price_route.router, prefix="", tags=["price"])
api_router.include_router(airports_route.router, prefix="", tags=["airports"])
api_router.include_router(cities_route.router, prefix="", tags=["cities"])
