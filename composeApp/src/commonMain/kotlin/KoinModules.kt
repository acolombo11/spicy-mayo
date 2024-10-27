import feature.layers.data.source.DefaultEventsRepository
import feature.layers.data.source.EventsDataSource
import feature.layers.data.source.EventsRepository
import feature.layers.data.source.remote.RemoteEventsDataSource
import feature.layers.domain.GetEventsUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.serialization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private val httpModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                serialization(
                    contentType = ContentType.Application.Json,
                    format = Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        explicitNulls = false
                    },
                )
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }
}

private val eventsModule = module {
    single<EventsDataSource> { (RemoteEventsDataSource(get())) }
    single<EventsRepository> { DefaultEventsRepository(get(), Dispatchers.IO) }
}

private val useCasesModule = module {
    single { GetEventsUseCase(get()) }
}

val sharedModules = listOf(
    httpModule,
    eventsModule,
    useCasesModule,
)