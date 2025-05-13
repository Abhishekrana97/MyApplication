package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.ExpenseDetails
import com.example.myapplication.domain.repository.ExpenseScreenRepository
import com.example.myapplication.utils.DataState

class ExpenseDetailsUseCase(private val repository: ExpenseScreenRepository) {

    suspend operator fun invoke(): DataState<ExpenseDetails> {
        return repository.getExpenseDetails()
    }
}