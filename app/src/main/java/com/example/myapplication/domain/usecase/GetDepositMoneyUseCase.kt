package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.DepositMoney
import com.example.myapplication.domain.repository.DepositMoneyRepository
import kotlinx.coroutines.flow.Flow

class GetDepositMoneyUseCase(private val repository: DepositMoneyRepository) {
    suspend operator fun invoke(): Flow<List<DepositMoney>> {
        return repository.getDepositMoney()
    }
}