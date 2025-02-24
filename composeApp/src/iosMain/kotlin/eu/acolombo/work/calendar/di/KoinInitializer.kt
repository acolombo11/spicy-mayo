package eu.acolombo.work.calendar.di

import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(koinModules)
        }
    }
}