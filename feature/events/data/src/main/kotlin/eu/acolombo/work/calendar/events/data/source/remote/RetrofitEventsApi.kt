package eu.acolombo.work.calendar.events.data.source.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    explicitNulls = false
}

internal val retrofitEventsApi = Retrofit.Builder()
    .baseUrl("https://script.google.com/macros/s/")
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()
    .create(EventsApi::class.java)

