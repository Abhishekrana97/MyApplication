package com.example.myapplication.data.repositoryImpl


import com.example.myapplication.data.model.ExchangeCurrency
import com.example.myapplication.data.network.ApiService
import com.example.myapplication.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ExchangeCurrencyRepositoryImplTest {

    private lateinit var repository: ExchangeCurrencyRepositoryImpl
    private val apiService: ApiService = mock()  // Mock ApiService

    private val exchangeCurrency : ExchangeCurrency = mock()

    @Before
    fun setUp() {
        // Injecting the mocked ApiService into the repository
        repository = ExchangeCurrencyRepositoryImpl(apiService)

    }

    @Test
    fun testGetCurrencyRatesSuccess() = runTest {
        // Prepare mock response
        whenever(apiService.getExchangeCurrencyDetails(any())).thenReturn(exchangeCurrency)

        // Call the method under test
        val result = repository.getCurrencyRates("USD")

        // Assert that the result is a Success
        assertTrue(result is DataState.Success)
        assertEquals(exchangeCurrency, result.data)
    }

    @Test
    fun testGetCurrencyRatesError() = runTest {
        // Simulate an error by throwing an exception
        whenever(apiService.getExchangeCurrencyDetails(any())).thenThrow(RuntimeException("Network Error"))

        // Call the method under test
        val result = repository.getCurrencyRates("USD")

        // Assert that the result is an Error
        assertTrue(result is DataState.Error)
        assertEquals("Network Error", result.exception.message)
    }
}
