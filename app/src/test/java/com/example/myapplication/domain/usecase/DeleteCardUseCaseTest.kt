package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.AddCards
import com.example.myapplication.domain.repository.CardScreenRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import kotlin.test.Test

class DeleteCardUseCaseTest{

    private lateinit var deleteCardUseCase: DeleteCardUseCase
    private val repository: CardScreenRepository = mock() // mock repository
    private val card: AddCards = mock() // mock card data

    @Before
    fun setUp() {
        deleteCardUseCase = DeleteCardUseCase(repository)
    }

    @Test
    fun testDeleteCard() = runTest {
        // Act
        deleteCardUseCase(card)

        // Assert
        verify(repository, times(1)).deleteCard(card)
    }

}