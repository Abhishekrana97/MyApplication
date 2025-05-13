package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.DepositMoney
import com.example.myapplication.domain.repository.DepositMoneyRepository

class DepositMoneyUseCase(private val repository: DepositMoneyRepository) {
    suspend operator fun invoke(depositMoney: DepositMoney) {
        repository.insertMoney(depositMoney)
    }
}