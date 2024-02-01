package eu.acolombo.work.calendar.events.screen

import eu.acolombo.work.calendar.events.screen.model.Event
import eu.acolombo.work.calendar.events.screen.model.Update

sealed class EventsViewState(val input: EventsFilter) {
    class Loading(input: EventsFilter, val showSnackbar: Boolean = false) : EventsViewState(input)
    class Success(val events: List<Event>, input: EventsFilter, val update: Update) : EventsViewState(input)
    class Error(input: EventsFilter, val message: String?) : EventsViewState(input)
}
