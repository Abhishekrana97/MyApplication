package com.example.myapplication.data.repositoryImpl


import com.example.myapplication.data.model.GraphDetails
import com.example.myapplication.data.network.ApiService
import com.example.myapplication.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class GraphScreenRepositoryImplTest {

    private lateinit var repository: GraphScreenRepositoryImpl
    private val apiService: ApiService = mock()  // Mock ApiService
    private val graphDetails: GraphDetails = mock()  // Mock ApiService


    @Before
    fun setUp() {
        // Injecting the mocked ApiService into the repository
        repository = GraphScreenRepositoryImpl(apiService)
    }

    @Test
    fun testGetGraphDetailsSuccess() = runTest {
        // Prepare mock response
        whenever(apiService.getGraphDetails()).thenReturn(graphDetails)

        // Call the method under test
        val result = repository.getGraphDetails()

        // Assert that the result is a Success
        assertTrue(result is DataState.Success)
        assertEquals(graphDetails, result.data)
    }

    @Test
    fun testGetGraphDetailsError() = runTest {
        // Simulate an error by throwing an exception
        whenever(apiService.getGraphDetails()).thenThrow(RuntimeException("Network Error"))

        // Call the method under test
        val result = repository.getGraphDetails()

        // Assert that the result is an Error
        assertTrue(result is DataState.Error)
        assertEquals("Network Error", result.exception.message)
    }
}
