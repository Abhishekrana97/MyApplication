package com.example.myapplication.domain.usecase


data class CardUserCases(
    val addCardUseCase: AddCardUseCase,
    val updateCardUseCase: UpdateCardUseCase,
    val deleteCardUseCase: DeleteCardUseCase,
    val getCardUseCase: GetCardUseCase
)