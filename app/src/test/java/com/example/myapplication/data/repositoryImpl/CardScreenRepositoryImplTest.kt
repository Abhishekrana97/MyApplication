package com.example.myapplication.data.repositoryImpl

import com.example.myapplication.data.database.dao.CardDao
import com.example.myapplication.data.model.AddCards
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class CardScreenRepositoryImplTest {

    private lateinit var cardScreenRepository: CardScreenRepositoryImpl
    private val dao: CardDao = mock()

    @Before
    fun setUp() {
        cardScreenRepository = CardScreenRepositoryImpl(dao)
    }

    @Test
    fun `test getCardsFromDB should return list of AddCards`() = runTest {
        // Given
        val cards = listOf(AddCards(1, "Abcd", "5623456789023456", "12/29", "123"))
        whenever(dao.getAllCards()).thenReturn(flowOf(cards))

        // When
        val result = cardScreenRepository.getCardsFromDB().first()

        // Then
        assertEquals(result,cards)
    }

    @Test
    fun `test insertCard should call dao insert method`() = runTest {
        // Given
        val card = AddCards(1, "Abcd", "5623456789023456", "12/29", "123")

        // When
        cardScreenRepository.insertCard(card)

        // Then
        verify(dao).insert(card)
    }

    @Test
    fun `test deleteCard should call dao deleteCard method`() = runTest {
        // Given
        val card =AddCards(1, "Abcd", "5623456789023456", "12/29", "123")

        // When
        cardScreenRepository.deleteCard(card)

        // Then
        verify(dao).deleteCard(card)
    }

    @Test
    fun `test updateCard should call dao updateCard method`() = runTest {
        // Given
        val card = AddCards(1, "Abcd", "5623456789023456", "12/29", "123")

        // When
        cardScreenRepository.updateCard(card)

        // Then
        verify(dao).updateCard(card)
    }
}
