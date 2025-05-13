package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.DepositMoneyRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import kotlin.test.Test

class GetDepositMoneyUseCaseTest{

    private lateinit var getDepositMoneyUseCase: GetDepositMoneyUseCase
    private val repository: DepositMoneyRepository = mock() // mock repository

    @Before
    fun setUp() {
        getDepositMoneyUseCase = GetDepositMoneyUseCase(repository)
    }

    @Test
    fun testGetDepositMoney() = runTest {
        // Act
        getDepositMoneyUseCase()

        // Assert
        verify(repository, times(1)).getDepositMoney()
    }
}
