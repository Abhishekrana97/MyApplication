package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.AddCards
import com.example.myapplication.domain.repository.CardScreenRepository

class AddCardUseCase(private val repository: CardScreenRepository) {

    suspend operator fun invoke(card: AddCards) {
        repository.insertCard(card)
    }
}