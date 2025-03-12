package eu.acolombo.work.calendar.events.di

import eu.acolombo.work.calendar.events.domain.di.modules
import eu.acolombo.work.calendar.events.presentation.EventsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val eventsModules = modules + module {
    viewModel { EventsViewModel(get(), get(), get()) }
}