package feature.layers.data.source

import feature.layers.data.model.Event
import kotlinx.datetime.LocalDate

interface EventsDataSource {
    suspend fun getEvents(date: LocalDate): List<Event>
}
