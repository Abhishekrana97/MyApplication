package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.DepositMoney
import com.example.myapplication.domain.usecase.DepositUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DepositListViewModel @Inject constructor(
    private val usecase: DepositUseCase
) : ViewModel() {

    private val _depositMoney = MutableStateFlow<List<DepositMoney>>(emptyList())
    val depositMoney get() = _depositMoney.asStateFlow()



    fun getDepositMoney() {
        viewModelScope.launch {
            usecase.getDepositMoneyUseCase().collect { data ->
                _depositMoney.value = data
            }
        }
    }

    fun insertMoney(money: DepositMoney) {
        viewModelScope.launch {
            usecase.depositMoneyUseCase(money)
        }
    }
}