package com.example.myapplication.presentation.viewmodel

import com.example.myapplication.data.model.DepositMoney
import com.example.myapplication.domain.usecase.DepositMoneyUseCase
import com.example.myapplication.domain.usecase.DepositUseCase
import com.example.myapplication.domain.usecase.GetDepositMoneyUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class DepositListViewModelTest {

    private lateinit var viewModel: DepositListViewModel

    private val dispatcher = StandardTestDispatcher()

    private lateinit var depositUseCase: DepositUseCase

    @Before
    fun setUp() {
        val getDepositMoney = mock(GetDepositMoneyUseCase::class.java)
        val depositMoney = mock(DepositMoneyUseCase::class.java)
        depositUseCase = DepositUseCase(getDepositMoney, depositMoney)
        viewModel = DepositListViewModel(depositUseCase)

        Dispatchers.setMain(dispatcher)
    }


    @Test
    fun testDepositMoney() = runTest {

        val depositMoney = DepositMoney(1, 1,"2000", "12/04/1997")

        viewModel.insertMoney(depositMoney)

        dispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(depositUseCase.depositMoneyUseCase, Mockito.times(1)).invoke(depositMoney)
    }


    @Test
    fun testGetDepositMoneyEmpty() = runTest {
        // Mocking the use case to return an empty list
        whenever(depositUseCase.getDepositMoneyUseCase())
            .thenReturn(flow { emit(emptyList()) })

        // When
        viewModel.getDepositMoney()


        dispatcher.scheduler.advanceUntilIdle()
// Collect the emitted value from depositMoney StateFlow
        val result = viewModel.depositMoney.first() // `first()` gets the first emitted value


        // Then
        verify(
            depositUseCase.getDepositMoneyUseCase,
            Mockito.times(1)
        ).invoke() // Verify the use case method is called
        assertEquals(emptyList<DepositMoney>(), result) // Assert that the result is an empty list
    }

    @Test
    fun testGetDepositMoney() = runTest {
        // Prepare expected data
        val expectedData = listOf(
            DepositMoney(2,1, "1000", "12/12/2024"),
            DepositMoney(1, 1,"2000", "11/12/2024")
        )

        // Mock the use case to return the expected flow
        whenever(depositUseCase.getDepositMoneyUseCase()).thenReturn(flow { emit(expectedData) })

        // When calling the method on the ViewModel
        viewModel.getDepositMoney()

        // Advance the dispatcher to allow the flow to emit and be collected
        dispatcher.scheduler.advanceUntilIdle()

        // Then verify that the use case method was called
        verify(depositUseCase.getDepositMoneyUseCase, Mockito.times(1)).invoke()

        // Assert that the depositMoney state has been updated with the expected data
        assertEquals(expectedData, viewModel.depositMoney.value)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}