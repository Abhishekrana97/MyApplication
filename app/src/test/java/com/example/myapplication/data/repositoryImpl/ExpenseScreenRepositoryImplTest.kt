package com.example.myapplication.data.repositoryImpl


import com.example.myapplication.data.model.ExpenseDetails
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
class ExpenseScreenRepositoryImplTest {

    private lateinit var repository: ExpenseScreenRepositoryImpl
    private val apiService: ApiService = mock()  // Mock ApiService
    private val expenseDetails: ExpenseDetails = mock()


    @Before
    fun setUp() {
        // Injecting the mocked ApiService into the repository
        repository = ExpenseScreenRepositoryImpl(apiService)
    }

    @Test
    fun testGetExpenseDetailsSuccess() = runTest {
        // Prepare mock response
        whenever(apiService.getExpenseDetails()).thenReturn(expenseDetails)

        // Call the method under test
        val result = repository.getExpenseDetails()

        // Assert that the result is a Success
        assertTrue(result is DataState.Success)
        assertEquals(expenseDetails, result.data)
    }

    @Test
    fun testGetExpenseDetailsError() = runTest {
        // Simulate an error by throwing an exception
        whenever(apiService.getExpenseDetails()).thenThrow(RuntimeException("Network Error"))

        // Call the method under test
        val result = repository.getExpenseDetails()

        // Assert that the result is an Error
        assertTrue(result is DataState.Error)
        assertEquals("Network Error", result.exception.message)
    }
}
