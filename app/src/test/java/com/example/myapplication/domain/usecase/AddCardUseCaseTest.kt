package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.AddCards
import com.example.myapplication.domain.repository.CardScreenRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import kotlin.test.Test

class AddCardUseCaseTest{

    private lateinit var addCardUseCase: AddCardUseCase
    private val repository: CardScreenRepository = mock() // mock repository
    private val card: AddCards = mock() // mock card data

    @Before
    fun setUp() {
        addCardUseCase = AddCardUseCase(repository)
    }

    @Test
    fun testInsertCard() = runTest {
        // Act
        addCardUseCase(card)

        // Assert
        verify(repository, times(1)).insertCard(card)
    }

}