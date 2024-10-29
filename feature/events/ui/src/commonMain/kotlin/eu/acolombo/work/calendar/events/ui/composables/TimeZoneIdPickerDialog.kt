package eu.acolombo.work.calendar.events.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import spicy_mayo.feature.events.ui.generated.resources.Res
import spicy_mayo.feature.events.ui.generated.resources.cancel
import spicy_mayo.feature.events.ui.generated.resources.ok

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimeZoneIdPickerDialog(
    selected: String?,
    onSelection: (String) -> Unit,
    hideTimeZoneIdPicker: () -> Unit,
) {
    BasicAlertDialog(
        modifier = Modifier.fillMaxHeight(.9f),
        onDismissRequest = { hideTimeZoneIdPicker() },
        properties = DialogProperties(),
    ) {
        LazyColumn(modifier = Modifier.clip(MaterialTheme.shapes.extraLarge)) {
            itemsIndexed(items = timeZones) { index, zoneId ->
                val isSelected = selected == zoneId
                val isEven = index % 2 == 0
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = when {
                                isSelected -> MaterialTheme.colorScheme.primary
                                isEven -> MaterialTheme.colorScheme.surfaceVariant
                                else -> MaterialTheme.colorScheme.surface
                            },
                        )
                        .clickable {
                            onSelection(zoneId)
                            hideTimeZoneIdPicker()
                        }
                        .padding(16.dp),
                    text = zoneId,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        }
    }
}

private val timeZones = TimeZone.availableZoneIds
    .filter { "/" in it && !it.startsWith("System") && !it.startsWith("Etc") }
    .toList()
    .sorted()



