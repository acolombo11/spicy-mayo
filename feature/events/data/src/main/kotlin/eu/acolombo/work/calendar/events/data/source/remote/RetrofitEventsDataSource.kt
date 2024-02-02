package eu.acolombo.work.calendar.events.data.source.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import eu.acolombo.work.calendar.events.data.model.Event
import eu.acolombo.work.calendar.events.data.source.EventsDataSource
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val SECRET_DEPLOYMENT_ID =
    "AKfycbzcS6_biq2cAY5HAB36DJ4VeBYgBltOcpfX4Jrqaoqok3qQLBkuZgpTR1kQ0KVg9wXD"
private const val SECRET_KEY =
    "ryXExF2T\$UNomSk%eCCUFYaN@ymU3KGjWLbC^yQcKDFjc54DHySVAW6kF%7k"

internal interface RetrofitEventsApi {
    @GET(value = "$SECRET_DEPLOYMENT_ID/exec")
    suspend fun getEvents(
        @Query("date") date: LocalDate,
    ): List<Event>
}

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
}

private val retrofit = Retrofit.Builder()
    .baseUrl("https://script.google.com/macros/s/")
    .client(
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("key", SECRET_KEY)
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    )
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()
    .create(RetrofitEventsApi::class.java)

internal class RetrofitEventsDataSource(
    private val networkApi: RetrofitEventsApi = retrofit,
) : EventsDataSource {

    override suspend fun getEvents(date: LocalDate): List<Event> = networkApi.getEvents(date = date)
}
