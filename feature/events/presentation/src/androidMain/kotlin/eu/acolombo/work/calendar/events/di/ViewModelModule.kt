package eu.acolombo.work.calendar.events.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import eu.acolombo.work.calendar.events.presentations.EventsViewModel

actual val viewModelModule = module {
    viewModelOf(::EventsViewModel)
}