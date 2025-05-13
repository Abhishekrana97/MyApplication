package com.example.myapplication.data.repositoryImpl

import com.example.myapplication.domain.repository.SettingsRepository
import com.example.myapplication.utils.SettingsPreference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val settingsPreference: SettingsPreference) :
    SettingsRepository {
    override fun getDarkModePreference(): Flow<Boolean> {
        return settingsPreference.isDarkModeEnabled
    }

    override suspend fun saveDarkModePreference(value: Boolean) {
       settingsPreference.setDarkMode(value)
    }

    override fun getFingerprintPreference(): Flow<Boolean> {
        return settingsPreference.isFingerprintEnabled

    }

    override suspend fun saveFingerprintPreference(value: Boolean) {
        settingsPreference.setFingerprint(value)

    }

    override fun getLanguagePreference(): Flow<Int> {
        return settingsPreference.getPreferredLanguage
    }

    override suspend fun saveLanguagePreference(value: Int) {
        settingsPreference.savePreferredLanguage(value)
    }
}