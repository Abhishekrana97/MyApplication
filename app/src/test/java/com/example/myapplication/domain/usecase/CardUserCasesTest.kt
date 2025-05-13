package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.AddCards
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class CardUserCasesTest {
    private val addCardUseCase: AddCardUseCase = mock()
    private val updateCardUseCase: UpdateCardUseCase = mock()
    private val deleteCardUseCase: DeleteCardUseCase = mock()
    private val getCardUseCase: GetCardUseCase = mock()

    private lateinit var cardUserCases: CardUserCases

    @Before
    fun setUp() {
        cardUserCases =
            CardUserCases(addCardUseCase, updateCardUseCase, deleteCardUseCase, getCardUseCase)
    }


    @Test
    fun addCard() = runTest {
        val card = AddCards(1, "Abc", "1234567890234567", "12/28", "123")

        cardUserCases.addCardUseCase.invoke(card)

        verify(addCardUseCase, Mockito.times(1)).invoke(card)  // Verify that addCard was called
    }

    @Test
    fun getCard() = runTest {
        cardUserCases.getCardUseCase()

        verify(getCardUseCase, Mockito.times(1)).invoke()  // Verify that getCard was called
    }

    @Test
    fun updateCard() = runTest {
        val card = AddCards(1, "Abcd", "5623456789023456", "12/29", "123")

        cardUserCases.updateCardUseCase.invoke(card)

        verify(updateCardUseCase, Mockito.times(1)).invoke(card)  // Verify that update was called
    }

    @Test
    fun deleteCard() = runTest {
        val card = AddCards(1, "Abcd", "5623456789023456", "12/29", "123")

        cardUserCases.deleteCardUseCase.invoke(card)

        verify(deleteCardUseCase, Mockito.times(1)).invoke(card)  // Verify that delete was called
    }


}