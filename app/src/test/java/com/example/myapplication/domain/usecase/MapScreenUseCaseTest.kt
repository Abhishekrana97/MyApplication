package com.example.myapplication.domain.usecase

import android.content.Context
import com.example.myapplication.domain.repository.MapScreenRepository
import com.example.myapplication.presentation.states.MapState
import com.google.android.gms.location.FusedLocationProviderClient
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MapScreenUseCaseTest {

    private val fetchUserLocationUseCase: FetchUserLocationUseCase = mockk()
    private val selectedLocationUseCase: SelectedLocationUseCase = mockk()
    private lateinit var repository: MapScreenRepository
    private lateinit var context: Context
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mapScreenUseCase: MapScreenUseCase

    @Before
    fun setUp() {
        // Initialize the MapScreenUseCase with the mocked dependencies
        repository = mockk()
        context = mockk(relaxed = true)
        fusedLocationProviderClient = mockk()
        mapScreenUseCase = MapScreenUseCase(fetchUserLocationUseCase, selectedLocationUseCase)

    }

    @Test
    fun fetchUserLocation() = runTest {
        // Arrange
        val mockedLocation = mockk<MapState>()
        // Mock the behavior of fetchUserLocationUseCase
        coEvery { fetchUserLocationUseCase.invoke(context, fusedLocationProviderClient) } returns mockedLocation

        // Act
        mapScreenUseCase.fetchUserLocationUseCase(context, fusedLocationProviderClient)

        // Verify that the fetchUserLocationUseCase is called
        coVerify { fetchUserLocationUseCase.invoke(context, fusedLocationProviderClient) }

    }

    @Test
    fun getSelectedLocation() = runTest {
        val place = "New York"

        // Arrange
        val mockedLocation = mockk<MapState>()
        // Mock the behavior of fetchUserLocationUseCase
        coEvery { selectedLocationUseCase.invoke(context, place) } returns mockedLocation

        // Act
        mapScreenUseCase.selectedLocationUseCase(context, place)

        // Verify that the selectedLocationUseCase is called
        coVerify { selectedLocationUseCase.invoke(context, place) }



    }
}
