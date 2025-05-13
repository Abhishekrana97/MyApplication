package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.SettingsRepository


class SetLanguagePrefUseCase(private val repository: SettingsRepository) {

    suspend operator fun invoke(value: Int) {
        return repository.saveLanguagePreference(value)
    }
}