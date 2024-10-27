package feature.layers.data.source

import feature.layers.data.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface EventsRepository {
    fun getEvents(date: LocalDate): Flow<List<Event>>
}
