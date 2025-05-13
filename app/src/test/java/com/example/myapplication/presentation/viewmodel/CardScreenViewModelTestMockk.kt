package com.example.myapplication.presentation.viewmodel


import com.example.myapplication.data.model.AddCards
import com.example.myapplication.domain.usecase.AddCardUseCase
import com.example.myapplication.domain.usecase.CardUserCases
import com.example.myapplication.domain.usecase.DeleteCardUseCase
import com.example.myapplication.domain.usecase.GetCardUseCase
import com.example.myapplication.domain.usecase.UpdateCardUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@ExperimentalCoroutinesApi
class CardScreenViewModelTestMockk {

    private val addCardUseCase: AddCardUseCase = mockk()
    private val updateCardUseCase: UpdateCardUseCase = mockk()
    private val deleteCardUseCase: DeleteCardUseCase = mockk()
    private val getCardUseCase: GetCardUseCase = mockk()
    private lateinit var useCase: CardUserCases
    private val addCard: AddCards = mockk()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CardScreenViewModel

    @Before
    fun setup() {
        useCase = CardUserCases(
            addCardUseCase,
            updateCardUseCase,
            deleteCardUseCase,
            getCardUseCase
        )

        viewModel = CardScreenViewModel(useCase)
        Dispatchers.setMain(dispatcher) // Set the main dispatcher to the test dispatcher
    }

    @Test
    fun `test insertCard with valid data`() = runTest {
        val validCard = AddCards(
            id = 0,
            cardNumber = "1234567890123456",
            cardName = "John Doe",
            cvv = "123",
            validity = "12/25"
        )

        // Mocking the addCardUseCase to do nothing
        coEvery { useCase.addCardUseCase(validCard) } returns Unit

        // Setting valid values
        viewModel.cardNumber.value = "1234567890123456"
        viewModel.cardName.value = "John Doe"
        viewModel.cardCvv.value = "123"
        viewModel.cardValidity.value = "12/25"

        // Calling the insertCard method
        viewModel.insertCard(validCard)
        dispatcher.scheduler.advanceUntilIdle() // Ensure coroutines are finished

        // Verifying that the addCardUseCase was called with the correct card
        coVerify  { useCase.addCardUseCase(validCard) }
    }

    @Test
    fun `test insertCard with invalid card number`() = runTest {
        val invalidCard = AddCards(
            id = 0,
            "John Doe",  // Invalid card number
            "212334",
            "12/26", "123"
        )

        // Mocking the addCardUseCase to do nothing
        coEvery { useCase.addCardUseCase(invalidCard) } returns Unit

        // Setting invalid card number
        viewModel.cardNumber.value = "212334"  // Invalid length for card number
        viewModel.cardName.value = "John Doe"
        viewModel.cardCvv.value = "123"
        viewModel.cardValidity.value = "12/26"

        // Calling the insertCard method (should not proceed due to validation failure)
        viewModel.insertCard(invalidCard)
        dispatcher.scheduler.advanceUntilIdle() // Ensure coroutines are finished

        // Verify that the addCardUseCase was NOT called due to validation failure
        coVerify(exactly = 0) { useCase.addCardUseCase(invalidCard) }
    }

    @Test
    fun `test getCardsFromDB returns data`() = runTest {
        val mockCards = listOf(addCard)

        // Mocking the flow returned by getCardUseCase to return mock data
        coEvery { useCase.getCardUseCase() } returns flow { emit(mockCards) }

        // Calling getCardsFromDB
        viewModel.getCardsFromDB()
        dispatcher.scheduler.advanceUntilIdle() // Ensure coroutines are finished

        // Asserting that the _cards state flow contains the mocked list of cards
        assertEquals(viewModel.cards.value, mockCards)
    }

    @Test
    fun `test deleteCard is called`() = runTest {
        // Mocking the deleteCardUseCase to do nothing
        coEvery { useCase.deleteCardUseCase(addCard) } returns Unit

        // Calling the deleteCard method
        viewModel.deleteCard(addCard)
        dispatcher.scheduler.advanceUntilIdle() // Ensure coroutines are finished

        // Verifying that deleteCardUseCase was called with the correct card
        coVerify { useCase.deleteCardUseCase(addCard) }
    }

    @Test
    fun `test onCardNameChange updates the cardName`() {
        val newCardName = "Jane Doe"

        // Calling the method to change the card name
        viewModel.onCardNameChange(newCardName)

        // Asserting that the cardName state has been updated
        assertEquals(viewModel.cardName.value, newCardName)
    }

    @Test
    fun `test validateCardInfo sets correct errors for invalid data`() {
        // Set invalid data
        viewModel.cardNumber.value = "1234"  // Invalid card number
        viewModel.cardName.value = ""
        viewModel.cardCvv.value = "12"  // Invalid CVV
        viewModel.cardValidity.value = "12/20"

        // Call validateCardInfo
        viewModel.validateCardInfo()

        // Assert that errors are set correctly
        assertNotEquals(viewModel.cardNumberError.value, null)
        assertNotEquals(viewModel.cardNameError.value, null)
        assertNotEquals(viewModel.cardCvvError.value, null)
        assertNotEquals(viewModel.cardValidityError.value, null)  // Valid expiration date
    }

    @Test
    fun `test onCardNumberChange updates cardNumber`() {
        val newCardNumber = "1234567890123456"

        // Calling the method to change the card number
        viewModel.onCardNumberChange(newCardNumber)

        // Asserting that the cardNumber state has been updated
        assertEquals(viewModel.cardNumber.value, newCardNumber)
    }

    @Test
    fun `test onCvvChange updates cardCvv when valid CVV is provided`() {
        val newCvv = "123"

        // Calling the method to change the CVV
        viewModel.onCvvChange(newCvv)

        // Asserting that the cardCvv state has been updated
        assertEquals(viewModel.cardCvv.value, newCvv)
    }

    @Test
    fun `test onValidityChange updates cardValidity for valid input`() {
        val newValidity = "12"

        // Calling the method to change the validity
        viewModel.onValidityChange(newValidity)

        // Asserting that the cardValidity state has been updated with a correct format
        assertEquals(viewModel.cardValidity.value, "$newValidity/")
    }

    @Test
    fun `test onValidityChange`() {
        val newValidity = "12/26"  // Invalid format (should truncate after MM/YY)

        // Calling the method to change the validity
        viewModel.onValidityChange(newValidity)

        // Asserting that the cardValidity state has been truncated to "12/26"
        assertEquals(viewModel.cardValidity.value, "12/26")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the dispatcher after tests
    }
}
