@file:OptIn(ExperimentalMaterial3Api::class)

package eu.acolombo.work.calendar.events.presentation.composables

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import spicy_mayo.feature.events.presentation.generated.resources.Res
import spicy_mayo.feature.events.presentation.generated.resources.cancel
import spicy_mayo.feature.events.presentation.generated.resources.ok

@Composable
internal fun DatePickerDialog(
    datePickerState: DatePickerState,
    onSelectDate: (LocalDate) -> Unit,
    hideDatePicker: () -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = { hideDatePicker() },
        confirmButton = {
            TextButton(onClick = {
                hideDatePicker()
                datePickerState.selectedDateMillis?.let {
                    onSelectDate(
                        Instant.fromEpochMilliseconds(it)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date,
                    )
                }
            }) {
                Text(stringResource(Res.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = { hideDatePicker() }) {
                Text(stringResource(Res.string.cancel))
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun rememberDatePicker(date: LocalDate): DatePickerState {
    val selectedMillis = date
        .atTime(StartOfDay, 0, 0, 0)
        .toInstant(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
    return rememberDatePickerState(
        initialSelectedDateMillis = selectedMillis,
        initialDisplayedMonthMillis = selectedMillis,
        yearRange = DatePickerDefaults.YearRange,
        initialDisplayMode = DisplayMode.Picker,
    )
}

private const val StartOfDay = 9
