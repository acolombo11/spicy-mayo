package eu.acolombo.work.calendar.events.util

object TimeZoneFormatter {
    fun getSearchName(timeZoneId: String) = timeZoneId.replace("/", " ")
    fun getDisplayName(timeZoneId: String) = timeZoneId.replace("_", " ")
    fun getLastSegment(timeZoneId: String) = getDisplayName(timeZoneId).split("/").last()
}