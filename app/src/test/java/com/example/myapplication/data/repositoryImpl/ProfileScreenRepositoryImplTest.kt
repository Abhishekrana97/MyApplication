package com.example.myapplication.data.repositoryImpl


import com.example.myapplication.data.model.ProfileDetails
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
class ProfileScreenRepositoryImplTest {

    private lateinit var repository: ProfileScreenRepositoryImpl
    private val apiService: ApiService = mock()  // Mock ApiService
    private val profileDetails: ProfileDetails = mock()  // Mock ApiService


    @Before
    fun setUp() {
        // Injecting the mocked ApiService into the repository
        repository = ProfileScreenRepositoryImpl(apiService)
    }

    @Test
    fun testGetProfileDetailsSuccess() = runTest {
        // Prepare mock response
        whenever(apiService.getProfileDetails()).thenReturn(profileDetails)

        // Call the method under test
        val result = repository.getProfileDetails()

        // Assert that the result is a Success
        assertTrue(result is DataState.Success)
        assertEquals(profileDetails, result.data)
    }

    @Test
    fun testGetProfileDetailsError() = runTest {
        // Simulate an error by throwing an exception
        whenever(apiService.getProfileDetails()).thenThrow(RuntimeException("Network Error"))

        // Call the method under test
        val result = repository.getProfileDetails()

        // Assert that the result is an Error
        assertTrue(result is DataState.Error)
        assertEquals("Network Error", result.exception.message)
    }
}
