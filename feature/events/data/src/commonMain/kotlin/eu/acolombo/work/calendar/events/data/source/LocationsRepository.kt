package eu.acolombo.work.calendar.events.data.source

import kotlinx.coroutines.flow.Flow

interface LocationsRepository {
    fun getLocations(): Flow<List<String>>
    fun setLocation(index: Int, zoneId: String)
}
