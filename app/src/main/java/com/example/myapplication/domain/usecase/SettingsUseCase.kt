package com.example.myapplication.domain.usecase


data class SettingsUseCase(
    val setDarkModeStatusUseCase: SetDarkModeStatusUseCase,
    val getDarkModeStatusUseCase: GetDarkModeStatusUseCase,
    val getFingerprintStatusUseCase: GetFingerprintStatusUseCase,
    val setFingerprintStatusUseCase: SetFingerprintStatusUseCase,
    val setLanguagePrefUseCase: SetLanguagePrefUseCase,
    val getLanguagePrefUseCase: GetLanguagePrefUseCase
)