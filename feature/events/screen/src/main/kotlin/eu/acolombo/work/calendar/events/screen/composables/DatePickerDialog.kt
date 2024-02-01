@file:OptIn(ExperimentalMaterial3Api::class)

package eu.acolombo.work.calendar.events.screen.composables

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@Composable
internal fun DatePickerDialog(
    datePickerState: DatePickerState,
    onSelection: (LocalDate) -> Unit,
    hideDatePicker: () -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = { hideDatePicker() },
        confirmButton = {
            TextButton(onClick = {
                hideDatePicker()
                datePickerState.selectedDateMillis?.let {
                    onSelection(
                        Instant.fromEpochMilliseconds(it)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date,
                    )
                }
            }) {
                Text(stringResource(id = android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = { hideDatePicker() }) {
                Text(stringResource(id = android.R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun rememberDatePickerState(date: LocalDate): DatePickerState =
    rememberSaveable(date, saver = DatePickerState.Saver()) {
        val selectedMillis = date
            .atTime(9, 0, 0, 0)
            .toInstant(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()

        DatePickerState(
            initialSelectedDateMillis = selectedMillis,
            initialDisplayedMonthMillis = selectedMillis,
            yearRange = DatePickerDefaults.YearRange,
            initialDisplayMode = DisplayMode.Picker,
        )
    }

