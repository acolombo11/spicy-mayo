package eu.acolombo.work.calendar.events.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.acolombo.work.calendar.design.theme.Spacing
import eu.acolombo.work.calendar.events.domain.model.Location
import eu.acolombo.work.calendar.events.util.TimeZoneFormatter
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import spicy_mayo.feature.events.presentation.generated.resources.Res
import spicy_mayo.feature.events.presentation.generated.resources.title_last_update
import spicy_mayo.feature.events.presentation.generated.resources.label_set_location

@Composable
fun TimeInformation(
    onOfficeClick: (index: Int) -> Unit,
    latest: Instant?,
    locations: List<Location?>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.widthIn(max = BottomSheetMaxWidth * WidthRatio),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column(
                modifier = Modifier
                    .weight(ContentHeight)
                    .padding(horizontal = Spacing.L.dp)
                    .padding(bottom = Spacing.S.dp),
            ) {
                Text(
                    text = latest.toTimeString(),
                    style = MaterialTheme.typography.displayLarge,
                )
                Text(
                    text = stringResource(Res.string.title_last_update),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(DividerHeight)
                    .padding(vertical = Spacing.M.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = Spacing.M.dp),
            ) {
                locations.take(n = 2, or = { null }).forEachIndexed { index, office ->
                    LocationsInformation(
                        latest = latest,
                        location = office,
                        onClick = { onOfficeClick(index) },
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationsInformation(
    latest: Instant?,
    location: Location?,
    onClick: () -> Unit,
) {
    Box {
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable(onClick = onClick)
                .padding(bottom = Spacing.XS.dp)
                .padding(horizontal = Spacing.M.dp, vertical = Spacing.XS.dp),
        ) {
            Box(contentAlignment = Alignment.TopEnd) {
                Text(
                    text = latest.toTimeString(timeZone = location?.timezone),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }

            Text(
                text = location?.zoneId?.let {
                    TimeZoneFormatter.getLastSegment(timeZoneId = it)
                } ?: stringResource(Res.string.label_set_location),
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
        latest?.let { time ->
            val timeZone = TimeZone.currentSystemDefault()
            val here = time.toLocalDateTime(timeZone)
            val there = time.toLocalDateTime(location?.timezone ?: timeZone)
            val difference: DatePeriod = (there.date - here.date)
            difference.days
        }?.takeIf { it != 0 }?.let { delta ->
            val sign = if (delta > 0) "+" else ""
            Badge(
                modifier = Modifier.align(Alignment.TopEnd),
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Text(
                    text = "$sign$delta",
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

private fun <T, R : T> Iterable<T>.take(n: Int, or: (index: Int) -> R): List<T> {
    val taken = take(n)
    return taken + List(n - taken.size) { or(it) }
}

private fun Instant?.toTimeString(
    timeZone: TimeZone? = TimeZone.currentSystemDefault(),
    maxLength: Int = 5,
    replacement: String = "--:--",
): String = timeZone?.let {
    this?.toLocalDateTime(timeZone)
        ?.time
        ?.toString()
        ?.substring(0, maxLength)
} ?: replacement

// copy of internal androidx.compose.material3.BottomSheetMaxWidth
private val BottomSheetMaxWidth = 640.dp
private const val DividerHeight = .75f
private const val ContentHeight = .6f
private const val WidthRatio = .9f
