import os
from dotenv import load_dotenv
load_dotenv(".env")

class Settings:
    SERVICE_NAME: str = "flights"
    SERVICE_VERSION: str = "0.0.1"

    POSTGRES_ADMIN: str = os.getenv("POSTGRES_ADMIN")
    POSTGRES_PASSWORD: str = os.getenv("POSTGRES_PASSWORD")
    POSTGRES_SERVER: str = os.getenv("POSTGRES_SERVER")
    POSTGRES_PORT: str = os.getenv("POSTGRES_PORT")
    POSTGRES_DB: str = os.getenv("POSTGRES_DB")
    POSTGRES_URL: str = f"postgresql://{POSTGRES_ADMIN}:\
{POSTGRES_PASSWORD}@{POSTGRES_SERVER}:\
{POSTGRES_PORT}/{POSTGRES_DB}"

settings = Settings()