package eu.acolombo.work.calendar.events.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import eu.acolombo.work.calendar.events.data.model.Event as DataEvent

data class Event(
    val summary: String,
    val start: LocalTime?,
    val end: LocalTime?,
    val duration: Duration,
    val attendees: List<String>,
    val type: Type?,
) {
    enum class Type(val value: String) {
        Default("default"),
        OutOfOffice("outOfOffice"),
        WorkingLocation("workingLocation"),
        FocusTime("focusTime"),
    }
}

fun DataEvent.toLocalEvent(): Event = Event(
    summary = summary.orEmpty(),
    start = start?.toLocalTime(),
    end = end?.toLocalTime(),
    duration = start?.let { start -> end?.let { end -> end - start } } ?: Duration.INFINITE,
    attendees = attendees,
    type = Event.Type.entries.find { it.value == type },
)

private fun Instant.toLocalTime(): LocalTime = toLocalDateTime(TimeZone.currentSystemDefault()).time
