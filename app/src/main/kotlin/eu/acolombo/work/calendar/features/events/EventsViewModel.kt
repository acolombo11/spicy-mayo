package eu.acolombo.work.calendar.features.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.acolombo.work.calendar.data.model.Update
import eu.acolombo.work.calendar.data.model.toLocalEvent
import eu.acolombo.work.calendar.data.source.DefaultEventsRepository
import eu.acolombo.work.calendar.data.source.EventsRepository
import eu.acolombo.work.calendar.features.events.EventsFilter.Date
import eu.acolombo.work.calendar.features.events.EventsFilter.Today
import eu.acolombo.work.calendar.features.events.EventsFilter.Tomorrow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@OptIn(ExperimentalCoroutinesApi::class)
class EventsViewModel(
    private val eventsRepository: EventsRepository = DefaultEventsRepository(),
) : ViewModel() {

    private val _input = MutableSharedFlow<Pair<EventsFilter, Boolean>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    @Suppress("USELESS_CAST")
    val uiState: StateFlow<EventsViewState> = _input.flatMapLatest { (input, showSnackbar) ->
        eventsRepository.getEvents(input.date).map { events ->
            EventsViewState.Success(
                events = events.map { it.toLocalEvent() },
                input = input,
                update = Update(Clock.System.now()),
            ) as EventsViewState
        }.onStart {
            emit(EventsViewState.Loading(input, showSnackbar))
        }.catch {
            emit(EventsViewState.Error(input, it.message))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = EventsViewState.Loading(Today),
    )

    init {
        onInputChange(Today)
        checkEndOfTheDay()
    }

    fun onInputChange(
        selection: EventsFilter,
    ) = onInputChange(selection = selection, showSnackbar = false)

    private fun onInputChange(
        selection: EventsFilter,
        showSnackbar: Boolean,
    ) = viewModelScope.launch {
        val input = when ((selection as? Date)?.date) {
            Today.date -> Today
            Tomorrow.date -> Tomorrow
            else -> selection
        }
        _input.emit(input to showSnackbar)
    }

    private fun checkEndOfTheDay() = viewModelScope.launch {
        val state = uiState.filterIsInstance<EventsViewState.Success>().first()
        state.events.lastOrNull()
            ?.takeIf { it.end < state.update.latest }
            ?.let { onInputChange(selection = Tomorrow, showSnackbar = true) }
    }

    // TODO Add refresh in ui
    fun refresh() = viewModelScope.launch {
        _input.emit(_input.replayCache.last())
    }
}

