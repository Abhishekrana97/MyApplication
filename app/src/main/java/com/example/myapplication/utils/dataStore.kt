package com.example.myapplication.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsPreference(private val context: Context) {

    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")
    private val FINGERPRINT_MODE_KEY = booleanPreferencesKey("fingerprint_mode_enabled")
    private val PREFERRED_LANGUAGE_KEY = intPreferencesKey("preferred_language")
    private val LOGIN_KEY = booleanPreferencesKey("login_key")
    private val PDF_PAGE = intPreferencesKey("pdf_page_key")

    val isDarkModeEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_MODE_KEY] ?: false // Default to light mode if not set
        }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDarkMode
        }
    }

    val isUserLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[LOGIN_KEY] ?: false // Default to light mode if not set
        }

    suspend fun setUserLogin(isLogin: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[LOGIN_KEY] = isLogin
        }
    }

    val isFingerprintEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[FINGERPRINT_MODE_KEY] ?: false // Default to fingerprint mode if not set
        }

    suspend fun setFingerprint(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[FINGERPRINT_MODE_KEY] = value
        }
    }

    // Function to save the preferred language
    suspend fun savePreferredLanguage(index: Int) {
        context.dataStore.edit { preferences ->
            preferences[PREFERRED_LANGUAGE_KEY] = index
        }
    }

    // Function to get the preferred language
    val getPreferredLanguage: Flow<Int> =
        context.dataStore.data
            .map { preferences ->
                preferences[PREFERRED_LANGUAGE_KEY] ?: 0
            }


    suspend fun savePdfPage(page: Int) {
        context.dataStore.edit { preferences ->
            preferences[PDF_PAGE] = page
        }
    }

    val getPdfPage: Flow<Int> =
        context.dataStore.data
            .map { preferences ->
                preferences[PDF_PAGE] ?: 0
            }

}