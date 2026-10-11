package eu.acolombo.work.calendar.storage.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import eu.acolombo.work.calendar.storage.createDataStore
import org.koin.dsl.module
import java.io.File

actual val dataStoreModule = module {
    single<DataStore<Preferences>> {
        createDataStore(producePath = { File(System.getProperty("java.io.tmpdir"), "spicy-mayo.preferences_pb").absolutePath })
    }
}
