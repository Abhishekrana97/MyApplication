package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.ExchangeCurrency
import com.example.myapplication.utils.DataState

interface ExchangeCurrencyRepository {

    suspend fun getCurrencyRates(path:String): DataState<ExchangeCurrency>
}