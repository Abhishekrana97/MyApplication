package com.example.myapplication.domain.usecase

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import kotlin.test.Test

class LoginUseCasesTest {

    private val registerUserUseCase: RegisterUserUseCase = mock()
    private val loginUserUseCase: LoginUserUseCase = mock()
    private val logoutUserUseCase: LogoutUserUseCase = mock()

    private lateinit var loginUseCases: LoginUseCases

    @Before
    fun setUp() {
        loginUseCases = LoginUseCases(registerUserUseCase, loginUserUseCase,logoutUserUseCase)
    }

    @Test
    fun registerUser()= runTest {
        val email = "test@example.com"
        val password = "password123"

        loginUseCases.registerUserUseCase(email,password)

        verify(registerUserUseCase,Mockito.times(1)).invoke(email,password)  // Verify that addUser was called
    }

    @Test
    fun loginUser()= runTest {
        val email = "test@example.com"
        val password = "password123"

        loginUseCases.loginUserUseCase(email,password)

        verify(loginUserUseCase, Mockito.times(1)).invoke(email,password)  // Verify that getUser was called
    }

    @Test
    fun logoutUser()= runTest {
        loginUseCases.logoutUserUseCase()

        verify(logoutUserUseCase, Mockito.times(1)).invoke()
    }



}