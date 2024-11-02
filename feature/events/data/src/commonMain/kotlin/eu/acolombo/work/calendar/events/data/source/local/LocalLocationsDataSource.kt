package eu.acolombo.work.calendar.events.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import eu.acolombo.work.calendar.events.data.source.LocationsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class LocalLocationsDataSource(
    private val dataStore: DataStore<Preferences>,
) : LocationsDataSource {

    private val key = stringPreferencesKey(LOCATIONS_KEY)

    private val Preferences.locationsList: List<String?>
        get() = get(key)?.let { data ->
            Json.decodeFromString<List<String?>>(data)
        } ?: listOf(null, null)

    override fun getLocations(): Flow<List<String?>> = dataStore.data.map { preferences ->
        preferences.locationsList
    }

    override suspend fun setLocation(index: Int, zoneId: String?) {
        dataStore.edit { preferences ->
            val newList = preferences.locationsList.toMutableList()
            if (newList.lastIndex < index) {
                newList.addAll(List(newList.lastIndex - index) { null })
            }
            newList[index] = zoneId
            preferences[key] = Json.encodeToString(newList)
        }
    }

    companion object {
        private const val LOCATIONS_KEY: String = "locations"
    }

}