import uvicorn
from fastapi import FastAPI

from db.api.base import api_router
from db.core.settings import settings
from db.database.session import engine
from db.database.base import Base, FlightPriceModel


def include_router(app):
    app.include_router(api_router)


def prepare_db():
    Base.metadata.drop_all(engine, tables=[FlightPriceModel.__table__])
    Base.metadata.create_all(bind=engine)


def run_app():
    app = FastAPI(title=settings.SERVICE_NAME, version=settings.SERVICE_VERSION)
    prepare_db()
    include_router(app)
    return app


app = run_app()


@app.get("/")
def hello_api():
    return {"msg": "Hello world!"}

def execute_application():
    uvicorn.run(app)

if __name__ == "__main__":
    execute_application()
