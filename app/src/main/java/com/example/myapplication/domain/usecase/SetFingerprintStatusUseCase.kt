package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.SettingsRepository

class SetFingerprintStatusUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(value: Boolean) {
        return repository.saveFingerprintPreference(value)
    }
}