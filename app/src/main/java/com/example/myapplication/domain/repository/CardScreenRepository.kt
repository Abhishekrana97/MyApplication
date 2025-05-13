package com.example.myapplication.domain.repository


import com.example.myapplication.data.model.AddCards
import kotlinx.coroutines.flow.Flow


interface CardScreenRepository {
    suspend fun getCardsFromDB(): Flow<List<AddCards>>
    suspend fun insertCard(addCards: AddCards)
    suspend fun deleteCard(addCards: AddCards)
    suspend fun updateCard(addCards: AddCards)
}

