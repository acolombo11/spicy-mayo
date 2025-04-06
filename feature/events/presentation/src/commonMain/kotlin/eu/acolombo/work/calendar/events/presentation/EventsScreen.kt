package eu.acolombo.work.calendar.events.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.acolombo.work.calendar.design.illustrations.Illustrations
import eu.acolombo.work.calendar.design.illustrations.vectors.Computer
import eu.acolombo.work.calendar.design.illustrations.vectors.Meditation
import eu.acolombo.work.calendar.design.theme.Spacing
import eu.acolombo.work.calendar.events.domain.model.Location
import eu.acolombo.work.calendar.events.presentation.EventsFilter.Date
import eu.acolombo.work.calendar.events.presentation.EventsFilter.Today
import eu.acolombo.work.calendar.events.presentation.EventsFilter.Tomorrow
import eu.acolombo.work.calendar.events.presentation.EventsViewState.Error
import eu.acolombo.work.calendar.events.presentation.EventsViewState.Loading
import eu.acolombo.work.calendar.events.presentation.EventsViewState.Success
import eu.acolombo.work.calendar.events.presentation.composables.DatePickerDialog
import eu.acolombo.work.calendar.events.presentation.composables.FiltersRow
import eu.acolombo.work.calendar.events.presentation.composables.IllustrationWithDescription
import eu.acolombo.work.calendar.events.presentation.composables.LoadingWithDescription
import eu.acolombo.work.calendar.events.presentation.composables.TimeInformation
import eu.acolombo.work.calendar.events.presentation.composables.TimeZoneIdPickerDialog
import eu.acolombo.work.calendar.events.presentation.composables.rememberDatePicker
import kotlinx.coroutines.launch

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import spicy_mayo.feature.events.presentation.generated.resources.Res
import spicy_mayo.feature.events.presentation.generated.resources.alert_done_for_the_day
import spicy_mayo.feature.events.presentation.generated.resources.button_retry
import spicy_mayo.feature.events.presentation.generated.resources.description_empty
import spicy_mayo.feature.events.presentation.generated.resources.description_error
import spicy_mayo.feature.events.presentation.generated.resources.loading

@Serializable
object EventsRouteDestination

@Composable
fun EventsRoute(
    viewModel: EventsViewModel = viewModel(),
) {
    val eventsState by viewModel.eventsState.collectAsStateWithLifecycle()
    val locations by viewModel.locations.collectAsStateWithLifecycle()

    EventsScreen(
        onInputChange = viewModel::onInputChange,
        onLocationChange = viewModel::onChangeLocation,
        onRefresh = viewModel::refresh,
        locations = locations,
        eventsState = eventsState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventsScreen(
    onInputChange: (EventsFilter) -> Unit,
    onLocationChange: (index: Int, timeZoneId: String?) -> Unit,
    onRefresh: () -> Unit,
    eventsState: EventsViewState,
    locations: List<Location?>,
) {
    val showDatePicker = remember { mutableStateOf(false) }
    val showTimeZonePickerIndex = remember { mutableStateOf<Int?>(null) }

    val snackHostState = remember { SnackbarHostState() }
    val snackOwner = LocalLifecycleOwner.current

    val datePickerState = rememberDatePicker(eventsState.input.date)
    if (showDatePicker.value) {
        DatePickerDialog(
            datePickerState = datePickerState,
            onSelectDate = { onInputChange(Date(it)) },
            hideDatePicker = { showDatePicker.value = false },
        )
    }
    val timeZoneIdPickerState = rememberLazyListState()
    showTimeZonePickerIndex.value?.let { index ->
        TimeZoneIdPickerDialog(
            modifier = Modifier.fillMaxHeight(DialogHeight)
                .clip(MaterialTheme.shapes.extraLarge),
            lazyState = timeZoneIdPickerState,
            selectedTimeZoneId = locations.getOrNull(index)?.timezone?.id,
            onSelectTimeZoneId = { onLocationChange(index, it) },
            hideTimeZoneIdPicker = { showTimeZonePickerIndex.value = null },
            onSearchError = {
                onLocationChange(index, null)
                snackOwner.lifecycleScope.launch {
                    snackHostState.showSnackbar(
                        message = "No timezone found. Select one of the available timezones",
                    )
                }
            },
        )
    }

    val contentHeight = BackLayerContentHeight

    BoxWithConstraints(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .windowInsetsPadding(WindowInsets.statusBars),
    ) {
        BottomSheetScaffold(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            content = { padding ->
                TimeInformation(
                    modifier = Modifier
                        .padding(padding)
                        .padding(horizontal = Spacing.S.dp)
                        .padding(bottom = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() / 2)
                        .height(contentHeight)
                        .fillMaxWidth(),
                    latest = (eventsState as? Success)?.update?.latest,
                    locations = locations,
                    onOfficeClick = { index ->
                        showTimeZonePickerIndex.value = index
                    },
                )
            },
            scaffoldState = rememberBottomSheetScaffoldState(),
            snackbarHost = { SnackbarHost(snackHostState) },
            sheetPeekHeight = maxHeight - contentHeight,
            sheetTonalElevation = 0.dp,
            sheetShadowElevation = 0.dp,
            sheetContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            sheetDragHandle = {
                FiltersRow(
                    modifier = Modifier.padding(Spacing.M.dp),
                    input = eventsState.input,
                    onSelectToday = { onInputChange(Today) },
                    onSelectTomorrow = { onInputChange(Tomorrow) },
                    showDatePicker = { showDatePicker.value = true },
                )
            },
            sheetContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .draggable(
                            // FIXME Change to ViewPager
                            orientation = Orientation.Horizontal,
                            state = DraggableState {
                                when {
                                    it < 0 && eventsState.input == Today -> onInputChange(Tomorrow)
                                    it > 0 && eventsState.input == Tomorrow -> onInputChange(Today)
                                    it > 0 && eventsState.input is Date -> onInputChange(Tomorrow)
                                }
                            },
                        ),
                ) {
                    val modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                    when (eventsState) {
                        is Success -> if (eventsState.events.isEmpty()) {
                            IllustrationWithDescription(
                                modifier = modifier,
                                illustration = Illustrations.Meditation,
                                description = stringResource(Res.string.description_empty),
                            )
                        } else {
                            LazyColumn(
                                contentPadding = WindowInsets.navigationBars.asPaddingValues(),
                                modifier = Modifier
                                    .padding(horizontal = Spacing.S.dp)
                                    .fillMaxSize()
                                    .clip(
                                        MaterialTheme.shapes.medium.copy(
                                            bottomStart = CornerSize(0.dp),
                                            bottomEnd = CornerSize(0.dp),
                                        ),
                                    )
                                    .background(MaterialTheme.colorScheme.surfaceContainerLow),
                            ) {
                                items(items = eventsState.events) { event ->
                                    EventItem(
                                        event = event,
                                        modifier = Modifier.padding(bottom = Spacing.S.dp),
                                    )
                                }
                            }
                        }

                        is Loading -> Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            LoadingWithDescription(
                                modifier = modifier,
                                description = stringResource(Res.string.loading),
                            )
                            if (eventsState.showSnack) {
                                val snackMessage = stringResource(Res.string.alert_done_for_the_day)
                                LaunchedEffect(snackOwner.lifecycle) {
                                    snackHostState.showSnackbar(message = snackMessage)
                                }
                            }
                        }

                        is Error -> IllustrationWithDescription(
                            modifier = modifier,
                            illustration = Illustrations.Computer,
                            description = eventsState.resource?.let { stringResource(it) }
                                ?: eventsState.message
                                ?: stringResource(Res.string.description_error),
                            button = stringResource(Res.string.button_retry),
                            onClick = onRefresh,
                        )
                    }
                }
            },
        )
    }
}

private val BackLayerContentHeight = 220.dp
private const val DialogHeight = .9f
