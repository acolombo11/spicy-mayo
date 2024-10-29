package eu.acolombo.work.calendar.events.data.source.remote

import eu.acolombo.work.calendar.events.data.Secrets
import eu.acolombo.work.calendar.events.data.model.Event
import eu.acolombo.work.calendar.events.data.model.NoConnectionException
import eu.acolombo.work.calendar.events.data.model.DeploymentErrorException
import eu.acolombo.work.calendar.events.data.model.Error
import eu.acolombo.work.calendar.events.data.model.ServerErrorException
import eu.acolombo.work.calendar.events.data.source.EventsDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.JsonConvertException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

internal class RemoteEventsDataSource(
    private val client: HttpClient,
    private val dispatcher: CoroutineDispatcher,
    private val baseUrl: String = "https://script.google.com/macros/s",
) : EventsDataSource {
    override suspend fun <T, R> getData(param: T): List<Event> = withContext(dispatcher) {
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
