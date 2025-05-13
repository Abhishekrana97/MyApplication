package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.GraphDetails
import com.example.myapplication.domain.repository.GraphScreenRepository
import com.example.myapplication.utils.DataState

class GraphUseCase(private val repository: GraphScreenRepository) {

    suspend operator fun invoke(): DataState<GraphDetails> {
        return repository.getGraphDetails()
    }
}