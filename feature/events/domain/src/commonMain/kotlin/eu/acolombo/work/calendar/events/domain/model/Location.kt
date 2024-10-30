package eu.acolombo.work.calendar.events.domain.model

import kotlinx.datetime.TimeZone

data class Location(val zoneId: String) {
    val name = zoneId.split("/").last()
    val timezone = TimeZone.of(zoneId)
}