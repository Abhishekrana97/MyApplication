package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.CardScreenRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import kotlin.test.Test

class GetCardUseCaseTest{

    private lateinit var getCardUseCase: GetCardUseCase
    private val repository: CardScreenRepository = mock() // mock repository

    @Before
    fun setUp() {
        getCardUseCase = GetCardUseCase(repository)
    }

    @Test
    fun testGetCard() = runTest {
        // Act
        getCardUseCase()

        // Assert
        verify(repository, times(1)).getCardsFromDB()
    }
}