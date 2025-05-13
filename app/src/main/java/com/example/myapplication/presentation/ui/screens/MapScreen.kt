package com.example.myapplication.presentation.ui.screens

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.presentation.intents.MapIntent
import com.example.myapplication.presentation.ui.component.AlertDialogComponent
import com.example.myapplication.presentation.ui.component.SearchBar
import com.example.myapplication.presentation.viewmodel.MapViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import timber.log.Timber

@Composable
fun MapScreen(onBackPress: () -> Unit) {
    val mapViewModel = hiltViewModel<MapViewModel>()
    val context = LocalContext.current
    val state by mapViewModel.state
    val permissionDialog = remember { mutableStateOf(false) }
    val locationDialog = remember { mutableStateOf(false) }

    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    val cameraPositionState = rememberCameraPositionState()


    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Fetch the user's location and update the camera if permission is granted
            mapViewModel.processIntent(MapIntent.FetchUserLocation(context, fusedLocationClient))
        } else {
            // Handle the case when permission is denied
            Timber.e("Location permission was denied by the user.")
            permissionDialog.value = true
        }
    }


    LaunchedEffect(Unit) {
        if (isLocationEnabled) {
            when (PackageManager.PERMISSION_GRANTED) {
                // Check if the location permission is already granted
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    // Fetch the user's location and update the camera
                    mapViewModel.processIntent(
                        MapIntent.FetchUserLocation(
                            context,
                            fusedLocationClient
                        )
                    )
                }
                else -> {
                    // Request the location permission if it has not been granted
                    permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        } else {
            locationDialog.value = true
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(18.dp)) // Add space for the search bar

        // Search Bar Component
        SearchBar(
            onPlaceSelected = { place ->
                mapViewModel.processIntent(MapIntent.SelectLocation(context, place))
            }
        )

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            state.userLocation?.let {
                Marker(state = MarkerState(position = it))
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 10f)
            }

            state.selectedLocation?.let {
                Marker(state = MarkerState(position = it))
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 10f)
            }

        }
    }

    // Handle Dialogs
    if (locationDialog.value) {
        AlertDialogComponent(
            stringResource(R.string.location), stringResource(R.string.location_enable_msg),
            onClick = {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(intent)
                onBackPress()
            },
            onDismissClick = { onBackPress() }
        )
    }

    if (permissionDialog.value) {
        AlertDialogComponent(
            stringResource(R.string.location), stringResource(R.string.location_msg),
            onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
                onBackPress()
            },
            onDismissClick = { onBackPress() }
        )
    }
}

