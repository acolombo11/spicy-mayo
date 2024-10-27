package eu.acolombo.work.calendar.events.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.acolombo.work.calendar.design.illustrations.Computer
import eu.acolombo.work.calendar.design.illustrations.Meditation
import eu.acolombo.work.calendar.design.theme.Spacing
import eu.acolombo.work.calendar.events.ui.EventsFilter.Date
import eu.acolombo.work.calendar.events.ui.EventsFilter.Today
import eu.acolombo.work.calendar.events.ui.EventsFilter.Tomorrow
import eu.acolombo.work.calendar.events.ui.EventsViewState.Error
import eu.acolombo.work.calendar.events.ui.EventsViewState.Loading
import eu.acolombo.work.calendar.events.ui.EventsViewState.Success
import eu.acolombo.work.calendar.events.ui.composables.DatePickerDialog
import eu.acolombo.work.calendar.events.ui.composables.FiltersRow
import eu.acolombo.work.calendar.events.ui.composables.IllustrationWithDescription
import eu.acolombo.work.calendar.events.ui.composables.LoadingWithDescription
import eu.acolombo.work.calendar.events.ui.composables.TimeInformation
import eu.acolombo.work.calendar.events.ui.composables.rememberDatePicker

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import spicy_mayo_multiplatform.feature.events.ui.generated.resources.Res
import spicy_mayo_multiplatform.feature.events.ui.generated.resources.alert_done_for_the_day
import spicy_mayo_multiplatform.feature.events.ui.generated.resources.description_empty
import spicy_mayo_multiplatform.feature.events.ui.generated.resources.description_error
import spicy_mayo_multiplatform.feature.events.ui.generated.resources.loading

@Serializable
object EventsRouteDestination

@Composable
fun EventsRoute(
    viewModel: EventsViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    EventsScreen(
        uiState = uiState,
        onInputChange = viewModel::onInputChange,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventsScreen(
    uiState: EventsViewState,
    onInputChange: (EventsFilter) -> Unit,
) {
    val showDialog = remember { mutableStateOf(false) }

    val datePickerState = rememberDatePicker(uiState.input.date)
    if (showDialog.value) {
        DatePickerDialog(
            datePickerState = datePickerState,
            onSelection = { onInputChange(Date(it)) },
            hideDatePicker = { showDialog.value = false },
        )
    }

    val snackHostState = remember { SnackbarHostState() }
    val snackMessage = stringResource(Res.string.alert_done_for_the_day)
    val snackOwner = LocalLifecycleOwner.current
    val contentHeight = BackLayerContentHeight

    Scaffold { padding ->
        BoxWithConstraints(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(top = padding.calculateTopPadding()),
        ) {
            BottomSheetScaffold(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                content = {
                    TimeInformation(
                        modifier = Modifier
                            .padding(it)
                            .padding(bottom = padding.calculateTopPadding()/2)
                            .height(contentHeight)
                            .widthIn(max = BottomSheetMaxWidth)
                            .fillMaxWidth(),
                        update = (uiState as? Success)?.update,
                    )
                },
                scaffoldState = rememberBottomSheetScaffoldState(),
                snackbarHost = { SnackbarHost(snackHostState) },
                sheetPeekHeight = maxHeight - contentHeight,
                sheetTonalElevation = 0.dp,
                sheetShadowElevation = 0.dp,
                sheetDragHandle = {
                    FiltersRow(
                        modifier = Modifier.padding(Spacing.M),
                        input = uiState.input,
                        onSelectToday = { onInputChange(Today) },
                        onSelectTomorrow = { onInputChange(Tomorrow) },
                        showDatePicker = { showDialog.value = true },
                    )
                },
                sheetContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .draggable(
                                // TODO: Change to ViewPager
                                orientation = Orientation.Horizontal,
                                state = DraggableState {
                                    when {
                                        it < 0 && uiState.input == Today -> onInputChange(Tomorrow)
                                        it > 0 && uiState.input == Tomorrow -> onInputChange(Today)
                                        it > 0 && uiState.input is Date -> onInputChange(Tomorrow)
                                    }
                                },
                            )
                    ) {
                        val bottom = padding.calculateBottomPadding()
                        when (uiState) {
                            is Success -> if (uiState.events.isEmpty()) {
                                // TODO: It would be cool to resize the column when expanding and add vertical arrangement, or maybe just use something similar to xml coordinator layout
                                IllustrationWithDescription(
                                    modifier = Modifier.padding(bottom = bottom),
                                    illustration = Meditation(
                                        fill = MaterialTheme.colorScheme.secondaryContainer,
                                        stroke = MaterialTheme.colorScheme.onSecondaryContainer,
                                    ),
                                    description = stringResource(Res.string.description_empty),
                                )
                            } else {
                                LazyColumn(
                                    contentPadding = PaddingValues(bottom = bottom),
                                    modifier = Modifier
                                        .padding(horizontal = Spacing.S)
                                        .fillMaxSize()
                                        .clip(
                                            MaterialTheme.shapes.medium.copy(
                                                bottomStart = CornerSize(0.dp),
                                                bottomEnd = CornerSize(0.dp),
                                            )
                                        )
                                        .background(MaterialTheme.colorScheme.background),
                                ) {
                                    items(items = uiState.events) {
                                        EventItem(
                                            event = it,
                                            modifier = Modifier.padding(bottom = Spacing.S),
                                        )
                                    }
                                }
                            }

                            is Loading -> Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center,
                            ) {
                                LoadingWithDescription(
                                    modifier = Modifier.padding(bottom = bottom),
                                    description = stringResource(Res.string.loading),
                                )
                                if (uiState.showSnack) {
                                    LaunchedEffect(snackOwner.lifecycle) {
                                        snackHostState.showSnackbar(message = snackMessage)
                                    }
                                }
                            }

                            is Error -> IllustrationWithDescription(
                                modifier = Modifier.padding(bottom = bottom),
                                illustration = Computer(
                                    fill = MaterialTheme.colorScheme.secondaryContainer,
                                    stroke = MaterialTheme.colorScheme.onSecondaryContainer,
                                ),
                                description = uiState.message
                                    ?: stringResource(Res.string.description_error),
                            )
                        }
                    }
                },
            )
        }
    }
}

// copy of internal androidx.compose.material3.BottomSheetMaxWidth
private val BottomSheetMaxWidth = 640.dp
private val BackLayerContentHeight = 220.dp
