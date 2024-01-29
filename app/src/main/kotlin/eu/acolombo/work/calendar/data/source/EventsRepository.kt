package eu.acolombo.work.calendar.data.source

import eu.acolombo.work.calendar.data.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface EventsRepository {
    fun getEvents(date: LocalDate): Flow<List<Event>>
}
