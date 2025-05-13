package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.DepositMoney
import kotlinx.coroutines.flow.Flow

interface DepositMoneyRepository {
    suspend fun getDepositMoney(): Flow<List<DepositMoney>>
    suspend fun insertMoney(depositMoney: DepositMoney)
}