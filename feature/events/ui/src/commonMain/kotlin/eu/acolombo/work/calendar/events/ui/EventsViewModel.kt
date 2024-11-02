package eu.acolombo.work.calendar.events.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.acolombo.work.calendar.events.domain.GetEventsUseCase
import eu.acolombo.work.calendar.events.domain.GetLocationsUseCase
import eu.acolombo.work.calendar.events.domain.SetLocationUseCase
import eu.acolombo.work.calendar.events.domain.model.Update
import eu.acolombo.work.calendar.events.domain.model.Location
import eu.acolombo.work.calendar.events.ui.EventsFilter.Date
import eu.acolombo.work.calendar.events.ui.EventsFilter.Today
import eu.acolombo.work.calendar.events.ui.EventsFilter.Tomorrow
import eu.acolombo.work.calendar.network.DeploymentErrorException
import eu.acolombo.work.calendar.network.NoConnectionException
import eu.acolombo.work.calendar.network.ServerErrorException
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
import spicy_mayo.feature.events.ui.generated.resources.Res
import spicy_mayo.feature.events.ui.generated.resources.error_connection
import spicy_mayo.feature.events.ui.generated.resources.error_deployment

@OptIn(ExperimentalCoroutinesApi::class)
class EventsViewModel(
    getEvents: GetEventsUseCase,
    getLocations: GetLocationsUseCase,
    private val setLocation: SetLocationUseCase,
) : ViewModel() {

    private val _input = MutableSharedFlow<Pair<EventsFilter, Boolean>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    @Suppress("USELESS_CAST")
    val eventsState: StateFlow<EventsViewState> = _input.flatMapLatest { (input, showSnackbar) ->
        getEvents(input.date).map { events ->
            EventsViewState.Success(
                events = events,
                input = input,
                update = Update(Clock.System.now()),
            ) as EventsViewState
        }.onStart {
            emit(EventsViewState.Loading(input = input, showSnack = showSnackbar))
        }.catch {
            val resource = when (it) {
                is DeploymentErrorException -> Res.string.error_deployment
                is NoConnectionException -> Res.string.error_connection
                else -> null
            }
            val message = when (it) {
                is ServerErrorException -> it.error.message
                else -> it.message
            }
            emit(EventsViewState.Error(input = input, resource = resource, message = message))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = EventsViewState.Loading(input = Today),
    )

    val locations: StateFlow<List<Location?>> = getLocations().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = List(2) { null },
    )

    init {
        onInputChange(Today)
        checkEndOfTheDay()
    }

    fun onInputChange(
        selection: EventsFilter,
    ) = onInputChange(selection = selection, showSnack = false)

    private fun onInputChange(
        selection: EventsFilter,
        showSnack: Boolean,
    ) = viewModelScope.launch {
        val input = when ((selection as? Date)?.date) {
            Today.date -> Today
            Tomorrow.date -> Tomorrow
            else -> selection
        }
        _input.emit(input to showSnack)
    }

    private fun checkEndOfTheDay() = viewModelScope.launch {
        val state = eventsState.filterIsInstance<EventsViewState.Success>().first()
        state.events.lastOrNull()
            ?.takeIf { it.end?.let { it < state.update.time } ?: false }
            ?.let { onInputChange(selection = Tomorrow, showSnack = true) }
    }

    // TODO Add refresh in ui
    fun refresh() = viewModelScope.launch {
        _input.emit(_input.replayCache.last())
    }

    fun onChangeLocation(index: Int, timeZoneId: String?) = viewModelScope.launch {
        setLocation(index, timeZoneId)
    }
}

