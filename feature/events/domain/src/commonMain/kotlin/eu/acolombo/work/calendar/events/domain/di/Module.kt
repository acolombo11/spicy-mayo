package eu.acolombo.work.calendar.events.domain.di

import eu.acolombo.work.calendar.events.domain.GetEventsUseCase
import eu.acolombo.work.calendar.events.domain.GetLocationsUseCase
import eu.acolombo.work.calendar.events.domain.SetLocationUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetEventsUseCase(get()) }
    single { GetLocationsUseCase(get()) }
    single { SetLocationUseCase(get()) }
}