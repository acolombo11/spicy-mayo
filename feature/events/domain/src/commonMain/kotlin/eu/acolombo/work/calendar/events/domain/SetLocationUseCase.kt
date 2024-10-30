package eu.acolombo.work.calendar.events.domain

import eu.acolombo.work.calendar.events.data.source.LocationsRepository

class SetLocationUseCase(
    private val repository: LocationsRepository,
) {
    operator fun invoke(index: Int, timeZoneId: String) = repository.setLocation(index, timeZoneId)
}