package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.ExpenseDetails
import com.example.myapplication.domain.usecase.ExpenseDetailsUseCase
import com.example.myapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExpenseScreenViewModel @Inject constructor(
    private val expenseDetailsUseCase: ExpenseDetailsUseCase
) : ViewModel() {

    private val _expenseDetails = MutableStateFlow<ExpenseDetails?>(null)
    val expenseDetails get() = _expenseDetails.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()

    fun getExpenseDetails() {
        viewModelScope.launch {
            expenseDetailsUseCase().let {
                when (it) {
                    is DataState.Success -> {
                        _expenseDetails.value = it.data
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
