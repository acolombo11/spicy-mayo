package eu.acolombo.work.calendar.events.screen.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import eu.acolombo.work.calendar.events.data.model.Event as DataEvent

data class Event(
    val summary: String,
    val start: LocalTime,
    val end: LocalTime,
    val duration: Duration,
    val attendees: List<String>,
)

fun DataEvent.toLocalEvent(): Event = Event(
    summary = summary,
    start = start.toLocalTime(),
    end = end.toLocalTime(),
    duration = end - start,
    attendees = attendees,
)

private fun Instant.toLocalTime(): LocalTime = toLocalDateTime(TimeZone.currentSystemDefault()).time
