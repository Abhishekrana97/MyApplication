package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(private val settingsUseCase: SettingsUseCase) :
    ViewModel() {

    // State for dark mode
    val isDarkThemeEnabled = MutableStateFlow(false)
    val isFingerprintEnabled = MutableStateFlow(false)
    val languagePref = MutableStateFlow(0)


    fun getCurrentTheme() {
        viewModelScope.launch {
            settingsUseCase.getDarkModeStatusUseCase().collect { value ->
                isDarkThemeEnabled.value = value
            }
        }
    }

    fun getLanguagePref() {
        viewModelScope.launch {
            settingsUseCase.getLanguagePrefUseCase().collect { value ->
                languagePref.value = value
            }
        }
    }

    fun getFingerprintStatus() {
        viewModelScope.launch {
            settingsUseCase.getFingerprintStatusUseCase().collect { value ->
                isFingerprintEnabled.value = value
            }
        }
    }

    // Toggle dark mode preference
    fun toggleTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingsUseCase.setDarkModeStatusUseCase(isDarkMode)
        }
    }

    fun toggleFingerprint(value: Boolean) {
        viewModelScope.launch {
            settingsUseCase.setFingerprintStatusUseCase(value)
        }
    }

    fun setLanguagePref(value:Int){
        viewModelScope.launch {
            settingsUseCase.setLanguagePrefUseCase(value)
        }
    }


}