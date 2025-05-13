package com.example.myapplication.data.repositoryImpl

import com.example.myapplication.data.database.dao.CardDao
import com.example.myapplication.data.model.AddCards
import com.example.myapplication.domain.repository.CardScreenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CardScreenRepositoryImpl @Inject constructor(private val dao: CardDao) :
    CardScreenRepository {

    override suspend fun getCardsFromDB(): Flow<List<AddCards>> {
        return dao.getAllCards()
    }

    override suspend fun insertCard(addCards: AddCards) {
        dao.insert(addCards)
    }

    override suspend fun deleteCard(addCards: AddCards) {
        dao.deleteCard(addCards)
    }

    override suspend fun updateCard(addCards: AddCards) {
        dao.updateCard(addCards)

    }
}