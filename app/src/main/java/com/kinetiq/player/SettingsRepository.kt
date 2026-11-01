package com.kinetiq.player

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "kinetiq_settings"
)

class SettingsRepository(context: Context) {
    private val dataStore = context.applicationContext.settingsDataStore
    private val themeKey = stringPreferencesKey("theme_mode")

    val themeMode: Flow<ThemeMode> = dataStore.data
        .catch { error ->
            if (error is IOException) {
                KinetiqLogger.warn("SettingsRepository", "Could not read settings; using defaults", error)
                emit(androidx.datastore.preferences.core.emptyPreferences())
            } else {
                throw error
            }
        }
        .map { preferences -> ThemeMode.fromStorageValue(preferences[themeKey]) }

    suspend fun setThemeMode(mode: ThemeMode) {
        try {
            dataStore.edit { preferences ->
                preferences[themeKey] = mode.storageValue
            }
        } catch (error: Exception) {
            KinetiqLogger.error("SettingsRepository", "Could not save theme preference", error)
            throw error
        }
    }
}
