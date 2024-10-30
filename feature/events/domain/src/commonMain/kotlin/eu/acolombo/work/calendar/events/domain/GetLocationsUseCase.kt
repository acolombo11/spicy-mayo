package eu.acolombo.work.calendar.events.domain

import eu.acolombo.work.calendar.events.data.source.LocationsRepository
import eu.acolombo.work.calendar.events.domain.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLocationsUseCase(
    private val repository: LocationsRepository,
) {
    operator fun invoke(): Flow<List<Location?>> = repository.getLocations().map { timeZoneIds ->
        timeZoneIds.map { timeZoneId ->
            timeZoneId?.let { Location(it) }
        }
    }
}