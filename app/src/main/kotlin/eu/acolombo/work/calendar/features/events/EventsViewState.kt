package eu.acolombo.work.calendar.features.events

import eu.acolombo.work.calendar.data.model.LocalEvent
import eu.acolombo.work.calendar.data.model.Update

sealed class EventsViewState(val input: EventsFilter) {
    class Loading(input: EventsFilter, val showSnackbar: Boolean = false) : EventsViewState(input)
    class Success(val events: List<LocalEvent>, input: EventsFilter, val update: Update) : EventsViewState(input)
    class Error(input: EventsFilter, val message: String?) : EventsViewState(input)
}
