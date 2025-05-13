package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.ProfileDetails
import com.example.myapplication.domain.usecase.ProfileUseCase
import com.example.myapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val profileUserCase: ProfileUseCase
) : ViewModel() {

    private val _profileDetails = MutableStateFlow<ProfileDetails?>(null)
    val profileDetail get() = _profileDetails.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()

    fun getProfileDetails() {
        viewModelScope.launch {
            profileUserCase().let {
                when (it) {
                    is DataState.Success -> {
                        _profileDetails.value = it.data
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


