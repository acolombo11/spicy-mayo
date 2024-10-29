package eu.acolombo.work.calendar.events.domain

import eu.acolombo.work.calendar.events.data.source.LocationsRepository

class GetSavedTimezonesUseCase(
    private val repository: LocationsRepository,
) {
    operator fun invoke(): List<String> = repository.getLocationsZoneIds()
}