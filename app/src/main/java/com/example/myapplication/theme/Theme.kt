package com.example.myapplication.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = Purple,
    secondary = Black,
    error = Red,
    errorContainer = Red,
    background = White,
    onBackground = White,
    surface = Black,
    surfaceVariant = Purple,
)


private val DarkColors = darkColorScheme(
    primary = Black,
    secondary = White,
    error = Red,
    errorContainer = Red,
    background = Black,
    onBackground = PurpleGrey80,
    surface = White,
    surfaceVariant = White,
)

@Composable
fun JetpackComposeTheme(
    useDarkTheme: Boolean = false,
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor =  if(!useDarkTheme) Purple.toArgb() else Black.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
