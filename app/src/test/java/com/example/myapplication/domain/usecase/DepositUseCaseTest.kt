package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.DepositMoney
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class DepositUseCaseTest {

    private var getDepositMoneyUseCase: GetDepositMoneyUseCase = mock()
    private var depositMoneyUseCase: DepositMoneyUseCase = mock()

    private lateinit var depositUseCase: DepositUseCase

    @Before
    fun setUp() {
        depositUseCase = DepositUseCase(getDepositMoneyUseCase, depositMoneyUseCase)
    }

    @Test
    fun testGetDepositMoney() = runTest {
        depositUseCase.getDepositMoneyUseCase()

        verify(getDepositMoneyUseCase, Mockito.times(1)).invoke()
    }

    @Test
    fun testDepositMoney() = runTest {
        val depositMoney = DepositMoney(1, 1,"2000", "12/04/1997")
        depositUseCase.depositMoneyUseCase(depositMoney)

        verify(depositMoneyUseCase, Mockito.times(1)).invoke(depositMoney)
    }
}