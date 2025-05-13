package com.example.myapplication.data.repositoryImpl

import com.example.myapplication.data.model.GraphDetails
import com.example.myapplication.data.network.ApiService
import com.example.myapplication.domain.repository.GraphScreenRepository
import com.example.myapplication.utils.DataState
import javax.inject.Inject

class GraphScreenRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    GraphScreenRepository {

    override suspend fun getGraphDetails(): DataState<GraphDetails> {
        return try {
            val results = apiService.getGraphDetails()
            DataState.Success(results)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

}