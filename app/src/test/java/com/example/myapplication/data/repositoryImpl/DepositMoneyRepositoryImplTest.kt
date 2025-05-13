package com.example.myapplication.data.repositoryImpl



import com.example.myapplication.data.database.dao.DepositMoneyDao
import com.example.myapplication.data.model.DepositMoney
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class DepositMoneyRepositoryImplTest {

    private lateinit var depositMoneyRepository: DepositMoneyRepositoryImpl
    private val dao: DepositMoneyDao = mock()
    private val depositMoney: DepositMoney = mock()

    @Before
    fun setUp() {
        depositMoneyRepository = DepositMoneyRepositoryImpl(dao)
    }

    @Test
    fun testGetDepositMoney() = runTest {
        // Given
        val cards = listOf(depositMoney)
        whenever(dao.getDepositMoney()).thenReturn(flowOf(cards))

        // When
        val result = depositMoneyRepository.getDepositMoney().first()

        // Then
        assertEquals(result,cards)
    }

    @Test
    fun insertMoney() = runTest {
        // When
        depositMoneyRepository.insertMoney(depositMoney)

        // Then
        verify(dao).insert(depositMoney)
    }


}