package com.example.myapplication.presentation.viewmodel

import com.example.myapplication.data.model.GraphDetails
import com.example.myapplication.domain.usecase.GraphUseCase
import com.example.myapplication.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class GraphScreenViewModelTest {

    private lateinit var viewModel: GraphScreenViewModel

    private val dispatcher = StandardTestDispatcher()

    private val graphUseCase: GraphUseCase = mock() // Mocking the GraphUseCase
    private  val graphDetails : GraphDetails = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher) // Set the main dispatcher to the test dispatcher
        viewModel = GraphScreenViewModel(graphUseCase)
    }

    @Test
    fun testGetGraphDetailsSuccess() = runTest {
        // Given
        val dataState = DataState.Success(graphDetails)

        // Mock the graphUseCase to return a flow emitting success
        whenever(graphUseCase()).thenReturn(dataState)

        // When
        viewModel.getGraphDetails()
        dispatcher.scheduler.advanceUntilIdle() // Ensure coroutines are finished

        // Then
        // Verify that the graphUseCase was called once
        verify(graphUseCase, times(1)).invoke()

        // Verify that the ViewModel updates the graphDetails and loading state correctly
        val graphResult = viewModel.graphDetail.first() // Collect the emitted value from graphDetail StateFlow
        val loadingState = viewModel.isLoading.first() // Collect the emitted value from isLoading StateFlow

        assert(graphResult == graphDetails) // Assert that the graphDetails were updated correctly
        assertTrue(!loadingState) // Assert that the loading state is false
    }

    @Test
    fun testGetGraphDetailsError() = runTest {
        // Given
        val dataState = DataState.Error(Exception("Failed to load graph data"))

        // Mock the graphUseCase to return a flow emitting error
        whenever(graphUseCase()).thenReturn(dataState)

        // When
        viewModel.getGraphDetails()
        dispatcher.scheduler.advanceUntilIdle() // Ensure coroutines are finished

        // Then
        // Verify that the graphUseCase was called once
        verify(graphUseCase, times(1)).invoke()

        // Verify that the ViewModel updates the graphDetails to null and loading state correctly
        val graphResult = viewModel.graphDetail.first() // Collect the emitted value from graphDetail StateFlow
        val loadingState = viewModel.isLoading.first() // Collect the emitted value from isLoading StateFlow

        assert(graphResult == null) // Assert that the graphDetails were not updated (null)
        assertTrue(!loadingState) // Assert that the loading state is false
    }

    @Test
    fun testGetGraphDetailsLoadingState() = runTest {
        // Given
        val dataState = DataState.Success(graphDetails)

        // Mock the graphUseCase to return a flow emitting success
        whenever(graphUseCase()).thenReturn(dataState)

        // When
        viewModel.getGraphDetails()

        // Check initial loading state before data is received
        val initialLoadingState = viewModel.isLoading.first() // Collect the initial value of isLoading

        // Assert that the initial loading state is true
        assertTrue(initialLoadingState)

        // Let the data flow be emitted
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