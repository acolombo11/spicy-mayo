package eu.acolombo.work.calendar.events.domain

import eu.acolombo.work.calendar.events.data.source.EventsRepository
import eu.acolombo.work.calendar.events.domain.model.Event
import eu.acolombo.work.calendar.events.domain.model.toLocalEvent
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
