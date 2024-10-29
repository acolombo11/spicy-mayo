package eu.acolombo.work.calendar.events.data.source

class DefaultLocationsRepository : LocationsRepository { // TODO Add local storage
    private var locationsZonesIds = mutableListOf("Europe/Madrid", "Europe/Dublin") // TODO Replace with empty list

    override fun getLocationsZoneIds(): List<String> = locationsZonesIds

    override fun setLocation(index: Int, zoneId: String) {
        locationsZonesIds.set(index, zoneId)
    }
}