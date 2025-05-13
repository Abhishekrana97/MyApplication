package com.example.myapplication.data.repositoryImpl

import com.example.myapplication.utils.DataState
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LoginRepositoryImplTest {

    private var auth: FirebaseAuth = mock() // Mock FirebaseAuth
    private var firebaseUser: FirebaseUser = mock() // Mock FirebaseUser
    private var authResult: AuthResult = mock() // Mock AuthResult

    private lateinit var repository: LoginRepositoryImpl

    @Before
    fun setUp() {
        // Initialize repository with the mocked FirebaseAuth
        repository = LoginRepositoryImpl(auth)
    }

    private fun mockTaskForAuthResult(): Task<AuthResult> {
        val taskCompletionSource = TaskCompletionSource<AuthResult>()
        taskCompletionSource.setResult(authResult) // Ensure the task returns the mock AuthResult
        return taskCompletionSource.task
    }

    @Test
    fun `test registerUser when registration is successful`(): Unit = runBlocking {
        // Arrange
        val email = "test@example.com"
        val password = "password"

        // Mock the Firebase methods to return the Task
        whenever(auth.createUserWithEmailAndPassword(email, password)).thenReturn(mockTaskForAuthResult())
        whenever(auth.currentUser).thenReturn(firebaseUser) // Mock currentUser

        // Act
        val result = repository.registerUser(email, password)

        // Assert
        assertTrue(result is DataState.Success)
        assertEquals(firebaseUser, (result as DataState.Success).data)
        verify(auth).createUserWithEmailAndPassword(email, password) // Verify the method call
    }

    @Test
    fun `test registerUser when registration fails`(): Unit = runBlocking {
        // Arrange
        val email = "test@example.com"
        val password = "password"

        // Mocking Firebase methods to return a failed Task
        val taskCompletionSource = TaskCompletionSource<AuthResult>()
        taskCompletionSource.setException(Exception("Registration failed"))
        whenever(auth.createUserWithEmailAndPassword(email, password)).thenReturn(taskCompletionSource.task)

        // Act
        val result = repository.registerUser(email, password)

        // Assert
        assertTrue(result is DataState.Error)
        assertEquals("Registration failed", (result as DataState.Error).exception.message)
    }

    @Test
    fun `test loginUser when login is successful`(): Unit = runBlocking {
        // Arrange
        val email = "test@example.com"
        val password = "password"

        // Mock the Firebase methods to return the Task
        whenever(auth.signInWithEmailAndPassword(email, password)).thenReturn(mockTaskForAuthResult())
        whenever(auth.currentUser).thenReturn(firebaseUser) // Mock currentUser
        // Act
        val result = repository.loginUser(email, password)

        // Assert
        assertTrue(result is DataState.Success)
        assertEquals(firebaseUser, (result as DataState.Success).data)
        verify(auth).signInWithEmailAndPassword(email, password) // Verify the method call
    }

    @Test
    fun `test loginUser when login fails`(): Unit = runBlocking {
        // Arrange
        val email = "test@example.com"
        val password = "password"

        // Mocking Firebase methods to return a failed Task
        val taskCompletionSource = TaskCompletionSource<AuthResult>()
        taskCompletionSource.setException(Exception("Login failed"))
        whenever(auth.signInWithEmailAndPassword(email, password)).thenReturn(taskCompletionSource.task)

        // Act
        val result = repository.loginUser(email, password)

        // Assert
        assertTrue(result is DataState.Error)
        assertEquals("Login failed", (result as DataState.Error).exception.message)
    }

    @Test
    fun `test logoutUser when called`(): Unit = runBlocking {
        // Act
        repository.logoutUser()

        // Assert
        verify(auth).signOut()
    }
}






