package eu.acolombo.work.calendar.events.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import eu.acolombo.work.calendar.events.ui.composables.LoadingIndicator
import eu.acolombo.work.calendar.events.ui.composables.TimeInformation
import eu.acolombo.work.calendar.events.ui.composables.rememberDatePickerState

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

    val datePickerState = rememberDatePickerState(uiState.input.date)
    if (showDialog.value) {
        DatePickerDialog(
            datePickerState = datePickerState,
            onSelection = { onInputChange(Date(it)) },
            hideDatePicker = { showDialog.value = false },
        )
    }

    val snackHostState = remember { SnackbarHostState() }
    val snackMessage = stringResource(id = R.string.alert_done_for_the_day)
    val snackOwner = LocalLifecycleOwner.current

    val contentHeight = 200.dp

    BoxWithConstraints {
        BottomSheetScaffold(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            content = {
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TimeInformation(
                        modifier = Modifier
                            .padding(it)
                            .height(contentHeight)
                            .widthIn(max = 640.dp) // androidx.compose.material3.BottomSheetMaxWidth
                            .fillMaxWidth(),
                        update = (uiState as? Success)?.update,
                    )
                }
            },
            scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberStandardBottomSheetState(
                    confirmValueChange = { it != SheetValue.Hidden }
                )
            ),
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
                    when (uiState) {
                        is Success -> if (uiState.events.isEmpty()) {
                            // TODO: It would be cool to resize the column when expanding and add vertical arrangement, or maybe just use something similar to xml coordinator layout
                            IllustrationWithDescription(
                                illustration = Meditation(
                                    fill = MaterialTheme.colorScheme.secondaryContainer,
                                    stroke = MaterialTheme.colorScheme.onSecondaryContainer,
                                ),
                                description = stringResource(R.string.description_empty),
                            )
                        } else {
                            LazyColumn(
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
                            LoadingIndicator(circleColor = MaterialTheme.colorScheme.primary)
                            if (uiState.showSnackbar) {
                                LaunchedEffect(snackOwner.lifecycle) {
                                    snackHostState.showSnackbar(message = snackMessage)
                                }
                            }
                        }

                        is Error -> IllustrationWithDescription(
                            illustration = Computer(
                                fill = MaterialTheme.colorScheme.secondaryContainer,
                                stroke = MaterialTheme.colorScheme.onSecondaryContainer,
                            ),
                            description = uiState.message
                                ?: stringResource(R.string.description_error),
                        )
                    }
                }
            },
        )
    }
}

