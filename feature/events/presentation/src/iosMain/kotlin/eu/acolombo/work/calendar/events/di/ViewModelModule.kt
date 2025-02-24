package eu.acolombo.work.calendar.events.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import eu.acolombo.work.calendar.events.presentations.EventsViewModel

actual val viewModelModule = module {
    singleOf(::EventsViewModel)
}