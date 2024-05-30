from typing import Any
from sqlalchemy.ext.declarative import declared_attr
from sqlalchemy.orm import as_declarative


@as_declarative()
class Base:
    __name__: str

    @declared_attr
    def __table_name__(cls) -> str:
        return cls.__name__.lower()
