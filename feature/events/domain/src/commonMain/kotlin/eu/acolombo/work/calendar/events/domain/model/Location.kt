package eu.acolombo.work.calendar.events.domain.model

import kotlinx.datetime.TimeZone

data class Location(val zoneId: String) {
    val timezone = TimeZone.of(zoneId)
}
