package eu.acolombo.work.calendar.events.data.source

import kotlinx.coroutines.flow.Flow

interface LocationsDataSource {
    fun getLocations(): Flow<List<String?>>
    suspend fun setLocation(index: Int, zoneId: String?)
}
