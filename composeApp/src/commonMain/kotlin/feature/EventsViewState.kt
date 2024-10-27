package feature

import feature.layers.domain.model.Event
import feature.layers.domain.model.Update

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
        val message: String?,
    ) : EventsViewState(input)
}
