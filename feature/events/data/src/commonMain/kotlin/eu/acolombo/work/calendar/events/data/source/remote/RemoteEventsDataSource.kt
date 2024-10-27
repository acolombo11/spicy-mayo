package eu.acolombo.work.calendar.events.data.source.remote

import eu.acolombo.work.calendar.events.data.Secrets
import eu.acolombo.work.calendar.events.data.model.Event
import eu.acolombo.work.calendar.events.data.source.EventsDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

internal class RemoteEventsDataSource(
    private val client: HttpClient,
    private val dispatcher: CoroutineDispatcher,
    private val baseUrl: String = "https://script.google.com/macros/s",
) : EventsDataSource {
    override suspend fun getEvents(date: LocalDate): List<Event> = withContext(dispatcher) {
        client.get("$baseUrl/${Secrets.DeployId}/exec") {
            parameter("date", date)
            parameter("key", Secrets.ApiKey)
        }.body<List<Event>>()
    }
}
