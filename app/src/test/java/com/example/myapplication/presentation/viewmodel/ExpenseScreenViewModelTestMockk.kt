package com.example.myapplication.presentation.viewmodel

import com.example.myapplication.data.model.ExpenseDetails
import com.example.myapplication.domain.usecase.ExpenseDetailsUseCase
import com.example.myapplication.utils.DataState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

@ExperimentalCoroutinesApi
class ExpenseScreenViewModelTestMockk {

    private lateinit var viewModel: ExpenseScreenViewModel

    private val dispatcher = StandardTestDispatcher()

    private val expenseDetailsUseCase: ExpenseDetailsUseCase = mockk() // Mocking the ExpenseDetailsUseCase with MockK
    private val expenseDetails : ExpenseDetails = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher) // Set the main dispatcher to the test dispatcher
        viewModel = ExpenseScreenViewModel(expenseDetailsUseCase)
    }

    @Test
    fun testGetExpenseDetailsSuccess() = runTest {
        // Given
        val dataState = DataState.Success(expenseDetails)

        // Mock the expenseDetailsUseCase to return success
        coEvery { expenseDetailsUseCase() } returns dataState

        // When
        viewModel.getExpenseDetails()
        dispatcher.scheduler.advanceUntilIdle() // Ensure coroutines are finished

        // Then
        // Verify that the expenseDetailsUseCase was called once
        coVerify(exactly = 1) { expenseDetailsUseCase() }

        // Verify that the ViewModel updates the expenseDetails and loading state correctly
        val expenseResult = viewModel.expenseDetails.first() // Collect the emitted value from expenseDetails StateFlow
        val loadingState = viewModel.isLoading.first() // Collect the emitted value from isLoading StateFlow

        assert(expenseResult == expenseDetails) // Assert that the expenseDetails were updated correctly
        assertTrue(!loadingState) // Assert that the loading state is false
    }

    @Test
    fun testGetExpenseDetailsError() = runTest {
        // Given
        val dataState = DataState.Error(Exception("Failed to load expense details"))

        // Mock the expenseDetailsUseCase to return error
        coEvery { expenseDetailsUseCase() } returns dataState

        // When
        viewModel.getExpenseDetails()
        dispatcher.scheduler.advanceUntilIdle() // Ensure coroutines are finished

        // Then
        // Verify that the expenseDetailsUseCase was called once
        coVerify(exactly = 1) { expenseDetailsUseCase() }

        // Verify that the ViewModel updates the expenseDetails to null and loading state correctly
        val expenseResult = viewModel.expenseDetails.first() // Collect the emitted value from expenseDetails StateFlow
        val loadingState = viewModel.isLoading.first() // Collect the emitted value from isLoading StateFlow

        assert(expenseResult == null) // Assert that the expenseDetails were not updated (null)
        assertTrue(!loadingState) // Assert that the loading state is false
    }

    @Test
    fun testGetExpenseDetailsLoadingState() = runTest {
        // Given
        val dataState = DataState.Success(expenseDetails)

        // Mock the expenseDetailsUseCase to return success
        coEvery { expenseDetailsUseCase() } returns dataState

        // When
        viewModel.getExpenseDetails()

        // Check initial loading state before data is received
        val initialLoadingState = viewModel.isLoading.first() // Collect the initial value of isLoading

        // Assert that the initial loading state is true
        assertTrue(initialLoadingState)

        dispatcher.scheduler.advanceUntilIdle() // Ensure coroutines are finished

        // Then
        // Assert that the loading state is false after the data is received
        val finalLoadingState = viewModel.isLoading.first() // Collect the final value of isLoading
        assertTrue(!finalLoadingState)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the dispatcher after tests
    }
}
