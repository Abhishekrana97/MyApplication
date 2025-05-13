package com.example.myapplication.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.MapScreenUseCase
import com.example.myapplication.presentation.intents.MapIntent
import com.example.myapplication.presentation.states.MapState
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapScreenUseCase: MapScreenUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MapState())
    val state: State<MapState> = _state

    fun processIntent(intent: MapIntent) {
        when (intent) {
            is MapIntent.FetchUserLocation -> fetchUserLocation(
                intent.context,
                intent.fusedLocationProviderClient
            )

            is MapIntent.SelectLocation -> selectLocation(intent.context, intent.place)
            //
            //
            //
            //
        }
    }

    private fun fetchUserLocation(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ) {
        viewModelScope.launch {
            val mapState = mapScreenUseCase.fetchUserLocationUseCase(context, fusedLocationClient)
            _state.value = reduce(state.value, mapState)
        }
    }

    private fun selectLocation(context: Context, place: String) {
        viewModelScope.launch {
            val mapState  = mapScreenUseCase.selectedLocationUseCase(context,place)
            _state.value = reduce(state.value , mapState)
        }
    }

    fun reduce(currentState: MapState, newState: MapState): MapState {
        return currentState.copy(
            userLocation = newState.userLocation,
            selectedLocation = newState.selectedLocation
        )
    }
}

