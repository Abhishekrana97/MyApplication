package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.ExpenseScreenRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class ExpenseDetailsUseCaseTest{
    private lateinit var expenseDetailsUseCase: ExpenseDetailsUseCase
    private val repository: ExpenseScreenRepository = mock()

    @Before
    fun setUp(){
        expenseDetailsUseCase = ExpenseDetailsUseCase(repository)
    }

    @Test
    fun testGetExpenseDetails() = runTest{
        expenseDetailsUseCase()

        Mockito.verify(repository, Mockito.times(1)).getExpenseDetails()
    }
}