package com.example.myapplication.domain.repository

import android.content.Context
import com.example.myapplication.presentation.states.MapState
import com.google.android.gms.location.FusedLocationProviderClient

interface MapScreenRepository {
    suspend fun fetchUserLocation(context: Context, fusedLocationClient: FusedLocationProviderClient) : MapState
    suspend fun selectedLocation(context: Context,place: String) : MapState

}