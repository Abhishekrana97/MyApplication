package com.example.myapplication.domain.usecase

data class LoginUseCases(
    val registerUserUseCase: RegisterUserUseCase,
    val loginUserUseCase: LoginUserUseCase,
    val logoutUserUseCase: LogoutUserUseCase
)