package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.DepositMoney
import com.example.myapplication.domain.repository.DepositMoneyRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class DepositMoneyUseCaseTest {

    private lateinit var depositMoneyUseCase: DepositMoneyUseCase
    private val depositMoneyRepository: DepositMoneyRepository = mock()
    private val depositMoney: DepositMoney = mock()


    @Before
    fun setUp() {
        depositMoneyUseCase = DepositMoneyUseCase(depositMoneyRepository)
    }

    @Test
    fun testInsertMoney() = runTest {
        depositMoneyUseCase(depositMoney)

        Mockito.verify(depositMoneyRepository, Mockito.times(1)).insertMoney(depositMoney)
    }
}