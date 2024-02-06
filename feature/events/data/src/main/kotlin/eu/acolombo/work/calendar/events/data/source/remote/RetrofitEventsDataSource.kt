package eu.acolombo.work.calendar.events.data.source.remote


import eu.acolombo.work.calendar.events.data.model.Event
import eu.acolombo.work.calendar.events.data.source.EventsDataSource
import kotlinx.datetime.LocalDate

internal class RetrofitEventsDataSource(
    private val networkApi: EventsApi = retrofitEventsApi,
) : EventsDataSource {
    override suspend fun getEvents(date: LocalDate): List<Event> = networkApi.getEvents(date = date)
}
