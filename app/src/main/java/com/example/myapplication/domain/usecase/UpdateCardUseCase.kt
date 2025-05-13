package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.AddCards
import com.example.myapplication.domain.repository.CardScreenRepository


class UpdateCardUseCase(private val repository: CardScreenRepository) {
    suspend operator fun invoke(card: AddCards) {
        repository.updateCard(card)
    }
}