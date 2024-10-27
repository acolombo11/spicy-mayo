package eu.acolombo.work.calendar.events.data.source

import eu.acolombo.work.calendar.events.data.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface EventsRepository {
    fun getEvents(date: LocalDate): Flow<List<Event>>
}
