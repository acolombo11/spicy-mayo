package eu.acolombo.work.calendar.events.data.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class DefaultLocationsRepository : LocationsRepository { // TODO Add local storage
    private val locationsZonesIds = MutableStateFlow(
        listOf("Europe/Madrid", "Europe/Dublin"), // TODO Replace with empty list
    )

    override fun getLocations(): Flow<List<String>> = locationsZonesIds

    override fun setLocation(index: Int, zoneId: String) {
        locationsZonesIds.update { it.toMutableList().apply { this[index] = zoneId } }
    }
}