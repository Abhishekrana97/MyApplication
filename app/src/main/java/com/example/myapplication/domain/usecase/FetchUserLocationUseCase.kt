package com.example.myapplication.domain.usecase

import android.content.Context
import com.example.myapplication.domain.repository.MapScreenRepository
import com.example.myapplication.presentation.states.MapState
import com.google.android.gms.location.FusedLocationProviderClient

class FetchUserLocationUseCase(private val repository: MapScreenRepository) {

    suspend operator fun invoke(context: Context,fetchClient: FusedLocationProviderClient) : MapState {
       return repository.fetchUserLocation(context,fetchClient)
    }
}