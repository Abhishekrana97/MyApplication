package com.example.myapplication.domain.usecase

import android.content.Context
import com.example.myapplication.domain.repository.MapScreenRepository
import com.example.myapplication.presentation.states.MapState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class FetchUserLocationUseCaseTest {

    private lateinit var fetchUserLocationUseCase: FetchUserLocationUseCase
    private lateinit var repository: MapScreenRepository
    private lateinit var context: Context
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Before
    fun setup() {
        // Mock the repository, context, and fusedLocationProviderClient
        repository = mockk()
        context = mockk(relaxed = true)
        fusedLocationProviderClient = mockk()

        // Initialize the use case
        fetchUserLocationUseCase = FetchUserLocationUseCase(repository)
    }

    @Test
    fun `invoke should call repository fetchUserLocation and return MapState with user location`() = runBlocking {
        // Setup mock behavior for repository
        val expectedMapState = MapState(userLocation = LatLng(10.0, 20.0))

        coEvery { repository.fetchUserLocation(context, fusedLocationProviderClient) } returns expectedMapState

        // Act
        val result = fetchUserLocationUseCase.invoke(context, fusedLocationProviderClient)

        // Assert
        assertEquals(expectedMapState, result)

        // Verify the repository fetchUserLocation method was called
        coVerify { repository.fetchUserLocation(context, fusedLocationProviderClient) }
    }

    @Test
    fun `invoke should call repository fetchUserLocation and return default MapState when no location is found`() = runBlocking {
        // Setup mock behavior for repository
        val defaultMapState = MapState(userLocation = LatLng(0.0, 0.0))

        coEvery { repository.fetchUserLocation(context, fusedLocationProviderClient) } returns defaultMapState

        // Act
        val result = fetchUserLocationUseCase.invoke(context, fusedLocationProviderClient)

        // Assert
        assertEquals(defaultMapState, result)

        // Verify the repository fetchUserLocation method was called
        coVerify { repository.fetchUserLocation(context, fusedLocationProviderClient) }
    }

    @Test
    fun `invoke should call repository fetchUserLocation when permission is denied`() = runBlocking {
        // Setup mock behavior for repository
        val defaultMapState = MapState(userLocation = LatLng(0.0, 0.0))

        coEvery { repository.fetchUserLocation(context, fusedLocationProviderClient) } returns defaultMapState

        // Act
        val result = fetchUserLocationUseCase.invoke(context, fusedLocationProviderClient)

        // Assert
        assertEquals(defaultMapState, result)

        // Verify the repository fetchUserLocation method was called
        coVerify { repository.fetchUserLocation(context, fusedLocationProviderClient) }
    }

    @Test
    fun `invoke should handle repository exception gracefully`() = runBlocking {
        // Simulate exception thrown by the repository
        coEvery { repository.fetchUserLocation(context, fusedLocationProviderClient) } throws Exception("Repository error")

        // Act & Assert
        try {
            fetchUserLocationUseCase.invoke(context, fusedLocationProviderClient)
            fail("Exception should have been thrown")
        } catch (e: Exception) {
            assertEquals("Repository error", e.message)
        }

        // Verify that the repository method was called
        coVerify { repository.fetchUserLocation(context, fusedLocationProviderClient) }
    }
}
