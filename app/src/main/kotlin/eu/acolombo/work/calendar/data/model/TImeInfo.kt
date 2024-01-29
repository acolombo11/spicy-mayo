package eu.acolombo.work.calendar.data.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Update(private val date: Instant) {
    val latest = date.toLocalTime()
    val zones = Offices.entries.associateWith { date.toLocalTime(it.zoneId) }

    private fun Instant.toLocalTime(zoneId: String? = null): LocalTime = toLocalDateTime(
        timeZone = zoneId?.let { TimeZone.of(it) } ?: TimeZone.currentSystemDefault()
    ).time
}

enum class Offices(val zoneId: String) {
    Valencia(zoneId = "Europe/Madrid"),
    Dublin(zoneId = "Europe/Dublin"),
}

