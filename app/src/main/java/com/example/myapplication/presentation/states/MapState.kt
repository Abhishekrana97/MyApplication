package com.example.myapplication.presentation.states

import com.google.android.gms.maps.model.LatLng

data class MapState(
    val userLocation: LatLng? = null,
    val selectedLocation: LatLng? = null
)