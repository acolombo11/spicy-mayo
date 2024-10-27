package eu.acolombo.work.calendar.events.data.di

import eu.acolombo.work.calendar.events.data.source.DefaultEventsRepository
import eu.acolombo.work.calendar.events.data.source.EventsDataSource
import eu.acolombo.work.calendar.events.data.source.EventsRepository
import eu.acolombo.work.calendar.events.data.source.remote.RemoteEventsDataSource
import org.koin.dsl.module

val dataModule = module {
    single<EventsDataSource> { RemoteEventsDataSource(get(), get()) }
    single<EventsRepository> { DefaultEventsRepository(get(), get()) }
}