from datetime import datetime


class Booking:
    def __init__(self, book_ref: str, book_date: datetime, total_amount: int):
        self.book_ref = book_ref
        self.book_date = book_date
        self.total_amount = total_amount
