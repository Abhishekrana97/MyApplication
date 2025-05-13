package com.example.myapplication.presentation.intents

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient

sealed class MapIntent {
    data class FetchUserLocation(val context: Context,val fusedLocationProviderClient: FusedLocationProviderClient) : MapIntent()
    data class SelectLocation(val context: Context,val place: String) : MapIntent()
}