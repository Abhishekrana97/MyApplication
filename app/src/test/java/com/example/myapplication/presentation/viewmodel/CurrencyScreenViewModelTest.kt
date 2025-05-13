package com.example.myapplication.presentation.viewmodel

import com.example.myapplication.data.model.ExchangeCurrency
import com.example.myapplication.domain.usecase.ExchangeCurrencyUseCase
import com.example.myapplication.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class CurrencyScreenViewModelTest {

    private val exchangeCurrencyUseCase: ExchangeCurrencyUseCase = mock()

    private lateinit var viewModel: CurrencyScreenViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val exchangeCurrency: ExchangeCurrency = mock()

    @Before
    fun setup() {
        // Initialize ViewModel and provide a mocked UseCase
        viewModel = CurrencyScreenViewModel(exchangeCurrencyUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test getCurrencyDetails success case`() = runTest {

        whenever(exchangeCurrencyUseCase.invoke("USD")).thenReturn(DataState.Success(exchangeCurrency))

        viewModel.getCurrencyDetails("USD")

        // Wait for the background task to complete
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify the state of the ViewModel
        val result = viewModel.updatedCurrencyValue.first()
        val loadingState = viewModel.isLoading.first()

        assertEquals(result , exchangeCurrency)
        assertTrue(!loadingState) // Assert that the loading state is false

    }

    @Test
    fun `test getCurrencyDetails error case`() = runTest {
        // Given
        whenever(exchangeCurrencyUseCase.invoke("USD")).thenReturn(DataState.Error(Exception("Error")))

        // When
        viewModel.getCurrencyDetails("USD")

        // Wait for the background task to complete
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(null, viewModel.updatedCurrencyValue.first())
    }

    @Test
    fun `test onCurrentCurrencySelected updates currentCurrencyKey`() = runTest {
        // Given
        val newCurrency = "EUR"

        // When
        viewModel.onCurrentCurrencySelected(newCurrency)

        // Then
        assertEquals(newCurrency, viewModel.currentCurrencyKey.first())
    }

    @Test
    fun `test onTargetCurrencySelected updates targetCurrencyKey`() = runTest {
        // Given
        val newTargetCurrency = "GBP"

        // When
        viewModel.onTargetCurrencySelected(newTargetCurrency)

        // Then
        assertEquals(newTargetCurrency, viewModel.targetCurrencyKey.first())
    }

    @Test
    fun `test onSliderValueChanged updates slider value`() = runTest {

        viewModel.onSliderValueChanged(10f)

        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(10f, viewModel.sliderValue.first())
    }
}
