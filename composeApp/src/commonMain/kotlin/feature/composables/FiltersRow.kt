package feature.composables

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CalendarToday
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import feature.EventsFilter
import spicy_mayo_multiplatform.composeapp.generated.resources.Res
import spicy_mayo_multiplatform.composeapp.generated.resources.button_today
import spicy_mayo_multiplatform.composeapp.generated.resources.button_tomorrow
import spicy_mayo_multiplatform.composeapp.generated.resources.description_select_date
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.Spacing
import theme.WorkCalendarTheme

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
                label = stringResource(Res.string.button_today),
                selected = input is EventsFilter.Today,
                onClick = { onSelectToday() },
            )
            SpicyFilterChip(
                label = stringResource(Res.string.button_tomorrow),
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
                    contentDescription = stringResource(Res.string.description_select_date),
                )
            }
        }
    }
}

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

@Preview
@Composable
private fun ActionRowPreview() {
    WorkCalendarTheme {
        FiltersRow(EventsFilter.Today, {}, {}, {})
    }
}

