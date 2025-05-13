package com.example.myapplication.presentation.viewmodel

import com.example.myapplication.domain.usecase.LoginUseCases
import com.example.myapplication.domain.usecase.LoginUserUseCase
import com.example.myapplication.domain.usecase.LogoutUserUseCase
import com.example.myapplication.domain.usecase.RegisterUserUseCase
import com.example.myapplication.utils.DataState
import com.example.myapplication.utils.SettingsPreference
import com.example.myapplication.utils.ValidationUtils
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertFalse

@ExperimentalCoroutinesApi
class LoginScreenViewModelTest {


    // Mocked dependencies

    private lateinit var viewModel: LoginScreenViewModel

    private val registerUserUseCase: RegisterUserUseCase = mock()
    private val loginUserUseCase: LoginUserUseCase = mock()
    private val logoutUserUseCase: LogoutUserUseCase = mock()

    private lateinit var loginUseCases: LoginUseCases

    private val validationUtils: ValidationUtils = mock()
    private val settingsPreference: SettingsPreference = mock()  // Mock SettingsPreference

    private val dispatcher: TestDispatcher = StandardTestDispatcher()

    private val firebaseUser: FirebaseUser = mock()

    @Before
    fun setUp() {
        // Initialize mocks and ViewModel
        loginUseCases = LoginUseCases(registerUserUseCase, loginUserUseCase, logoutUserUseCase)
        viewModel = LoginScreenViewModel(loginUseCases, settingsPreference)

        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the dispatcher after tests
    }

    @Test
    fun `test register when registration is successful`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"

        // Mock repository return value
        whenever(loginUseCases.registerUserUseCase(email, password)).thenReturn(
            DataState.Success(
                firebaseUser
            )
        )
        whenever(settingsPreference.setUserLogin(true)).thenReturn(Unit)
        val loadingState = viewModel.isLoading.first() // Collect the emitted value from isLoading StateFlow

        // Act
        viewModel.register(email, password)
        dispatcher.scheduler.advanceUntilIdle()
        // Assert
        assertTrue(viewModel.usersState.value != null) // Check that user is set
        assertEquals(firebaseUser, viewModel.usersState.value) // Ensure the Firebase user is set
        assertTrue(!loadingState) // Assert that the loading state is false

        verify(settingsPreference).setUserLogin(true) // Verify that login state was saved
    }

    @Test
    fun `test register when registration fails`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"

        // Mock repository return value
        whenever(loginUseCases.registerUserUseCase(email, password)).thenReturn(
            DataState.Error(
                Exception("Registration failed")
            )
        )

        // Act
        viewModel.register(email, password)
        dispatcher.scheduler.advanceUntilIdle()
        // Assert
        assertEquals(
            "Registration failed",
            viewModel.errorMessage.value
        ) // Ensure error message is set
        assertTrue(viewModel.usersState.value == null) // Ensure the user state is null
    }

    @Test
    fun `test login when login is successful`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"

        // Mock repository return value
        whenever(loginUseCases.loginUserUseCase(email, password)).thenReturn(
            DataState.Success(
                firebaseUser
            )
        )
        whenever(settingsPreference.setUserLogin(true)).thenReturn(Unit)

        // Act
        viewModel.login(email, password)
// Advance the coroutine to finish
        dispatcher.scheduler.advanceUntilIdle()
        // Assert
        assertTrue(viewModel.usersState.value != null) // Check that user is set
        assertEquals(firebaseUser, viewModel.usersState.value) // Ensure the Firebase user is set
        verify(settingsPreference).setUserLogin(true) // Verify that login state was saved
    }

    @Test
    fun `test login when login fails`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"

        // Mock repository return value
        whenever(loginUseCases.loginUserUseCase(email, password)).thenReturn(
            DataState.Error(
                Exception("Login failed")
            )
        )

        // Act
        viewModel.login(email, password)
        dispatcher.scheduler.advanceUntilIdle()
        // Assert
        assertEquals("Login failed", viewModel.errorMessage.value) // Ensure error message is set
        assertTrue(viewModel.usersState.value == null) // Ensure the user state is null
    }

    @Test
    fun `test logout when logout is successful`() = runTest {
        // Arrange
        // Act
        viewModel.logout()

        dispatcher.scheduler.advanceUntilIdle()

        // Then
        Mockito.verify(settingsPreference).setUserLogin(false)
    }

    @Test
    fun `validateForm should return true when validation passes`() {
        // Mock validation success
        whenever(validationUtils.validateName("John")).thenReturn(true)
        whenever(validationUtils.validateEmail("john@example.com")).thenReturn(true)
        whenever(validationUtils.validatePassword("password123")).thenReturn(true)
        whenever(validationUtils.validateTerms(true)).thenReturn(true)

        // Set valid inputs
        viewModel.onNameChange("John")
        viewModel.onEmailChange("john@example.com")
        viewModel.onPasswordChange("password123")
        viewModel.onCheckedChange(true)

        // Validate the form
        val isValid = viewModel.validateForm()

        // Assert the form is valid
        assertTrue(isValid)
    }

    @Test
    fun `onNameChange should update name state`() {
        val newName = "John Doe"
        viewModel.onNameChange(newName)

        // Assert that the name value is updated in the ViewModel
        assertEquals(newName, viewModel.name.value)
    }

    @Test
    fun `onEmailChange should update email state`() {
        val newEmail = "john.doe@example.com"
        viewModel.onEmailChange(newEmail)

        // Assert that the email value is updated in the ViewModel
        assertEquals(newEmail, viewModel.email.value)
    }

    @Test
    fun `onPasswordChange should update password state`() {
        val newPassword = "password456"
        viewModel.onPasswordChange(newPassword)

        // Assert that the password value is updated in the ViewModel
        assertEquals(newPassword, viewModel.password.value)
    }

    @Test
    fun `onCheckedChange should update checked state`() {
        val newCheckedState = true
        viewModel.onCheckedChange(newCheckedState)

        // Assert that the checked state value is updated
        assertTrue(viewModel.isChecked.value)
    }

    @Test
    fun `validateForm should return false when validation fails`() {
        // Mock validation failures
        whenever(validationUtils.validateName("")).thenReturn(false)
        whenever(validationUtils.getNameErrorMessage()).thenReturn("Name is required")

        whenever(validationUtils.validateEmail("john@example.com")).thenReturn(true)
        whenever(validationUtils.validatePassword("password123")).thenReturn(true)
        whenever(validationUtils.validateTerms(true)).thenReturn(true)



        // Set input values
        viewModel.onNameChange("")
        viewModel.onEmailChange("john@example.com")
        viewModel.onPasswordChange("password123")
        viewModel.onCheckedChange(true)


        // Validate the form
        val isValid = viewModel.validateForm()

        // Assert the form is not valid and the error message is set
        assertFalse(isValid)
        assertEquals("Name is required", viewModel.errorMessage.value)
    }

    @Test
    fun `validateForm should return false when email is invalid`() {
        // Mock invalid email validation
        whenever(validationUtils.validateName("John")).thenReturn(true)
        whenever(validationUtils.validatePassword("password123")).thenReturn(true)
        whenever(validationUtils.validateTerms(true)).thenReturn(true)

        whenever(validationUtils.validateEmail("invalid-email")).thenReturn(false)
        whenever(validationUtils.getEmailErrorMessage()).thenReturn("Invalid email address")

        // Set invalid email
        viewModel.onEmailChange("invalid-email")
        viewModel.onNameChange("John")
        viewModel.onPasswordChange("password123")
        viewModel.onCheckedChange(true)


        // Validate the form
        val isValid = viewModel.validateForm()

        // Assert the form is not valid and the error message is set for email
        assertFalse(isValid)
        assertEquals("Invalid email address", viewModel.errorMessage.value)
    }

    @Test
    fun `validateForm should return false when password is invalid`() {
        // Mock invalid password validation
        whenever(validationUtils.validatePassword("123")).thenReturn(false)
        whenever(validationUtils.getPasswordErrorMessage()).thenReturn("Password must be at least 8 characters")
        whenever(validationUtils.validateName("John")).thenReturn(true)
        whenever(validationUtils.validateEmail("john@example.com")).thenReturn(true)
        whenever(validationUtils.validateTerms(true)).thenReturn(true)

        // Set invalid password
        viewModel.onNameChange("John")
        viewModel.onEmailChange("john@example.com")
        viewModel.onPasswordChange("123")
        viewModel.onCheckedChange(true)

        // Validate the form
        val isValid = viewModel.validateForm()

        // Assert the form is not valid and the error message is set for password
        assertFalse(isValid)
        assertEquals("Password must be at least 8 characters", viewModel.errorMessage.value)
    }

    @Test
    fun `validateForm should return false when terms are not accepted`() {
        // Mock terms validation failure

        whenever(validationUtils.validateName("John")).thenReturn(true)
        whenever(validationUtils.validateEmail("john@example.com")).thenReturn(true)
        whenever(validationUtils.validatePassword("password123")).thenReturn(true)
        whenever(validationUtils.validateTerms(false)).thenReturn(false)
        whenever(validationUtils.getTermsErrorMessage()).thenReturn("You must agree to the terms")

        // Set valid inputs
        viewModel.onNameChange("John")
        viewModel.onEmailChange("john@example.com")
        viewModel.onPasswordChange("password123")
        viewModel.onCheckedChange(false)



        // Validate the form
        val isValid = viewModel.validateForm()

        // Assert the form is not valid and the error message is set for terms
        assertFalse(isValid)
        assertEquals("You must agree to the terms", viewModel.errorMessage.value)
    }

    @Test
    fun `test getUserLoginStatus updates _isLoggedIn`() = runTest {
        // Given
        val expectedLoggedInStatus = true
        whenever(settingsPreference.isUserLoggedIn).thenReturn(flowOf(expectedLoggedInStatus))

        // When
        viewModel.getUserLoginStatus()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedLoggedInStatus, viewModel.isUserLoggedIn.value)
    }
}
