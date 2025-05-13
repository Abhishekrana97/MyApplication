package com.example.myapplication.presentation.viewmodel

import com.example.myapplication.domain.usecase.GetDarkModeStatusUseCase
import com.example.myapplication.domain.usecase.GetFingerprintStatusUseCase
import com.example.myapplication.domain.usecase.GetLanguagePrefUseCase
import com.example.myapplication.domain.usecase.SetDarkModeStatusUseCase
import com.example.myapplication.domain.usecase.SetFingerprintStatusUseCase
import com.example.myapplication.domain.usecase.SetLanguagePrefUseCase
import com.example.myapplication.domain.usecase.SettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SettingsScreenViewModelTest {

    private lateinit var viewModel: SettingsScreenViewModel

    private val dispatcher = StandardTestDispatcher()

    private lateinit var settingsUseCase: SettingsUseCase

    @Before
    fun setUp() {
        val setDarkModeStatusUseCase = mock(SetDarkModeStatusUseCase::class.java)
        val getDarkModeStatusUseCase = mock(GetDarkModeStatusUseCase::class.java)
        val setFingerprintStatusUseCase = mock(SetFingerprintStatusUseCase::class.java)
        val getFingerprintStatusUseCase = mock(GetFingerprintStatusUseCase::class.java)
        val setLanguagePrefUseCase = mock(SetLanguagePrefUseCase::class.java)
        val getLanguagePrefUseCase = mock(GetLanguagePrefUseCase::class.java)
        settingsUseCase = SettingsUseCase(
            setDarkModeStatusUseCase,
            getDarkModeStatusUseCase,
            getFingerprintStatusUseCase,
            setFingerprintStatusUseCase,
            setLanguagePrefUseCase,
            getLanguagePrefUseCase
        )
        viewModel = SettingsScreenViewModel(settingsUseCase)

        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun testGetDarkTheme() = runTest {
        // Given a dark theme status of true
        val darkModeFlow = MutableStateFlow(true)
        whenever(settingsUseCase.getDarkModeStatusUseCase()).thenReturn(darkModeFlow)

        // When getCurrentTheme is called
        viewModel.getCurrentTheme()

        dispatcher.scheduler.advanceUntilIdle()

        // Then the state should be updated
        assertTrue(viewModel.isDarkThemeEnabled.value)
    }

    @Test
    fun testGetFingerprintStatus() = runTest {
        // Given a fingerprint status of true
        val fingerprintFlow = MutableStateFlow(true)
        whenever(settingsUseCase.getFingerprintStatusUseCase()).thenReturn(fingerprintFlow)

        // When getFingerprintStatus is called
        viewModel.getFingerprintStatus()

        dispatcher.scheduler.advanceUntilIdle()

        // Then the state should be updated
        assertTrue(viewModel.isFingerprintEnabled.value)
    }

    @Test
    fun testSetDarkModeWithTrue() = runTest {
        // Given
        val darkMode = true

        // When
        viewModel.toggleTheme(darkMode)

        dispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(settingsUseCase.setDarkModeStatusUseCase).invoke(darkMode)
    }

    @Test
    fun testLanguage() = runTest {
        // Given
        val languageIndex = 0

        // When
        viewModel.setLanguagePref(languageIndex)

        dispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(settingsUseCase.setLanguagePrefUseCase).invoke(languageIndex)
    }

    @Test
    fun testGetLanguage() = runTest {
        // Given a fingerprint status of true
        val languagePref = MutableStateFlow(0)
        whenever(settingsUseCase.getLanguagePrefUseCase()).thenReturn(languagePref)

        // When getLanguagePref is called
        viewModel.getLanguagePref()

        dispatcher.scheduler.advanceUntilIdle()

        // Then the state should be updated
        assertEquals(viewModel.languagePref.value,0)
    }

    //
    @Test
    fun testFingerprintWithTrue() = runTest {
        // Given
        val fingerprintEnabled = true

        // When
        viewModel.toggleFingerprint(fingerprintEnabled)

        dispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(settingsUseCase.setFingerprintStatusUseCase).invoke(fingerprintEnabled)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
