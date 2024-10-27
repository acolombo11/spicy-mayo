package eu.acolombo.work.calendar.events.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import eu.acolombo.work.calendar.design.theme.Spacing
import eu.acolombo.work.calendar.design.theme.WorkCalendarTheme
import eu.acolombo.work.calendar.events.domain.model.Event
import spicy_mayo_multiplatform.feature.events.ui.generated.resources.Res
import spicy_mayo_multiplatform.feature.events.ui.generated.resources.label_duration_all_day
import spicy_mayo_multiplatform.feature.events.ui.generated.resources.label_end
import spicy_mayo_multiplatform.feature.events.ui.generated.resources.label_start
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun EventItem(
    event: Event,
    modifier: Modifier = Modifier,
    colors: EventItemColors = EventItemColors(
        containerColor = when {
            event.type != Event.Type.Default -> MaterialTheme.colorScheme.primary
            event.duration == Duration.INFINITE -> MaterialTheme.colorScheme.secondary
            else -> MaterialTheme.colorScheme.surfaceVariant
        },
        accentColor = when {
            event.type != Event.Type.Default -> MaterialTheme.colorScheme.surfaceVariant
            event.duration == Duration.INFINITE -> MaterialTheme.colorScheme.secondaryContainer
            else -> MaterialTheme.colorScheme.primary
        },
    ),
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = colors.containerColor),
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Column(modifier = Modifier.padding(horizontal = Spacing.L, vertical = Spacing.M)) {
            Text(
                modifier = Modifier.fillMaxWidth(.6f),
                text = event.summary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                minLines = 2,
                color = colors.onContainerColor,
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                modifier = Modifier
                    .basicMarquee()
                    .padding(vertical = Spacing.S)
                    .alpha(.5f),
                text = event.attendees.joinToString(),
                maxLines = 1,
                color = colors.onContainerColor,
                style = MaterialTheme.typography.bodySmall,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                event.start?.let { start ->
                    LabeledTime(
                        localTime = start,
                        label = stringResource(Res.string.label_start),
                        alignment = Alignment.Start,
                        color = colors.onContainerColor,
                    )
                } ?: Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(colors.accentColor)
                        .padding(horizontal = Spacing.M, vertical = Spacing.S),
                    text = if (event.duration >= 1.toDuration(DurationUnit.DAYS)) {
                        stringResource(Res.string.label_duration_all_day)
                    } else {
                        event.duration.toString(DurationUnit.MINUTES)
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color = colors.onAccentColor,
                    maxLines = 1,
                )
                event.end?.let { end ->
                    LabeledTime(
                        localTime = end,
                        label = stringResource(Res.string.label_end),
                        alignment = Alignment.End,
                        color = colors.onContainerColor,
                    )
                } ?: Spacer(modifier = Modifier.weight(1f))
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
    color: Color = Color.Unspecified,
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
            color = color,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = textAlign,
        )
        Text(
            text = label,
            color = color,
            style = MaterialTheme.typography.labelMedium,
            textAlign = textAlign,
        )
    }
}

data class EventItemColors(
    val containerColor: Color,
    val accentColor: Color,
    val onContainerColor: Color = Color.Unspecified,
    val onAccentColor: Color = containerColor,
)

@Preview
@Composable
fun EventItemPreview() {
    WorkCalendarTheme {
        EventItem(
            Event(
                summary = "Lorem ipsum dolor sit amet",
                start = Clock.System.now()
                    .toLocalDateTime(TimeZone.UTC).time,
                end = Clock.System.now()
                    .plus(1L.toDuration(DurationUnit.HOURS))
                    .toLocalDateTime(TimeZone.UTC).time,
                attendees = listOf("Mark", "john"),
                type = Event.Type.Default,
                duration = 1L.toDuration(DurationUnit.HOURS),
            )
        )
    }
}

