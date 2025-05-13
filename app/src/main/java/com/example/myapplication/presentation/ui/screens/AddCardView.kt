package com.example.myapplication.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.data.model.AddCards
import com.example.myapplication.presentation.ui.component.ToolbarView
import com.example.myapplication.presentation.viewmodel.CardScreenViewModel
import com.example.myapplication.theme.Black
import com.example.myapplication.theme.HorizontalButtonGradient
import com.example.myapplication.theme.Purple
import com.example.myapplication.utils.Constants.getCardLogo


@Composable
fun AddCardView(onBackPressed: () -> Unit, addCardData: AddCards? = null) {
    val viewModel = hiltViewModel<CardScreenViewModel>()
    val isCardAdded by viewModel.isCardAdded.collectAsState()

    // Launch effect to pre-fill fields with existing card data if available
    LaunchedEffect(Unit) {
        viewModel.getCardsFromDB()
        addCardData?.let {
            viewModel.onCvvChange(it.cvv)
            viewModel.onValidityChange(it.validity)
            viewModel.onCardNumberChange(it.cardNumber)
            viewModel.onCardNameChange(it.cardName)
        }
    }

    LaunchedEffect(isCardAdded) {
        if(isCardAdded){
            onBackPressed()
            viewModel.resetCardAddedState()
        }
    }

    val cardNumber = viewModel.cardNumber.value
    val cardName = viewModel.cardName.value
    val cardCvv = viewModel.cardCvv.value
    val validity = viewModel.cardValidity.value
    val cardNumberError = viewModel.cardNumberError.value
    val cardNameError = viewModel.cardNameError.value
    val cardCvvError = viewModel.cardCvvError.value
    val cardValidityError = viewModel.cardValidityError.value

    val pattern = remember { Regex("^\\d+\$") }


    Column {
        // Top Toolbar
        ToolbarView(onBackPressed, stringResource(R.string.add_card))

        // Main form
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(20.dp)
        ) {
            val (heading, terms, divider, linkBtn, card) = createRefs()

            Text(
                text = stringResource(R.string.add_your_bank_card),
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier
                    .constrainAs(heading) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            ElevatedCard(
                modifier = Modifier
                    .constrainAs(card) {
                        top.linkTo(heading.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Cyan)
                        .padding(20.dp)
                ) {
                    val (title, cardLogo, valid, cvv, number,cardHolderName) = createRefs()

                    Text(
                        text = stringResource(R.string.bank_card),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .constrainAs(title) {
                                top.linkTo(cardLogo.top)
                                bottom.linkTo(cardLogo.bottom)
                                start.linkTo(parent.start)
                            }
                    )

                    Image(
                        painter = painterResource(getCardLogo(cardNumber)),
                        contentDescription = "Card Logo",
                        modifier = Modifier
                            .size(40.dp)
                            .constrainAs(cardLogo) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }
                    )


                    TextField(
                        value = cardName,
                        onValueChange = { viewModel.onCardNameChange(it) },
                        label = { Text(stringResource(R.string.card_holder_number)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        isError = cardNameError != null,
                        supportingText = {
                            cardNameError?.let {
                                Text(text = it, color = Color.Red)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(cardHolderName) {
                                top.linkTo(cardLogo.bottom, margin = 10.dp)
                                end.linkTo(parent.end)
                                start.linkTo(parent.start)
                            },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedIndicatorColor = Purple,
                            errorIndicatorColor = Color.Red
                        )
                    )


                    TextField(
                        value = cardNumber,
                        onValueChange = {
                            if (it.length <= 16 && (it.isEmpty() || it.matches(pattern))) {
                                viewModel.onCardNumberChange(it)
                            }
                        },
                        label = { Text(stringResource(R.string.card_number)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = cardNumberError != null,
                        supportingText = {
                            cardNumberError?.let {
                                Text(text = it, color = Color.Red)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(number) {
                                top.linkTo(cardHolderName.bottom, margin = 10.dp)
                                end.linkTo(parent.end)
                                start.linkTo(parent.start)
                            },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedIndicatorColor = Purple,
                            errorIndicatorColor = Color.Red
                        )
                    )

                    TextField(
                        value = validity,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() || char == '/' }) {
                                viewModel.onValidityChange(it)
                            }
                        },
                        label = { Text(stringResource(R.string.mm_yy)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = cardValidityError != null,
                        supportingText = {
                            cardValidityError?.let {
                                Text(text = it, color = Color.Red)
                            }
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .constrainAs(valid) {
                                top.linkTo(number.bottom, margin = 20.dp)
                                start.linkTo(parent.start)
                            },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedIndicatorColor = Purple,
                            errorIndicatorColor = Color.Red
                        )
                    )

                    TextField(
                        value = cardCvv,
                        onValueChange = {
                            viewModel.onCvvChange(it)
                        },
                        label = { Text(stringResource(R.string.cvv)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = cardCvvError != null,
                        supportingText = {
                            cardCvvError?.let {
                                Text(text = it, color = Color.Red)
                            }
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .constrainAs(cvv) {
                                end.linkTo(parent.end)
                                top.linkTo(valid.top)
                                bottom.linkTo(valid.bottom)
                            },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedIndicatorColor = Purple,
                            errorIndicatorColor = Color.Red
                        )
                    )
                }
            }

            Text(
                text = stringResource(R.string.card_terms),
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = Black,
                modifier = Modifier
                    .constrainAs(terms) {
                        top.linkTo(card.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            HorizontalDivider(color = Color.Cyan, thickness = 1.5.dp,
                modifier = Modifier
                    .constrainAs(divider) {
                        top.linkTo(terms.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(90.dp)
                    .padding(10.dp)
                    .background(HorizontalButtonGradient, shape = RoundedCornerShape(10))
                    .constrainAs(linkBtn) {
                        top.linkTo(divider.bottom, margin = 40.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .clickable {
                        viewModel.insertCard(
                            AddCards(
                                addCardData?.id ?: 0,
                                cardName = cardName,
                                cardNumber = cardNumber,
                                cvv = cardCvv,
                                validity = validity
                            )
                        )
                    }
                    .padding(horizontal = 80.dp, vertical = 10.dp)
            ) {
                Text(
                    text = if (addCardData == null) stringResource(R.string.link_card) else stringResource(
                        R.string.update_card
                    ),
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                )
            }
        }
    }
}

