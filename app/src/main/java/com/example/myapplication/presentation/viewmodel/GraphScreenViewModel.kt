package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.GraphDetails
import com.example.myapplication.domain.usecase.GraphUseCase
import com.example.myapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphScreenViewModel @Inject constructor(
    private val graphUseCase: GraphUseCase
) : ViewModel() {

    private val _graphDetails = MutableStateFlow<GraphDetails?>(null)
    val graphDetail get() = _graphDetails.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()

    fun getGraphDetails() {
        viewModelScope.launch {
            graphUseCase().let {
                when (it) {
                    is DataState.Success -> {
                        _graphDetails.value = it.data
                        _isLoading.value = false
                    }
                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

}
