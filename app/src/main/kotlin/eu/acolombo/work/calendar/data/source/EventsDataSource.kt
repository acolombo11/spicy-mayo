package eu.acolombo.work.calendar.data.source

import eu.acolombo.work.calendar.data.model.Event
import kotlinx.datetime.LocalDate

interface EventsDataSource {
    suspend fun getEvents(date: LocalDate): List<Event>
}
