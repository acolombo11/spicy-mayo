package eu.acolombo.work.calendar.events.data.source.remote

import eu.acolombo.work.calendar.events.data.BuildConfig
import eu.acolombo.work.calendar.events.data.model.Event
import kotlinx.datetime.LocalDate
import retrofit2.http.GET
import retrofit2.http.Query

internal interface EventsApi {
    @GET(value = "${BuildConfig.SPICY_DEPLOY_ID}/exec")
    suspend fun getEvents(
        @Query("date") date: LocalDate,
        @Query("key") apiKey: String = BuildConfig.SPICY_QUERY_KEY,
    ): List<Event>
}