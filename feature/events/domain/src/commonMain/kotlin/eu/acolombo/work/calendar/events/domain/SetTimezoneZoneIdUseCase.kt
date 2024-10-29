package eu.acolombo.work.calendar.events.domain

import eu.acolombo.work.calendar.events.data.source.LocationsRepository

class SetTimezoneZoneIdUseCase(
    private val repository: LocationsRepository,
) {
    operator fun invoke(index: Int, zoneId: String) = repository.setLocation(index, zoneId)
}