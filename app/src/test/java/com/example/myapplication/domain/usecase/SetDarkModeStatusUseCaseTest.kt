package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.SettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito
import org.mockito.Mockito.mock
import kotlin.test.Test

class SetDarkModeStatusUseCaseTest {

    private val repository: SettingsRepository = mock()
    private lateinit var useCase: SetDarkModeStatusUseCase

    @Before
    fun setUp() {
        useCase = SetDarkModeStatusUseCase(repository)
    }

    @Test
    fun testWithTrueValue() = runTest {
        val darkModeStatus = true
        useCase(darkModeStatus)
        Mockito.verify(repository, Mockito.times(1)).saveDarkModePreference(darkModeStatus)
    }

    @Test
    fun testWithFalseValue() = runTest {
        val darkModeStatus = false
        useCase(darkModeStatus)
        Mockito.verify(repository, Mockito.times(1)).saveDarkModePreference(darkModeStatus)
    }
}