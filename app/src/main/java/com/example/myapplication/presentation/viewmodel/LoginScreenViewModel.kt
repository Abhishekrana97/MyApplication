package com.example.myapplication.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.LoginUseCases
import com.example.myapplication.utils.DataState
import com.example.myapplication.utils.SettingsPreference
import com.example.myapplication.utils.ValidationUtils
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: LoginUseCases,
    private val settingsPreference: SettingsPreference
) : ViewModel() {

    private val _usersState = MutableStateFlow<FirebaseUser?>(null)
    val usersState get() = _usersState.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Form data
    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _isChecked = mutableStateOf(false)
    val isChecked: State<Boolean> = _isChecked

    private val _nameError = mutableStateOf(false)
    val nameError: State<Boolean> = _nameError

    private val _emailError = mutableStateOf(false)
    val emailError: State<Boolean> = _emailError

    private val _passwordError = mutableStateOf(false)
    val passwordError: State<Boolean> = _passwordError

    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn get() = _isLoggedIn.asStateFlow()

    // Validation function
    fun validateForm(): Boolean {
        val isNameValid = ValidationUtils.validateName(_name.value)
        val isEmailValid = ValidationUtils.validateEmail(_email.value)
        val isPasswordValid = ValidationUtils.validatePassword(_password.value)
        val isCheckedValid = ValidationUtils.validateTerms(_isChecked.value)

        if (!isNameValid) {
            _errorMessage.value = ValidationUtils.getNameErrorMessage()
        } else if (!isEmailValid) {
            _errorMessage.value = ValidationUtils.getEmailErrorMessage()
        } else if (!isPasswordValid) {
            _errorMessage.value = ValidationUtils.getPasswordErrorMessage()
        } else if (!isCheckedValid) {
            _errorMessage.value = ValidationUtils.getTermsErrorMessage()
        }

        _nameError.value = !isNameValid
        _emailError.value = _email.value.trim().isEmpty() || !isEmailValid
        _passwordError.value = _password.value.trim().isEmpty() || !isPasswordValid

        return isNameValid && isEmailValid && isPasswordValid && isCheckedValid
    }



    fun getUserLoginStatus(){
        viewModelScope.launch {
            settingsPreference.isUserLoggedIn.collect{value ->
                _isLoggedIn.value = value
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.registerUserUseCase(email, password).let {
                when (it) {
                    is DataState.Success -> {
                        settingsPreference.setUserLogin(true)
                        _usersState.value = it.data
                        _isLoading.value = false
                    }

                    is DataState.Error -> {
                        _errorMessage.value = it.exception.localizedMessage
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.loginUserUseCase(email, password).let {
                when (it) {
                    is DataState.Success -> {
                        settingsPreference.setUserLogin(true)
                        _usersState.value = it.data
                        _isLoading.value = false
                    }

                    is DataState.Error -> {
                        _errorMessage.value = it.exception.localizedMessage
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onCheckedChange(newCheckedState: Boolean) {
        _isChecked.value = newCheckedState
    }

    fun logout() {
        viewModelScope.launch {
            repository.logoutUserUseCase()
            settingsPreference.setUserLogin(false)
        }
    }
}