package com.example.myapplication.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.AddCards
import com.example.myapplication.domain.usecase.CardUserCases
import com.example.myapplication.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardScreenViewModel @Inject constructor(
    private val useCase: CardUserCases
) : ViewModel() {

    var cardNumber = mutableStateOf("")
    var cardName = mutableStateOf("")
    var cardCvv = mutableStateOf("")
    var cardValidity = mutableStateOf("")

    var cardNumberError = mutableStateOf<String?>(null)
    var cardNameError = mutableStateOf<String?>(null)
    var cardCvvError = mutableStateOf<String?>(null)
    var cardValidityError = mutableStateOf<String?>(null)

    private val _cards = MutableStateFlow<List<AddCards>?>(null)
    val cards get() = _cards.asStateFlow()

    private val _isCardAdded = MutableStateFlow(false)
    val isCardAdded get() = _isCardAdded.asStateFlow()

    fun getCardsFromDB() {
        viewModelScope.launch {
            useCase.getCardUseCase().collect { data ->
                _cards.value = data
            }
        }
    }

    fun insertCard(addCards: AddCards) {
        validateCardInfo()
        // Check if card number already exists
        val existingCard = _cards.value?.find { it.cardNumber == addCards.cardNumber }

        if (existingCard != null && existingCard.id != addCards.id) {
            // Card number already exists
            cardNumberError.value = "Card number already exists"
        }else {
            if (cardNumberError.value == null && cardCvvError.value == null && cardValidityError.value == null) {
                viewModelScope.launch {
                    _isCardAdded.value = false
                    if (addCards.id != 0) {
                        useCase.updateCardUseCase(addCards)
                    } else {
                        useCase.addCardUseCase(addCards)
                    }
                    _isCardAdded.value = true
                }
            }
        }
    }

    fun deleteCard(addCards: AddCards) {
        viewModelScope.launch {
            useCase.deleteCardUseCase(addCards)
        }
    }

    // A method to validate all fields before inserting card
    fun validateCardInfo() {
        cardNameError.value = ValidationUtils.validateCardName(cardName.value)
        cardNumberError.value = ValidationUtils.validateCardNumber(cardNumber.value)
        cardCvvError.value = ValidationUtils.validateCvv(cardCvv.value)
        cardValidityError.value = ValidationUtils.isCardValidExpiry(cardValidity.value)
    }

    fun onCardNameChange(value: String) {
        cardName.value = value
        cardNameError.value = ValidationUtils.validateCardName(cardName.value)
    }

    fun onCardNumberChange(newCardNumber: String) {
        cardNumber.value = newCardNumber
        cardNumberError.value = ValidationUtils.validateCardNumber(cardNumber.value)
    }

    fun onCvvChange(newCvv: String) {
        if (newCvv.length <= 3 && newCvv.all { it.isDigit() }) {
            cardCvv.value = newCvv
        }
        cardCvvError.value = ValidationUtils.validateCvv(cardCvv.value)
    }

    fun onValidityChange(newValidity: String) {
        if (newValidity.length == 2 && newValidity[1].isDigit()) {
            cardValidity.value = "$newValidity/"
        } else if (newValidity.length > 5) {
            cardValidity.value = cardValidity.value.take(5) // Limit the string to MM/YY
        } else {
            cardValidity.value = newValidity
        }

        cardValidityError.value = ValidationUtils.isCardValidExpiry(cardValidity.value)
    }

    fun resetCardAddedState(){
        _isCardAdded.value = false
    }
}
