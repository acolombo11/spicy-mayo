package eu.acolombo.work.calendar.events.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import eu.acolombo.work.calendar.events.ui.model.Event
import eu.acolombo.work.calendar.events.ui.model.toLocalEvent
import eu.acolombo.work.calendar.events.data.model.Event as DataEvent
import eu.acolombo.work.calendar.design.theme.Spacing
import eu.acolombo.work.calendar.design.theme.WorkCalendarTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun EventItem(
    event: Event,
    modifier: Modifier = Modifier,
    containerColor: Color = when (event.type) {
        Event.Type.Default -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.primary
    },
    accentColor: Color = when (event.type) {
        Event.Type.Default -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surfaceVariant
    },
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Column(modifier = Modifier.padding(horizontal = Spacing.L, vertical = Spacing.M)) {
            Text(
                modifier = Modifier.fillMaxWidth(.6f),
                text = event.summary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                minLines = 2,
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                modifier = Modifier
                    .basicMarquee()
                    .padding(vertical = Spacing.S)
                    .alpha(.5f),
                text = event.attendees.joinToString(),
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                LabeledTime(
                    localTime = event.start,
                    label = stringResource(R.string.label_start),
                    alignment = Alignment.Start,
                )
                Text(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(accentColor)
                        .padding(horizontal = Spacing.M, vertical = Spacing.S),
                    text = event.duration.toString(DurationUnit.MINUTES),
                    style = MaterialTheme.typography.labelMedium,
                    color = containerColor,
                    maxLines = 1,
                )
                LabeledTime(
                    localTime = event.end,
                    label = stringResource(R.string.label_end),
                    alignment = Alignment.End,
                )
            }
        }
    }
}

@Composable
private fun LabeledTime(
    localTime: LocalTime,
    label: String,
    alignment: Alignment.Horizontal,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = alignment,
        modifier = modifier.padding(bottom = Spacing.XS),
    ) {
        val textAlign = when (alignment) {
            Alignment.Start -> TextAlign.Start
            Alignment.End -> TextAlign.End
            else -> TextAlign.Center
        }
        Text(
            text = localTime.toString().substring(0, 5),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = textAlign,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            textAlign = textAlign,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EventItemPreview() {
    WorkCalendarTheme {
        EventItem(
            DataEvent(
                summary = "Lorem ipsum dolor sit amet",
                start = Clock.System.now(),
                end = Clock.System.now().plus(1L.toDuration(DurationUnit.HOURS)),
                attendees = listOf("Mark", "john"),
                type = "default",
            ).toLocalEvent()
        )
    }
}

