package com.example.myapplication.domain.usecase

data class DepositUseCase(
    var getDepositMoneyUseCase: GetDepositMoneyUseCase,
    var depositMoneyUseCase: DepositMoneyUseCase
)
