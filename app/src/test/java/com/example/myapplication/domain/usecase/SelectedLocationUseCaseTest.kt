package com.example.myapplication.domain.usecase


import android.content.Context
import com.example.myapplication.domain.repository.MapScreenRepository
import com.example.myapplication.presentation.states.MapState
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class SelectedLocationUseCaseTest {

    private lateinit var selectedLocationUseCase: SelectedLocationUseCase
    private lateinit var repository: MapScreenRepository
    private lateinit var context: Context

    @Before
    fun setup() {
        // Mock the repository and context
        repository = mockk()
        context = mockk(relaxed = true)

        // Initialize the use case
        selectedLocationUseCase = SelectedLocationUseCase(repository)
    }

    @Test
    fun `invoke should call repository selectedLocation and return MapState with resolved location`() =
        runBlocking {
            // Setup mock behavior for repository
            val resolvedMapState = MapState(userLocation = LatLng(30.0, 40.0))
            val place = "New York"

            coEvery { repository.selectedLocation(context, place) } returns resolvedMapState

            // Act
            val result = selectedLocationUseCase.invoke(context, place)

            // Assert
            assertEquals(resolvedMapState, result)

            // Verify the repository selectedLocation method was called
            coVerify { repository.selectedLocation(context, place) }
        }

    @Test
    fun `invoke should call repository selectedLocation and return default MapState when no location is found`() =
        runBlocking {
            // Setup mock behavior for repository
            val defaultMapState = MapState(userLocation = LatLng(0.0, 0.0))
            val place = "Unknown Place"

            coEvery { repository.selectedLocation(context, place) } returns defaultMapState

            // Act
            val result = selectedLocationUseCase.invoke(context, place)

            // Assert
            assertEquals(defaultMapState, result)

            // Verify the repository selectedLocation method was called
            coVerify { repository.selectedLocation(context, place) }
        }

    @Test
    fun `invoke should call repository selectedLocation and return default MapState when geocoder fails`() =
        runBlocking {
            // Simulate exception thrown by the repository
            coEvery {
                repository.selectedLocation(
                    context,
                    "Place"
                )
            } throws Exception("Geocoder failure")

            // Act & Assert
            try {
                selectedLocationUseCase.invoke(context, "Place")
                fail("Exception should have been thrown")
            } catch (e: Exception) {
                assertEquals("Geocoder failure", e.message)
            }

            // Verify that the repository method was called
            coVerify { repository.selectedLocation(context, "Place") }
        }
}

