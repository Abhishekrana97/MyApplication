package com.example.myapplication.domain.usecase

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import kotlin.test.Test

class SettingsUseCaseTest {

    private val setDarkModeStatusUseCase: SetDarkModeStatusUseCase = mock()
    private val getDarkModeStatusUseCase: GetDarkModeStatusUseCase = mock()
    private val getFingerprintStatusUseCase: GetFingerprintStatusUseCase = mock()
    private val setFingerprintStatusUseCase: SetFingerprintStatusUseCase = mock()
    private val setLanguagePrefUseCase: SetLanguagePrefUseCase = mock()
    private val getLanguagePrefUseCase: GetLanguagePrefUseCase = mock()


    private lateinit var settingsUseCase: SettingsUseCase


    @Before
    fun setUp() {
        settingsUseCase = SettingsUseCase(
            setDarkModeStatusUseCase,
            getDarkModeStatusUseCase,
            getFingerprintStatusUseCase,
            setFingerprintStatusUseCase,
            setLanguagePrefUseCase,
            getLanguagePrefUseCase
        )
    }

    @Test
    fun testSetDarkMode() = runTest {
        val darkModeStatus = true

        settingsUseCase.setDarkModeStatusUseCase(darkModeStatus)

        verify(setDarkModeStatusUseCase, Mockito.times(1)).invoke(darkModeStatus)
    }

    @Test
    fun testFingerprintStatus() = runTest {
        val status = true

        settingsUseCase.setFingerprintStatusUseCase(status)

        verify(setFingerprintStatusUseCase,Mockito.times(1)).invoke(status)
    }

    @Test
    fun testGetDarkMode() = runTest {
        settingsUseCase.getDarkModeStatusUseCase()

        verify(getDarkModeStatusUseCase,Mockito.times(1)).invoke()  // Verify that getUser was called

    }

    @Test
    fun testGetFingerprintStatus() = runTest {
        settingsUseCase.getFingerprintStatusUseCase()

        verify(getFingerprintStatusUseCase,Mockito.times(1)).invoke()  // Verify that getUser was called
    }


}