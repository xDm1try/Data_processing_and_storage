import enum


class DayOfWeek(enum.Enum):
    Monday = 1
    Tuesday = 2
    Wednesday = 3
    Thursday = 4
    Friday = 5
    Saturday = 6
    Sunday = 7


def replace_int_2_day(ints: [int]):
    return list(map(lambda numb: DayOfWeek(numb)._name_, ints))