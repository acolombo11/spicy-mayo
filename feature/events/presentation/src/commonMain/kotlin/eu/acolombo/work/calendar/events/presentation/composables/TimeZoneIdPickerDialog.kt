@file:OptIn(ExperimentalMaterial3Api::class)

package eu.acolombo.work.calendar.events.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.acolombo.work.calendar.events.util.TimeZoneFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import org.jetbrains.compose.resources.stringResource
import spicy_mayo.feature.events.presentation.generated.resources.Res
import spicy_mayo.feature.events.presentation.generated.resources.button_dismiss
import spicy_mayo.feature.events.presentation.generated.resources.button_return_to_top
import spicy_mayo.feature.events.presentation.generated.resources.label_search_timezone

private val timeZoneIds = TimeZone.availableZoneIds
    .filter { "/" in it && !it.startsWith("System") && !it.startsWith("Etc") }
    .toList()
    .sorted()

@Composable
internal fun TimeZoneIdPickerDialog(
    selectedTimeZoneId: String?,
    onSelectTimeZoneId: (String) -> Unit,
    hideTimeZoneIdPicker: () -> Unit,
    onSearchError: () -> Unit,
    modifier: Modifier = Modifier,
    lazyState: LazyListState = rememberLazyListState(),
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = { hideTimeZoneIdPicker() },
    ) {
        TimeZoneIdSearchBar(
            timeZoneIds = timeZoneIds,
            selectedTimeZoneId = selectedTimeZoneId,
            lazyState = lazyState,
            onDismiss = hideTimeZoneIdPicker,
            onSelection = { timeZoneId ->
                hideTimeZoneIdPicker()
                onSelectTimeZoneId(timeZoneId)
            },
            onSearchError = {
                hideTimeZoneIdPicker()
                onSearchError()
            },
        )
    }
}

@Composable
private fun TimeZoneIdSearchBar(
    timeZoneIds: List<String>,
    selectedTimeZoneId: String?,
    onSelection: (String) -> Unit,
    onDismiss: () -> Unit,
    onSearchError: () -> Unit,
    modifier: Modifier = Modifier,
    lazyState: LazyListState = rememberLazyListState(),
) {
    var query by remember { mutableStateOf("") }

    val filteredTimeZoneIds = with(TimeZoneFormatter) {
        timeZoneIds.filter { zoneId ->
            getFilterName(zoneId).contains(
                other = query,
                ignoreCase = true,
            )
        }
    }

    val expanded = true
    val onExpandedChange : (Boolean) -> Unit = { }

    Box(modifier = modifier) { // Box to avoid SearchBar InputField to take up all height
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    modifier = Modifier.fillMaxWidth(),
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = {
                        filteredTimeZoneIds.singleOrNull()?.let {
                            onSelection(it)
                        } ?: onSearchError()
                    },
                    expanded = expanded,
                    onExpandedChange = onExpandedChange,
                    placeholder = {
                        Text(stringResource(Res.string.label_search_timezone))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ManageSearch,
                            contentDescription = null, // Placeholder is enough
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = onDismiss,
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(Res.string.button_dismiss),
                                )
                            }
                        )
                    },
                )
            },
            expanded = expanded,
            onExpandedChange = onExpandedChange,
        ) {
            TimeZoneIdList(
                timeZoneIds = filteredTimeZoneIds,
                selectedZoneId = selectedTimeZoneId,
                lazyState = lazyState,
                onSelection = onSelection,
            )
        }
    }
}

@Composable
private fun TimeZoneIdList(
    timeZoneIds: List<String>,
    selectedZoneId: String?,
    lazyState: LazyListState,
    modifier: Modifier = Modifier,
    onSelection: (String) -> Unit,
) {
    Box(contentAlignment = Alignment.BottomEnd) {
        LazyColumn(
            modifier = modifier,
            state = lazyState,
        ) {
            itemsIndexed(
                items = timeZoneIds,
            ) { index, zoneId ->
                TimeZoneIdItem(
                    zoneId = zoneId,
                    selected = selectedZoneId == zoneId,
                    variant = index % 2 != 0,
                    onSelection = onSelection,
                )
            }
        }
        ReturnToTopButton(
            modifier = Modifier.padding(16.dp),
            lazyState = lazyState,
        )
    }
}

@Composable
private fun TimeZoneIdItem(
    zoneId: String,
    selected: Boolean,
    variant: Boolean,
    onSelection: (String) -> Unit,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = when {
                    selected -> MaterialTheme.colorScheme.primary
                    variant -> MaterialTheme.colorScheme.surfaceVariant
                    else -> MaterialTheme.colorScheme.surface
                },
            )
            .clickable {
                onSelection(zoneId)
            }
            .padding(16.dp),
        text = TimeZoneFormatter.getDisplayName(zoneId),
        color = if (selected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurface
        },
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

@Composable
fun ReturnToTopButton(
    lazyState: LazyListState,
    modifier: Modifier = Modifier,
    visibleAfterItems: Int = 10,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    icon: ImageVector = Icons.Default.ArrowUpward,
    contentDescription: String = stringResource(Res.string.button_return_to_top),
) {
    AnimatedVisibility(lazyState.firstVisibleItemIndex > visibleAfterItems) {
        FilledIconButton(
            modifier = modifier,
            onClick = {
                coroutineScope.launch {
                    lazyState.animateScrollToItem(0)
                }
            },
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
            )
        }
    }
}