package com.example.myapplication.presentation.viewmodel

import android.content.Context
import com.example.myapplication.domain.usecase.MapScreenUseCase
import com.example.myapplication.presentation.intents.MapIntent
import com.example.myapplication.presentation.states.MapState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MapViewModelTest {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var mapScreenUseCase: MapScreenUseCase
    private lateinit var context: Context
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var latLng: LatLng
    private val dispatcher: TestDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Mock the dependencies
        mapScreenUseCase = mockk()
        latLng = mockk()
        context = mockk()
        fusedLocationProviderClient = mockk()

        // Initialize the ViewModel with the mocked dependencies
        mapViewModel = MapViewModel(mapScreenUseCase)
        Dispatchers.setMain(dispatcher)

    }

    @Test
    fun `test fetch user location`() = runTest {
        // Arrange
        val mockMapState = MapState(userLocation =latLng)
        coEvery { mapScreenUseCase.fetchUserLocationUseCase(context, fusedLocationProviderClient) } returns mockMapState

        // Act
        mapViewModel.processIntent(MapIntent.FetchUserLocation(context, fusedLocationProviderClient))
        dispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertEquals(latLng, mapViewModel.state.value.userLocation)
        coVerify { mapScreenUseCase.fetchUserLocationUseCase(context, fusedLocationProviderClient) }
    }

    @Test
    fun `test select location`() = runTest {
        // Arrange
        val selectedPlace = "Paris"
        val mockMapState = MapState(selectedLocation = latLng)
        coEvery { mapScreenUseCase.selectedLocationUseCase(context, selectedPlace) } returns mockMapState

        // Act
        mapViewModel.processIntent(MapIntent.SelectLocation(context, selectedPlace))
        dispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertEquals(latLng, mapViewModel.state.value.selectedLocation)
        coVerify { mapScreenUseCase.selectedLocationUseCase(context, selectedPlace) }
    }

    @Test
    fun `test reduce state logic`() {
        // Arrange
        val initialState = MapState(userLocation = latLng, selectedLocation = latLng)
        val newState = MapState(userLocation =latLng, selectedLocation = latLng)

        // Act
        val reducedState = mapViewModel.reduce(initialState, newState)

        // Assert
        assertEquals(latLng, reducedState.userLocation)
        assertEquals(latLng, reducedState.selectedLocation)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the dispatcher after tests
    }

}
