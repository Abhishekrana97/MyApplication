package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.LoginRepository

class LogoutUserUseCase(private val repository: LoginRepository) {

   suspend operator fun invoke() {
        return repository.logoutUser()
    }
}
