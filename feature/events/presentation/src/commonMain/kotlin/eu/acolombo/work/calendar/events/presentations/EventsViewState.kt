package eu.acolombo.work.calendar.events.presentations

import eu.acolombo.work.calendar.events.domain.model.Event
import eu.acolombo.work.calendar.events.domain.model.Update
import org.jetbrains.compose.resources.StringResource

sealed class EventsViewState(val input: EventsFilter) {
    class Loading(
        input: EventsFilter,
        val showSnack: Boolean = false,
    ) : EventsViewState(input)
    class Success(
        val events: List<Event>,
        input: EventsFilter,
        val update: Update,
    ) : EventsViewState(input)
    class Error(
        input: EventsFilter,
        val resource: StringResource?,
        val message: String?,
    ) : EventsViewState(input)
}
