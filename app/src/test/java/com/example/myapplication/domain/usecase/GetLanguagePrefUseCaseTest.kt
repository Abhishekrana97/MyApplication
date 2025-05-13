package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.SettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GetLanguagePrefUseCaseTest{
    private lateinit var getLanguagePrefUseCase: GetLanguagePrefUseCase
    private val repository: SettingsRepository = mock()

    @Before
    fun setUp(){
        getLanguagePrefUseCase = GetLanguagePrefUseCase(repository)
    }

    @Test
    fun testGetDarkMode() = runTest{
        getLanguagePrefUseCase()

        Mockito.verify(repository, Mockito.times(1)).getLanguagePreference()
    }
}