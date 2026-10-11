package eu.acolombo.work.calendar.events.domain.model

import kotlin.time.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Update(
    val latest: Instant,
) {
    val time = latest.toLocalDateTime(TimeZone.currentSystemDefault()).time
}
