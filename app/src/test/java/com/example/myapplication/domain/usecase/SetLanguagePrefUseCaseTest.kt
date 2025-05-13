package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.SettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito
import org.mockito.Mockito.mock
import kotlin.test.Test

class SetLanguagePrefUseCaseTest {


    private val repository: SettingsRepository = mock()
    private lateinit var useCase: SetLanguagePrefUseCase

    @Before
    fun setUp() {
        useCase = SetLanguagePrefUseCase(repository)
    }

    @Test
    fun testSetLanguagePref() = runTest {
        useCase(0)
        Mockito.verify(repository, Mockito.times(1)).saveLanguagePreference(0)
    }

}