

class Ticket:
    def __init__(self, book_ref: str, ticket_no: str, passenger_id: str, passenger_name: str):
        self.book_ref = book_ref
        self.ticket_no = ticket_no
        self.passenger_id = passenger_id
        self.passenger_name = passenger_name

    def __str__(self):
        return f"""
        Ticket
            book_ref = {self.book_ref}
            ticket_no = {self.ticket_no}
            passenger_id = {self.passenger_id}
            passenger_name = {self.passenger_name}
        """