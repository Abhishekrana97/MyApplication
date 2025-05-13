package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.SettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GetDarkModeStatusUseCaseTest{

    private lateinit var getDarkModeStatusUseCase: GetDarkModeStatusUseCase
    private val repository: SettingsRepository = mock()

    @Before
    fun setUp(){
        getDarkModeStatusUseCase = GetDarkModeStatusUseCase(repository)
    }

    @Test
    fun testGetDarkMode() = runTest{
        getDarkModeStatusUseCase()

        Mockito.verify(repository, Mockito.times(1)).getDarkModePreference()
    }
}