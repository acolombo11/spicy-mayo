package eu.acolombo.work.calendar.events.domain.di

import eu.acolombo.work.calendar.events.domain.GetEventsUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetEventsUseCase(get()) }
}