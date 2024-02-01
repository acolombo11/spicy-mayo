package eu.acolombo.work.calendar.events.screen

import eu.acolombo.work.calendar.events.screen.extensions.now
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

sealed class EventsFilter(val date: LocalDate) {
    data object Today : EventsFilter(
        date = LocalDate.now(),
    )

    data object Tomorrow : EventsFilter(
        date = LocalDate.now().plus(1, DateTimeUnit.DAY),
    )

    class Date(date: LocalDate) : EventsFilter(
        date = date,
    )
}
