package com.example.myapplication.data.repositoryImpl

import com.example.myapplication.data.model.ExpenseDetails
import com.example.myapplication.data.network.ApiService
import com.example.myapplication.domain.repository.ExpenseScreenRepository
import com.example.myapplication.utils.DataState
import javax.inject.Inject

class ExpenseScreenRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    ExpenseScreenRepository {

    override suspend fun getExpenseDetails(): DataState<ExpenseDetails> {
        return try {
            val results = apiService.getExpenseDetails()
            DataState.Success(results)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }
}