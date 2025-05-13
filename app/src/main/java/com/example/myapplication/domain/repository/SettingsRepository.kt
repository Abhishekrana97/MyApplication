package com.example.myapplication.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getDarkModePreference(): Flow<Boolean>
    suspend fun saveDarkModePreference(value: Boolean)
    fun getFingerprintPreference(): Flow<Boolean>
    suspend fun saveFingerprintPreference(value: Boolean)
    fun getLanguagePreference(): Flow<Int>
    suspend fun saveLanguagePreference(value: Int)
}