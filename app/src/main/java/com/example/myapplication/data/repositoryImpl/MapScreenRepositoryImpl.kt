package com.example.myapplication.data.repositoryImpl

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.content.ContextCompat
import com.example.myapplication.domain.repository.MapScreenRepository
import com.example.myapplication.presentation.states.MapState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MapScreenRepositoryImpl @Inject constructor(): MapScreenRepository {
    override suspend fun fetchUserLocation(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ): MapState = suspendCoroutine { continuation ->
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val mapState = MapState(userLocation = LatLng(it.latitude, it.longitude))
                    continuation.resume(mapState)  // Continue with the result
                } ?: continuation.resume(MapState())  // In case location is null
            }
        } else {
            continuation.resume(MapState())  // If permission is not granted, return empty MapState
        }
    }

    override suspend fun selectedLocation(context: Context, place: String) : MapState {
        val geocoder = Geocoder(context)
        val addresses = withContext(Dispatchers.IO) {
            geocoder.getFromLocationName(place, 1)
        }

        return if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            // Directly return a new MapState with the resolved location.
            MapState(userLocation = LatLng(address.latitude, address.longitude))
        } else {
            Timber.tag("MapScreen").e("No location found for the selected place.")
            // Return a default MapState when no location is found.
            MapState()
        }
    }
}