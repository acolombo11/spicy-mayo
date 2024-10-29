package eu.acolombo.work.calendar.events.model

import kotlinx.datetime.TimeZone

data class Office(val zoneId: String) {
    val name = zoneId.split("/").last()
    val timezone = TimeZone.of(zoneId)
}