package eu.acolombo.work.calendar.events.data.source.remote

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
) : EventsDataSource {
    companion object {
        private const val BASE_URL = "https://script.google.com/macros/s"
        private const val DEPLOY_ID="AKfycbwInsTRFg6sqsp8zDiwDQjmh5QRZ9GtPZ4fo1xWW1H0wi_jlu064IB1BErvnuaCqRYI"
        private const val API_KEY="bo5K!H9HHXwjpk9uXA!QeBfjt^XST6vwNzzUu22yoH3PrG2H"
    }

    override suspend fun getEvents(date: LocalDate): List<Event> = withContext(dispatcher) {
        client.get("$BASE_URL/$DEPLOY_ID/exec") {
            parameter("date", date)
            parameter("key", API_KEY)
        }.body<List<Event>>()
    }
}
