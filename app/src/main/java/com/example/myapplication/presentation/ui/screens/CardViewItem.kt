package com.example.myapplication.presentation.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.model.AddCards
import com.example.myapplication.presentation.ui.component.AlertDialogComponent
import com.example.myapplication.presentation.viewmodel.CardScreenViewModel
import com.example.myapplication.utils.Constants.getCardLogo
import com.example.myapplication.utils.Constants.getCardTitle
import com.example.myapplication.utils.Constants.getCardbg
import com.example.myapplication.utils.Navigation
import com.google.gson.Gson


@Composable
fun CardViewItem(card: AddCards, navController: NavHostController) {

    val viewModel = hiltViewModel<CardScreenViewModel>()
    val showDialog = remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, bottom = 16.dp, end = 8.dp)
    ) {
        val (elevatedCard, delete, edit) = createRefs()

        ElevatedCard(
            modifier = Modifier
                .constrainAs(elevatedCard) {
                    top.linkTo(parent.top)
                    end.linkTo(delete.start, margin = 4.dp)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                },
            shape = RoundedCornerShape(8.dp)
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(getCardbg(card.cardNumber))
            ) {
                val (title, cardType, cardNumber, cardName, validity, cvv) = createRefs()

                // Card Title (Card Name or Bank Name)
                Text(
                    text = getCardTitle(card.cardNumber),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(title) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(parent.top, margin = 16.dp)
                    }
                )

                // Card Logo at the top right
                Image(
                    painter = painterResource(getCardLogo(card.cardNumber)),
                    contentDescription = "Card Logo",
                    modifier = Modifier
                        .size(40.dp)
                        .constrainAs(cardType) {
                            top.linkTo(title.top)
                            bottom.linkTo(title.bottom)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                )

                Text(
                    text = card.cardNumber,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(cardNumber) {
                        top.linkTo(title.bottom, margin = 20.dp)
                        start.linkTo(title.start)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                Text(
                    text = stringResource(R.string.holder_name) + card.cardName,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.constrainAs(cardName) {
                        top.linkTo(cardNumber.bottom, margin = 20.dp)
                        start.linkTo(cardNumber.start)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                Text(
                    text = stringResource(R.string.validity) + card.validity,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.constrainAs(validity) {
                        start.linkTo(title.start)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        top.linkTo(cardName.bottom, margin = 20.dp)
                    }
                )

                Text(
                    text = stringResource(R.string.cvv_txt) + card.cvv,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.constrainAs(cvv) {
                        top.linkTo(validity.top)
                        bottom.linkTo(validity.bottom)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                )
            }
        }

        // Edit button
        IconButton(
            onClick = {
                val encodedCardJson = Uri.encode(Gson().toJson(card))

                navController.navigate("${Navigation.EditCard.route}/${encodedCardJson}")
            },
            modifier = Modifier
                .constrainAs(edit) {
                    top.linkTo(delete.bottom)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier.size(24.dp),
                tint = Color.Green
            )
        }

        // Delete button
        IconButton(
            onClick = {
                showDialog.value = true
            },
            modifier = Modifier
                .constrainAs(delete) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(edit.top)
                }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                tint = Color.Red,
                contentDescription = "Delete",
                modifier = Modifier.size(24.dp)
            )
        }

        // Confirmation Dialog for Deletion
        if (showDialog.value) {
            AlertDialogComponent(
                stringResource(R.string.delete_card), stringResource(R.string.delete_card_msg),
                onClick = {
                    viewModel.deleteCard(card)
                },
                onDismissClick = {
                    showDialog.value = false
                })
        }
    }
}