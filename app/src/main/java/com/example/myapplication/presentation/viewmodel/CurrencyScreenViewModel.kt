package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.ExchangeCurrency
import com.example.myapplication.domain.usecase.ExchangeCurrencyUseCase
import com.example.myapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyScreenViewModel @Inject constructor(
    private val exchangeCurrencyUseCase: ExchangeCurrencyUseCase
) : ViewModel() {

    private val _updatedCurrencyValue = MutableStateFlow<ExchangeCurrency?>(null)
    val updatedCurrencyValue get() = _updatedCurrencyValue.asStateFlow()

    private val _currentCurrencyKey = MutableStateFlow("USD")
    val currentCurrencyKey get() = _currentCurrencyKey.asStateFlow()

    private val _targetCurrencyKey = MutableStateFlow("AED")
    val targetCurrencyKey get() = _targetCurrencyKey.asStateFlow()

    private val _sliderValue = MutableStateFlow(0f)
    val sliderValue get() = _sliderValue.asStateFlow()

    private val _convertedValue = MutableStateFlow(0f)
    val convertedValue get() = _convertedValue.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()

    fun getCurrencyDetails(path:String) {
        viewModelScope.launch {
            exchangeCurrencyUseCase(path).let {
                when (it) {
                    is DataState.Success -> {
                        _updatedCurrencyValue.value = it.data
                        _isLoading.value = false
                        calculateConvertedValue()
                    }
                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun onCurrentCurrencySelected(updatedKey: String) {
        _currentCurrencyKey.value = updatedKey
        calculateConvertedValue()
    }

    fun onTargetCurrencySelected(updatedKey: String) {
        _targetCurrencyKey.value = updatedKey
        calculateConvertedValue()
    }

    fun onSliderValueChanged(value: Float) {
        _sliderValue.value = String.format("%.3f", value).toFloat()
        calculateConvertedValue()
    }

    private fun calculateConvertedValue() {
        val targetRate = _updatedCurrencyValue.value?.rates?.get(targetCurrencyKey.value)?: 0f
        _convertedValue.value = String.format("%.3f", (sliderValue.value * targetRate.toFloat())).toFloat()
    }
}
