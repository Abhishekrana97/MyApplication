package com.example.myapplication.presentation.viewmodel


import com.example.myapplication.data.model.ProfileDetails
import com.example.myapplication.domain.usecase.ProfileUseCase
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
class ProfileScreenViewModelTest {

    private lateinit var viewModel: ProfileScreenViewModel

    private val dispatcher = StandardTestDispatcher()

    private val profileUseCase: ProfileUseCase = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher) // Set the main dispatcher to the test dispatcher
        viewModel = ProfileScreenViewModel(profileUseCase)
    }

    @Test
    fun testGetProfileDetailsSuccess() = runTest {
        // Given
        val profileDetails = ProfileDetails(name = "John Doe", totalBalance = "$400000", tabs = arrayListOf())
        val dataState = DataState.Success(profileDetails)

        whenever(profileUseCase()).thenReturn(dataState)

        // When
        viewModel.getProfileDetails()

        dispatcher.scheduler.advanceUntilIdle() // Ensure coroutines are finished

        // Then
        // Verify that profileUseCase was called
        verify(profileUseCase, times(1)).invoke()

        // Verify the state of the ViewModel
        val profileResult = viewModel.profileDetail.first()
        val loadingState = viewModel.isLoading.first()

        assert(profileResult == profileDetails) // Assert the profile details are updated
        assertTrue(!loadingState) // Assert that the loading state is false
    }

    @Test
    fun testGetProfileDetailsError() = runTest {
        // Given
        val dataState = DataState.Error(Exception("Failed to load profile"))

        whenever(profileUseCase()).thenReturn(dataState)

        // When
        viewModel.getProfileDetails()
        dispatcher.scheduler.advanceUntilIdle() // Ensure coroutines are finished

        // Then
        // Verify that profileUseCase was called
        verify(profileUseCase, times(1)).invoke()

        // Verify the state of the ViewModel
        val profileResult = viewModel.profileDetail.first()
        val loadingState = viewModel.isLoading.first()

        assert(profileResult == null) // Assert that the profile detail is not updated (null)
        assertTrue(!loadingState) // Assert that the loading state is false
    }

    @Test
    fun testGetProfileDetailsLoadingState() = runTest {
        // Given
        val profileDetails = ProfileDetails(name = "John Doe", totalBalance = "$400000", tabs = arrayListOf())
        val dataState = DataState.Success(profileDetails)

        // Mock the flow to emit loading state
        whenever(profileUseCase()).thenReturn(dataState)

        // When
        viewModel.getProfileDetails()

        // Check initial loading state before data is received
        val initialLoadingState = viewModel.isLoading.first()

        // Assert the initial loading state is true
        assertTrue(initialLoadingState)

        // Let the data flow be emitted
        dispatcher.scheduler.advanceUntilIdle() // Ensure coroutines are finished

        // Then
        // Assert that the loading state is false after the data is received
        val finalLoadingState = viewModel.isLoading.first()
        assertTrue(!finalLoadingState)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the dispatcher after tests
    }
}
