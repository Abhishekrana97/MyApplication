package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.ExchangeCurrency
import com.example.myapplication.domain.repository.ExchangeCurrencyRepository
import com.example.myapplication.utils.DataState

class ExchangeCurrencyUseCase (private val repository: ExchangeCurrencyRepository) {
    suspend operator fun invoke(path:String): DataState<ExchangeCurrency> {
        return repository.getCurrencyRates(path)
    }
}
