package eu.acolombo.work.calendar.events.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val summary: String,
    val start: Instant,
    val end: Instant,
    val attendees: List<String> = emptyList(),
    val type: String,
)