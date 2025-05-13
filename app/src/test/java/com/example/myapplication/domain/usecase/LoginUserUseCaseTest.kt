package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.LoginRepository
import com.example.myapplication.utils.DataState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LoginUserUseCaseTest {

    private lateinit var loginUserUseCase: LoginUserUseCase
    private val loginRepository: LoginRepository = mock()

    @Before
    fun setUp() {
        loginUserUseCase = LoginUserUseCase(loginRepository)
    }

    @Test
    fun `test successful login returns FirebaseUser`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password123"
        val mockUser: FirebaseUser = mock()
        val mockDataState = DataState.Success(mockUser)

        // Mock repository response
        whenever(loginRepository.loginUser(email, password)).thenReturn(mockDataState)

        // Act
        val result = loginUserUseCase.invoke(email, password)

        // Assert
        assert(result is DataState.Success)
        assert((result as DataState.Success).data == mockUser)
        verify(loginRepository).loginUser(email, password)
    }

    @Test
    fun `test login fails with an error`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "wrongpassword"
        val mockError = DataState.Error(Exception("Login failed"))

        // Mock repository response
        whenever(loginRepository.loginUser(email, password)).thenReturn(mockError)

        // Act
        val result = loginUserUseCase.invoke(email, password)

        // Assert
        assert(result is DataState.Error)
        assert((result as DataState.Error).exception.message == "Login failed")
        verify(loginRepository).loginUser(email, password)
    }

    @Test
    fun `test login with invalid credentials returns error`() = runTest {
        // Arrange
        val email = "wrong@example.com"
        val password = "incorrectpassword"
        val mockError = DataState.Error(Exception("Invalid credentials"))

        // Mock repository response
        whenever(loginRepository.loginUser(email, password)).thenReturn(mockError)

        // Act
        val result = loginUserUseCase.invoke(email, password)

        // Assert
        assert(result is DataState.Error)
        assert((result as DataState.Error).exception.message == "Invalid credentials")
        verify(loginRepository).loginUser(email, password)
    }
}
