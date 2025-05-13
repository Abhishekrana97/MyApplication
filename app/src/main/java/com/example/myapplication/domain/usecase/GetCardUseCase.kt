package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.AddCards
import com.example.myapplication.domain.repository.CardScreenRepository
import kotlinx.coroutines.flow.Flow


class GetCardUseCase(private val repository: CardScreenRepository) {

    suspend operator fun invoke(): Flow<List<AddCards>> {
        return repository.getCardsFromDB()
    }
}