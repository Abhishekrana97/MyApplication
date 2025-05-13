package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.LoginRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import kotlin.test.Test

class LogoutUserUseCaseTest{

    private lateinit var logoutUserUseCase: LogoutUserUseCase
    private val repository: LoginRepository = mock()

    @Before
    fun setUp() {
        logoutUserUseCase = LogoutUserUseCase(repository)
    }

    @Test
    fun testDeleteUser() = runTest {
        // Act
        logoutUserUseCase()

        // Assert
        verify(repository, times(1)).logoutUser()
    }
}