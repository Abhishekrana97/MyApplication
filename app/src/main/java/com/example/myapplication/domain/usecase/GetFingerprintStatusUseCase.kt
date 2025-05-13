package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetFingerprintStatusUseCase(private val repository: SettingsRepository) {

    suspend operator fun invoke(): Flow<Boolean> {
        return repository.getFingerprintPreference()
    }
}