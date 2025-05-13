package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.AddCards
import com.example.myapplication.domain.repository.CardScreenRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import kotlin.test.Test

class UpdateCardUseCaseTest{
    private lateinit var updateCardUseCase: UpdateCardUseCase
    private val repository: CardScreenRepository = mock() // mock repository
    private val card: AddCards = mock() // mock card data

    @Before
    fun setUp() {
        updateCardUseCase = UpdateCardUseCase(repository)
    }

    @Test
    fun testUpdateCard() = runTest {
        // Act
        updateCardUseCase(card)

        // Assert
        verify(repository, times(1)).updateCard(card)
    }
}