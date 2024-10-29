package eu.acolombo.work.calendar.events.ui

import eu.acolombo.work.calendar.events.domain.model.Event
import eu.acolombo.work.calendar.events.domain.model.Update
import eu.acolombo.work.calendar.events.model.Office
import org.jetbrains.compose.resources.StringResource

sealed class EventsViewState(val input: EventsFilter, val offices: List<Office>) {
    class Loading(
        input: EventsFilter,
        offices: List<Office>,
        val showSnack: Boolean = false,
    ) : EventsViewState(input, offices)
    class Success(
        val events: List<Event>,
        input: EventsFilter,
        offices: List<Office>,
        val update: Update,
    ) : EventsViewState(input, offices)
    class Error(
        input: EventsFilter,
        offices: List<Office>,
        val resource: StringResource?,
        val message: String?,
    ) : EventsViewState(input, offices)
}
