package eu.acolombo.work.calendar.events.data.source.remote

import eu.acolombo.work.calendar.events.data.Secrets
import eu.acolombo.work.calendar.events.data.model.Event
import eu.acolombo.work.calendar.events.data.source.EventsDataSource
import eu.acolombo.work.calendar.network.DeploymentErrorException
import eu.acolombo.work.calendar.network.NoConnectionException
import eu.acolombo.work.calendar.network.ServerErrorException
import eu.acolombo.work.calendar.network.data.Error
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.io.IOException

internal class RemoteEventsDataSource(
    private val client: HttpClient,
    private val dispatcher: CoroutineDispatcher,
    private val baseUrl: String = "https://script.google.com/macros/s",
) : EventsDataSource {
    // If you get Unresolved reference 'Secrets' compilation error
    // check the README.md file and make sure you filled the /secrets.properties file
    override suspend fun getEvents(date: LocalDate): List<Event> = withContext(dispatcher) {
        try {
            val response = client.get("$baseUrl/${Secrets.DeployId}/exec") {
                parameter("date", date)
                parameter("key", Secrets.ApiKey)
            }
            try {
                response.body<List<Event>>()
            } catch (exception: JsonConvertException) {
                throw ServerErrorException(response.body<Error>(), exception.message)
            } catch (exception: NoTransformationFoundException) {
                throw DeploymentErrorException(exception.message)
            }
        } catch (exception: IOException) {
            throw NoConnectionException(exception.message)
        }
    }
}
