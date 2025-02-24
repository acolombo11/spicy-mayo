package eu.acolombo.work.calendar.di

import eu.acolombo.work.calendar.events.di.eventsModules
import eu.acolombo.work.calendar.network.di.networkModule
import eu.acolombo.work.calendar.storage.di.storageModule

val koinModules = networkModule + storageModule + eventsModules