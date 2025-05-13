package com.example.myapplication.domain.usecase

import android.content.Context
import com.example.myapplication.domain.repository.MapScreenRepository
import com.example.myapplication.presentation.states.MapState


class SelectedLocationUseCase(private val repository: MapScreenRepository) {
    suspend operator fun invoke(context: Context, places: String) : MapState {
       return repository.selectedLocation(context,places)
    }
}