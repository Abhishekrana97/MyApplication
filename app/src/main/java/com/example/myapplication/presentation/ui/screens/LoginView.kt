package com.example.myapplication.presentation.ui.screens;

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.presentation.viewmodel.LoginScreenViewModel
import com.example.myapplication.theme.Black
import com.example.myapplication.theme.HorizontalButtonGradient
import com.example.myapplication.utils.Navigation

@Composable
fun LoginScreenView(navController: NavHostController, isNewUser: Boolean?) {
    val loginScreenViewModel = hiltViewModel<LoginScreenViewModel>()

    val name = loginScreenViewModel.name.value
    val email = loginScreenViewModel.email.value
    val password = loginScreenViewModel.password.value
    val isChecked = loginScreenViewModel.isChecked.value

    val nameError = loginScreenViewModel.nameError.value
    val emailError = loginScreenViewModel.emailError.value
    val passwordError = loginScreenViewModel.passwordError.value
    val snackbarMessage by loginScreenViewModel.errorMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    var passwordVisible by remember { mutableStateOf(false) }
    val isLoading by loginScreenViewModel.isLoading.collectAsState()
    val userData by loginScreenViewModel.usersState.collectAsState()


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(50.dp))
                Text(
                    text = stringResource(R.string.login),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(50.dp))

                // Name TextField
                TextField(
                    value = name,
                    onValueChange = { loginScreenViewModel.onNameChange(it) },
                    label = { Text(stringResource(R.string.name)) },
                    singleLine = true,
                    isError = nameError,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                        errorIndicatorColor = Color.Red
                    )
                )

                Spacer(modifier = Modifier.size(30.dp))

                // Email TextField
                TextField(
                    value = email,
                    onValueChange = { loginScreenViewModel.onEmailChange(it) },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = emailError,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                        errorIndicatorColor = Color.Red
                    )
                )

                Spacer(modifier = Modifier.size(30.dp))

                TextField(
                    value = password,
                    onValueChange = { loginScreenViewModel.onPasswordChange(it) },
                    label = { Text(stringResource(R.string.password)) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = passwordError,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            val iconRes =
                                if (passwordVisible) R.drawable.show_password else R.drawable.hide_password
                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.surface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                        errorIndicatorColor = Color.Red
                    )
                )


                Spacer(modifier = Modifier.height(50.dp))

                // Checkbox for terms and conditions
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { loginScreenViewModel.onCheckedChange(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Transparent,
                            uncheckedColor = MaterialTheme.colorScheme.onSurface,
                            checkmarkColor = MaterialTheme.colorScheme.surface
                        )
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = stringResource(R.string.terms_conditions),
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                    )
                }

                // Handle Snackbar Message
                snackbarMessage?.let {
                    LaunchedEffect(snackbarMessage) {
                        snackbarHostState.showSnackbar(
                            message = it,
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))

                // Submit Button
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(90.dp)
                        .padding(10.dp)
                        .background(HorizontalButtonGradient, shape = RoundedCornerShape(10))
                        .clickable {
                            if (loginScreenViewModel.validateForm()) {
                                // If form is valid, store data and navigate
                                if (isNewUser == true)
                                    loginScreenViewModel.register(email, password)
                                else
                                    loginScreenViewModel.login(email, password)

                            }
                        }
                        .padding(horizontal = 80.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = if (isNewUser == true) stringResource(R.string.register) else stringResource(
                            R.string.login
                        ),
                        color = Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                    )
                }

                userData?.let {
                    if (it.email == email)
                        navController.navigate(Navigation.Card.route)
                }
            }
    }
}

