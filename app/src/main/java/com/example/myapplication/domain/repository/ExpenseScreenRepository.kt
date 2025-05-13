package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.ExpenseDetails
import com.example.myapplication.utils.DataState

interface ExpenseScreenRepository {
    suspend fun getExpenseDetails(): DataState<ExpenseDetails>

}