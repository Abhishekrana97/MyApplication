package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.AddCards
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CardUserCasesTestMockk {

    private val addCardUseCase: AddCardUseCase = mockk()
    private val updateCardUseCase: UpdateCardUseCase = mockk()
    private val deleteCardUseCase: DeleteCardUseCase = mockk()
    private val getCardUseCase: GetCardUseCase = mockk()

    private lateinit var cardUserCases: CardUserCases

    @Before
    fun setUp() {
        cardUserCases = CardUserCases(
            addCardUseCase,
            updateCardUseCase,
            deleteCardUseCase,
            getCardUseCase
        )
    }

    @Test
    fun addCard() = runTest {
        val card = AddCards(1, "Abc", "1234567890234567", "12/28", "123")

        // Mocking the behavior of addCardUseCase (if necessary)
        coEvery { addCardUseCase.invoke(any()) } returns Unit

        // Invoke the use case
        cardUserCases.addCardUseCase.invoke(card)

        // Verify that addCardUseCase was called once with the specified card
        coVerify { addCardUseCase.invoke(card) }
    }

    @Test
    fun getCard() = runTest {
        val card = AddCards(1, "Abcd", "5623456789023456", "12/29", "123")

        // Mocking the behavior of getCardUseCase (if necessary)
        coEvery { getCardUseCase.invoke() } returns flow { emit(listOf(card)) }

        // Invoke the use case
        cardUserCases.getCardUseCase.invoke()

        // Verify that getCardUseCase was called once
        coVerify { getCardUseCase.invoke() }
    }

    @Test
    fun updateCard() = runTest {
        val card = AddCards(1, "Abcd", "5623456789023456", "12/29", "123")

        // Mocking the behavior of updateCardUseCase (if necessary)
        coEvery { updateCardUseCase.invoke(any()) } returns Unit

        // Invoke the use case
        cardUserCases.updateCardUseCase.invoke(card)

        // Verify that updateCardUseCase was called once with the specified card
        coVerify { updateCardUseCase.invoke(card) }
    }

    @Test
    fun deleteCard() = runTest {
        val card = AddCards(1, "Abcd", "5623456789023456", "12/29", "123")

        // Mocking the behavior of deleteCardUseCase (if necessary)
        coEvery { deleteCardUseCase.invoke(any()) } returns Unit

        // Invoke the use case
        cardUserCases.deleteCardUseCase.invoke(card)

        // Verify that deleteCardUseCase was called once with the specified card
        coVerify  { deleteCardUseCase.invoke(card) }
    }
}
