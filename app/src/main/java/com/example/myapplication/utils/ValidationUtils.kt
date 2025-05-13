package com.example.myapplication.utils

import java.time.LocalDate

object ValidationUtils {


    // Card number validation regex (16 digits)
    private val cardNumberPattern = Regex("^\\d{16}$")

    // CVV validation regex (3 digits)
    private val cardCvvPattern = Regex("^\\d{3}$")
    private val emailPatterns =
        Regex(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        )

    fun validateName(name: String): Boolean {
        return name.trim().isNotEmpty()
    }

    fun validateEmail(email: String): Boolean {
        return email.matches(emailPatterns)
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }

    fun validateTerms(isChecked: Boolean): Boolean {
        return isChecked
    }

    fun getNameErrorMessage(): String {
        return "Name is required"
    }

    fun getEmailErrorMessage(): String {
        return "Invalid email address"
    }

    fun getPasswordErrorMessage(): String {
        return "Password must be at least 8 characters"
    }

    fun getTermsErrorMessage(): String {
        return "You must agree to the terms"
    }

    // Expiry date validation method
    fun isCardValidExpiry(expiry: String): String? {
        return try {
            // Check if the format is MM/YY (should include the '/' separator)
            if (expiry.length != 5 || expiry[2] != '/') {
                return "Please enter a valid MM/YY format."
            }

            // Split the input into month and year
            val dateParts = expiry.split("/")
            if (dateParts.size == 2) {
                val month = dateParts[0].toInt()
                val year = dateParts[1].toInt()

                // Get the current date (month and year)
                val currentYear = LocalDate.now().year
                val currentMonth = LocalDate.now().monthValue

                // Get the year range (up to 5 years from the current year)
                val maxYear = currentYear + 5

                // Check if the month is valid (1-12)
                if (month !in 1..12) {
                    return "Invalid month. Please check the MM format."
                }

                // Check if the year is within the current year and 5 years from now
                if (year < currentYear % 100 || year > maxYear % 100) {
                    return "Year should be between current year and 5 years from now."
                }

                // If the year is the current year, ensure the month is not in the past
                if (year == currentYear % 100 && month < currentMonth) {
                    return "Card is expired. Please check the expiry date."
                }

                return null // Valid expiry date
            }

            // If the format is incorrect (not MM/YY)
            "Please enter a valid MM/YY format."
        } catch (e: Exception) {
            "Please enter a valid MM/YY format."
        }
    }

    // Validate card number
    fun validateCardNumber(cardNumber: String): String? {
        return if (!cardNumber.matches(cardNumberPattern)) {
            "Card number should be 16 digits."
        } else null
    }

    // Validate card CVV
    fun validateCvv(cvv: String): String? {
        return if (!cvv.matches(cardCvvPattern)) {
            "CVV should be 3 digits."
        } else null
    }

    // Validate card holder name
    fun validateCardName(cardName: String): String? {
        return if (cardName.isEmpty()) {
            "Holder name is required"
        } else null
    }
}
