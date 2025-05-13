package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.LoginRepository
import com.example.myapplication.utils.DataState
import com.google.firebase.auth.FirebaseUser


class RegisterUserUseCase(private val repository: LoginRepository) {
    suspend operator fun invoke(email:String,password: String): DataState<FirebaseUser> {
        return repository.registerUser(email,password)
    }

}