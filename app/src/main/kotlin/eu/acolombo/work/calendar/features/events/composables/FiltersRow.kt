@file:OptIn(ExperimentalMaterial3Api::class)

package eu.acolombo.work.calendar.features.events.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.acolombo.work.calendar.R
import eu.acolombo.work.calendar.features.events.EventsFilter
import eu.acolombo.work.calendar.ui.Spacing
import eu.acolombo.work.calendar.ui.WorkCalendarTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun FiltersRow(
    input: EventsFilter,
    onSelectToday: () -> Unit,
    onSelectTomorrow: () -> Unit,
    showDatePicker: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        FlowRow(modifier = Modifier.weight(1f)) {
            SpicyFilterChip(
                label = stringResource(R.string.button_today),
                selected = input is EventsFilter.Today,
                onClick = { onSelectToday() },
            )
            SpicyFilterChip(
                label = stringResource(R.string.button_tomorrow),
                modifier = Modifier.padding(start = Spacing.S),
                selected = input is EventsFilter.Tomorrow,
                onClick = { onSelectTomorrow() },
            )
            if (input is EventsFilter.Date) {
                SpicyFilterChip(
                    label = input.date.toString(),
                    modifier = Modifier.padding(start = Spacing.S),
                    selected = true,
                    onClick = { showDatePicker() },
//                    trailingIcon = {
//                        Icon(
//                            modifier = Modifier.size(18.dp),
//                            imageVector = Icons.TwoTone.Autorenew,
//                            contentDescription = null,
//                        )
//                    }
                )
            }
        }
        if (input !is EventsFilter.Date) {
            FilledTonalIconButton(
                onClick = { showDatePicker() },
                modifier = Modifier.padding(start = Spacing.S),
            ) {
                Icon(
                    imageVector = Icons.TwoTone.CalendarToday,
                    contentDescription = stringResource(R.string.description_select_date),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SpicyFilterChip(
    label: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    FilterChip(
        modifier = modifier,
        onClick = onClick,
        label = { Text(text = label, fontSize = 16.sp) },
        selected = selected,
        shape = MaterialTheme.shapes.extraLarge,
        trailingIcon = trailingIcon,
    )
}

@Composable
private fun MayoFilterChip(
    label: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    if (selected) {
        Button(
            modifier = modifier,
            onClick = onClick,
        ) {
            Text(text = label, fontSize = 16.sp)
        }
    } else {
        OutlinedButton(
            modifier = modifier,
            onClick = onClick,
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.outline),
        ) {
            Text(text = label, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionRowPreview() {
    WorkCalendarTheme {
        FiltersRow(EventsFilter.Today, {}, {}, {})
    }
}
