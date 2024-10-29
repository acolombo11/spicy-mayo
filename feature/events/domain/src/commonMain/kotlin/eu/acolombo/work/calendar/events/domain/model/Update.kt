package eu.acolombo.work.calendar.events.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Update(
    val latest: Instant,
) {
    val time = latest.toLocalDateTime(TimeZone.currentSystemDefault()).time
}

