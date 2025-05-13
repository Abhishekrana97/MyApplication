package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.GraphDetails
import com.example.myapplication.utils.DataState

interface GraphScreenRepository {
    suspend fun getGraphDetails(): DataState<GraphDetails>
}