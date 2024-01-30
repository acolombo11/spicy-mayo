package eu.acolombo.work.calendar.data.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class Event(
    val summary: String,
    val start: Instant,
    val end: Instant,
    val attendees: List<String>,
)

data class LocalEvent(
    val summary: String,
    val start: LocalTime,
    val end: LocalTime,
    val duration: Duration,
    val attendees: List<String>,
)

fun Event.toLocalEvent(): LocalEvent = LocalEvent(
    summary = summary,
    start = start.toLocalTime(),
    end = end.toLocalTime(),
    duration = end - start,
    attendees = attendees,
)

private fun Instant.toLocalTime(): LocalTime = toLocalDateTime(TimeZone.currentSystemDefault()).time
