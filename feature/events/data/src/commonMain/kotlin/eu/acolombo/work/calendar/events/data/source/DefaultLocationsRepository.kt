package eu.acolombo.work.calendar.events.data.source

import kotlinx.coroutines.flow.Flow

class DefaultLocationsRepository(
    private val dataSource: LocationsDataSource,
) : LocationsRepository {
    override fun getLocations(): Flow<List<String?>> = dataSource.getLocations()

    override suspend fun setLocation(index: Int, zoneId: String) {
        dataSource.setLocation(index, zoneId)
    }
}