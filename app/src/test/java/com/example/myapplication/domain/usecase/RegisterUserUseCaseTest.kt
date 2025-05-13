package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.LoginRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import kotlin.test.Test

class RegisterUserUseCaseTest{

    private lateinit var registerUserUseCase: RegisterUserUseCase
    private val repository: LoginRepository = mock()

    @Before
    fun setUp() {
        registerUserUseCase = RegisterUserUseCase(repository)
    }

    @Test
    fun testInsertUser() = runTest {
        // Act
        val email = "abc@gmail.com"
        val password = "12345678"
        registerUserUseCase(email,password)

        // Assert
        verify(repository, times(1)).registerUser(email,password)
    }

}