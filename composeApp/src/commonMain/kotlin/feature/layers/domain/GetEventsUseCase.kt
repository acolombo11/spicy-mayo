package feature.layers.domain

import feature.layers.data.source.EventsRepository
import feature.layers.domain.model.Event
import feature.layers.domain.model.toLocalEvent
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class GetEventsUseCase(
    private val eventsRepository: EventsRepository,
) {
    operator fun invoke(date: LocalDate) = eventsRepository.getEvents(date).map { events ->
        events.mapNotNull { event ->
            event.toLocalEvent().takeIf {
                it.type != Event.Type.WorkingLocation
            }
        }
    }
}
