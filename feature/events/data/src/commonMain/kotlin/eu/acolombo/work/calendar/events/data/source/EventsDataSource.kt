package eu.acolombo.work.calendar.events.data.source

import eu.acolombo.work.calendar.events.data.model.Event
import kotlinx.datetime.LocalDate

interface EventsDataSource {
    suspend fun getEvents(date: LocalDate): List<Event>
}
