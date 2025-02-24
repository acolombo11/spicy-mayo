package eu.acolombo.work.calendar.events.di

import eu.acolombo.work.calendar.events.data.di.dataModule
import eu.acolombo.work.calendar.events.domain.di.domainModule

val eventsModules = listOf(
    dataModule,
    domainModule,
    viewModelModule,
)