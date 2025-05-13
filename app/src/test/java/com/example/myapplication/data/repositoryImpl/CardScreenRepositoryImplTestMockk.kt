package com.example.myapplication.data.repositoryImpl

import com.example.myapplication.data.database.dao.CardDao
import com.example.myapplication.data.model.AddCards
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class CardScreenRepositoryImplTestMockk {

    private var cardDao: CardDao = mockk()
    private var addCard: AddCards = mockk()
    private lateinit var cardScreenRepository: CardScreenRepositoryImpl

    @Before
    fun setUp() {
        // Create an instance of the repository with the mocked CardDao
        cardScreenRepository = CardScreenRepositoryImpl(cardDao)
    }

    @Test
    fun `test getCardsFromDB returns correct data`() = runTest {
        // Arrange: Prepare a list of AddCards to return from the mocked Dao
        val mockCards = listOf(addCard)
        coEvery { cardDao.getAllCards() } returns flow { emit(mockCards) }

        // Act: Call the method
        val result = cardScreenRepository.getCardsFromDB().toList()

        // Assert: Verify that the result matches the expected value
        assertEquals(mockCards, result.flatten())
        coVerify { cardDao.getAllCards() }
    }

    @Test
    fun `test insertCard calls dao insert method`() = runTest {
        val card = AddCards(1, "Abcd", "5623456789023456", "12/29", "123")

        coEvery { cardDao.insert(card) } returns Unit  // Adjust the return value to match the actual method

        // Act: Call insertCard method
        cardScreenRepository.insertCard(card)

        // Assert: Verify that insert method was called with correct argument
        coVerify { cardDao.insert(card) }
    }

    @Test
    fun `test deleteCard calls dao deleteCard method`() = runTest {

        val card = AddCards(1, "Abcd", "5623456789023456", "12/29", "123")

        coEvery { cardDao.deleteCard(card) } returns Unit  // Adjust the return value to match the actual method


        // Act: Call deleteCard method
        cardScreenRepository.deleteCard(card)

        // Assert: Verify that deleteCard method was called with correct argument
        coVerify { cardDao.deleteCard(card) }
    }

    @Test
    fun `test updateCard calls dao updateCard method`() = runTest {
        val card = AddCards(1, "Abcd", "5623456789023456", "12/29", "123")

        coEvery { cardDao.updateCard(card) } returns Unit  // Adjust the return value to match the actual method

        // Act: Call updateCard method
        cardScreenRepository.updateCard(card)

        // Assert: Verify that updateCard method was called with correct argument
        coVerify { cardDao.updateCard(card) }
    }
}
