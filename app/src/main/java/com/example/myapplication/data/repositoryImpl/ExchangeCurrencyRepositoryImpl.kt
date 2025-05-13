package com.example.myapplication.data.repositoryImpl

import com.example.myapplication.data.model.ExchangeCurrency
import com.example.myapplication.data.network.ApiService
import com.example.myapplication.domain.repository.ExchangeCurrencyRepository
import com.example.myapplication.utils.DataState
import javax.inject.Inject

class ExchangeCurrencyRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    ExchangeCurrencyRepository {
    override suspend fun getCurrencyRates(path: String): DataState<ExchangeCurrency> {
        return try {
            val results = apiService.getExchangeCurrencyDetails(path)
            DataState.Success(results)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }
}