package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow


class GetLanguagePrefUseCase(private val repository: SettingsRepository) {

    suspend operator fun invoke(): Flow<Int> {
        return repository.getLanguagePreference()
    }
}