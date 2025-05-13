package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.SettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GetFingerprintStatusUseCaseTest{

    private lateinit var getFingerprintStatusUseCase: GetFingerprintStatusUseCase
    private val repository: SettingsRepository = mock()

    @Before
    fun setUp(){
        getFingerprintStatusUseCase = GetFingerprintStatusUseCase(repository)
    }

    @Test
    fun testGetFingerPrintStatus() = runTest{
        getFingerprintStatusUseCase()

        Mockito.verify(repository, Mockito.times(1)).getFingerprintPreference()
    }
}