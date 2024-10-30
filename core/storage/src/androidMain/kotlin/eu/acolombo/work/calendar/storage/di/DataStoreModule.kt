package eu.acolombo.work.calendar.storage.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import eu.acolombo.work.calendar.storage.createDataStore
import org.koin.dsl.module

actual val dataStoreModule = module {
    single<DataStore<Preferences>> { createDataStore(context = get()) }
}