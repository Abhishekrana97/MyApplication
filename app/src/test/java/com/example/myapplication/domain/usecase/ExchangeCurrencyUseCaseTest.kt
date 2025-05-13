package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.ExchangeCurrencyRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class ExchangeCurrencyUseCaseTest {

    private lateinit var exchangeCurrencyUseCase: ExchangeCurrencyUseCase
    private val repository: ExchangeCurrencyRepository = mock()

    @Before
    fun setUp(){
        exchangeCurrencyUseCase = ExchangeCurrencyUseCase(repository)
    }

    @Test
    fun testCurrencyRates() = runTest{
        val path = "USD"
        exchangeCurrencyUseCase(path)

        Mockito.verify(repository,Mockito.times(1)).getCurrencyRates(path)
    }

}