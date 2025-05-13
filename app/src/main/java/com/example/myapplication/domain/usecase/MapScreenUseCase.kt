package com.example.myapplication.domain.usecase

data class MapScreenUseCase(
    val fetchUserLocationUseCase: FetchUserLocationUseCase,
    val selectedLocationUseCase: SelectedLocationUseCase
)
