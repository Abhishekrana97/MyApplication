package com.example.myapplication.utils

import android.content.Context
import android.os.Build
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.myapplication.R
import com.example.myapplication.theme.Purple
import java.util.Locale

object Constants {

    const val BASE_URL = "https://mocki.io/v1/"
    const val PDF_URL = "https://sist.sathyabama.ac.in/sist_naac/documents/1.3.4/1822-b.e-cse-batchno-224.pdf"
    const val EXAMPLE_VIDEO_URI = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    fun getCardLogo(cardNumber: String): Int {
        return when {
            cardNumber.startsWith("4") -> R.drawable.ic_visa // Visa logo
            cardNumber.startsWith("5") -> R.drawable.ic_master_card // MasterCard logo
            cardNumber.startsWith("6") -> R.drawable.ic_rupay // RuPay logo
            else -> R.drawable.ic_visa // Default logo (if no match)
        }
    }

    val languageListItems = listOf(
        Pair("English", Locale.ENGLISH),
        Pair("Hindi", Locale("hi")),
        Pair("Punjabi", Locale("pa"))
    )


    fun getCardTitle(cardNumber: String): String {
        return when {
            cardNumber.startsWith("4") -> "Visa Card"
            cardNumber.startsWith("5") -> "Master Card"
            cardNumber.startsWith("6") -> "RuPay Card"
            else -> "Visa Card"
        }
    }

    fun getCardbg(cardNumber: String): Brush {
        return when {
            cardNumber.startsWith("4") -> Brush.verticalGradient(
                colors = listOf(Purple, Color.Black)
            )

            cardNumber.startsWith("5") -> Brush.verticalGradient(
                colors = listOf(Color.Red, Color.Black)
            )

            cardNumber.startsWith("6") -> Brush.verticalGradient(
                colors = listOf(Color.Blue, Color.Black)
            )

            else -> Brush.verticalGradient(
                colors = listOf(Purple, Color.Black)
            )
        }
    }

    fun updateLocale(context: Context, locale: Locale) {
        val resources = context.resources
        val configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}