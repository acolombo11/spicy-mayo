package eu.acolombo.work.calendar.events.data.source

interface LocationsRepository {
    fun getLocationsZoneIds(): List<String>
    fun setLocation(index: Int, zoneId: String)
}
