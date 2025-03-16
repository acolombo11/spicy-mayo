package eu.acolombo.work.calendar.events.data.di

import eu.acolombo.work.calendar.events.data.source.DefaultEventsRepository
import eu.acolombo.work.calendar.events.data.source.DefaultLocationsRepository
import eu.acolombo.work.calendar.events.data.source.EventsDataSource
import eu.acolombo.work.calendar.events.data.source.EventsRepository
import eu.acolombo.work.calendar.events.data.source.LocationsDataSource
import eu.acolombo.work.calendar.events.data.source.LocationsRepository
import eu.acolombo.work.calendar.events.data.source.local.LocalLocationsDataSource
import eu.acolombo.work.calendar.events.data.source.remote.FakeEventsDataSource
import eu.acolombo.work.calendar.events.data.source.remote.RemoteEventsDataSource
import org.koin.dsl.module

val dataModule = module {
    single<EventsDataSource> { FakeEventsDataSource(get()) }
    single<EventsRepository> { DefaultEventsRepository(get(), get()) }
    single<LocationsDataSource> { LocalLocationsDataSource(get()) }
    single<LocationsRepository> { DefaultLocationsRepository(get()) }
}