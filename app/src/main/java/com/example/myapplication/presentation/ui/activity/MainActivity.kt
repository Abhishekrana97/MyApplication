package com.example.myapplication.presentation.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.presentation.ui.navigation.Navigation
import com.example.myapplication.presentation.viewmodel.SettingsScreenViewModel
import com.example.myapplication.theme.JetpackComposeTheme
import com.example.myapplication.utils.Constants.languageListItems
import com.example.myapplication.utils.Constants.updateLocale
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val biometricManager by lazy { BiometricManager.from(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)  // Initialize Firebase
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.my_google_map_api_key))
        }
        fcmToken()
        setContent {
            val viewModel = hiltViewModel<SettingsScreenViewModel>()
            LaunchedEffect(Unit) {
                viewModel.getFingerprintStatus()
                viewModel.getCurrentTheme()
                viewModel.getLanguagePref()
            }
            val darkModeState = viewModel.isDarkThemeEnabled.collectAsState()
            val fingerPrintState = viewModel.isFingerprintEnabled.collectAsState()
            val languagePref = viewModel.languagePref.collectAsState()
            val biometricState = remember { mutableStateOf(false) }

            updateLocale(this, languageListItems[languagePref.value].second)

            JetpackComposeTheme(useDarkTheme = darkModeState.value) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (fingerPrintState.value && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        biometricCheck {
                            biometricState.value = true
                        }
                    } else {
                        biometricState.value = true
                    }
                    if (biometricState.value) {
                        Navigation()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun biometricCheck(success: () -> Unit) {
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val biometricPrompt = BiometricPrompt(
                    this,
                    this.mainExecutor,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            Timber.d("Authentication successful!!!")
                            success()
                        }

                        override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence
                        ) {
                            Timber.e("onAuthenticationError >>>>> $errorCode, $errString")
                        }

                        override fun onAuthenticationFailed() {
                            Timber.e("onAuthenticationFailed")
                        }
                    })

                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle(getString(R.string.biometric_authentication))
                    .setDescription(getString(R.string.sensor_msg))
                    .setNegativeButtonText(getString(R.string.cancel))
                    .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                    .build()

                biometricPrompt.authenticate(promptInfo)
            }

            else -> {
                success()
                Timber.d(
                    "TAG" +
                            "Device does not support strong biometric authentication"
                )
            }
        }
    }

    private fun fcmToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.e("Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            Timber.e(">>>>>>>>>>>> $token")
        })
    }
}

