package com.example.myapplication.data.repositoryImpl

import com.example.myapplication.data.database.dao.DepositMoneyDao
import com.example.myapplication.data.model.DepositMoney
import com.example.myapplication.domain.repository.DepositMoneyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DepositMoneyRepositoryImpl @Inject constructor(private val dao: DepositMoneyDao) :
    DepositMoneyRepository {
    override suspend fun getDepositMoney(): Flow<List<DepositMoney>> {
        return dao.getDepositMoney()
    }

    override suspend fun insertMoney(depositMoney: DepositMoney) {
        dao.insert(depositMoney)
    }
}