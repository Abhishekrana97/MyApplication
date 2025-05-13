package com.example.myapplication.presentation.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.data.model.DepositMoney
import com.example.myapplication.presentation.ui.component.CustomRadioButton
import com.example.myapplication.presentation.ui.component.ToolbarView
import com.example.myapplication.presentation.viewmodel.CardScreenViewModel
import com.example.myapplication.presentation.viewmodel.DepositListViewModel
import com.example.myapplication.theme.Black
import com.example.myapplication.theme.HorizontalButtonGradient
import com.example.myapplication.theme.LightGrey
import com.example.myapplication.theme.Purple
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositScreen(onBackPressed: () -> Unit) {
    var sliderValue by remember { mutableStateOf(0f) }
    val viewModel = hiltViewModel<DepositListViewModel>()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var selectedIndex by remember { mutableIntStateOf(-1) }
    val coroutineScope = rememberCoroutineScope()
    val cardViewModel = hiltViewModel<CardScreenViewModel>()

    LaunchedEffect(Unit) {
        cardViewModel.getCardsFromDB()
    }

    val cards by cardViewModel.cards.collectAsState()

    Column {
        ToolbarView(onBackPressed, stringResource(R.string.deposit))


        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(20.dp)

        ) {


            val (progress, percentage, slider, btnYes, btnNo) = createRefs()

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .requiredHeight(300f.dp)
                    .constrainAs(progress) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {

                val sweepAngle = (sliderValue / 5000f) * 360f  // Scale to fit 0-360 degrees

                inset(size.width / 2 - 300f, size.height / 2 - 300f) {
                    drawCircle(
                        color = LightGrey,
                        radius = 300f,
                        center = center,
                        style = Stroke(width = 50f, cap = StrokeCap.Round)
                    )

                    drawArc(
                        brush = HorizontalButtonGradient,
                        startAngle = 270f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        style = Stroke(width = 50f, cap = StrokeCap.Round)
                    )
                }
            }


            Text(
                text = "$${sliderValue.toInt()}",
                color = Purple,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(percentage) {
                        top.linkTo(progress.top)
                        start.linkTo(progress.start)
                        end.linkTo(progress.end)
                        bottom.linkTo(progress.bottom)
                    }
            )


            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                valueRange = 0f..5000f, // Set range from 0 to 5000
                steps = 5000, // Optionally add steps to make values more granular
                onValueChangeFinished = {

                },

                modifier = Modifier
                    .constrainAs(slider) {
                        top.linkTo(progress.bottom, margin = 40.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }

            )


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(90.dp)
                    .padding(10.dp)
                    .constrainAs(btnYes) {
                        top.linkTo(slider.bottom, margin = 40.dp)
                        start.linkTo(parent.start)
                        end.linkTo(btnNo.start)
                        width = Dimension.fillToConstraints
                    }
                    .background(HorizontalButtonGradient, shape = RoundedCornerShape(10))
                    .clickable {
                        showBottomSheet = true
                    }
                    .padding(horizontal = 40.dp, vertical = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.Yes),
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(90.dp)
                    .padding(10.dp)
                    .border(
                        width = 2.dp,
                        brush = HorizontalButtonGradient,
                        shape = RoundedCornerShape(10)
                    )
                    .clickable {
                        onBackPressed()
                    }
                    .padding(horizontal = 40.dp, vertical = 10.dp)
                    .constrainAs(btnNo) {
                        top.linkTo(btnYes.top)
                        bottom.linkTo(btnYes.bottom)
                        start.linkTo(btnYes.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                Text(
                    text = stringResource(R.string.no),
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                )
            }


            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    tonalElevation = 16.dp,
                    dragHandle = {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .width(50.dp)
                                .height(6.dp)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                ) {
                    ConstraintLayout(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                        val (text,languageList, btnSubmit) = createRefs()

                        Text(
                            text = stringResource(R.string.select_card),
                            color = Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .constrainAs(text) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start, margin = 6.dp)
                                    end.linkTo(parent.end)
                                    width = Dimension.fillToConstraints
                                }
                        )

                        LazyColumn(modifier = Modifier.constrainAs(languageList){
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(text.bottom, margin = 10.dp)
                            width = Dimension.fillToConstraints
                        }) {
                            cards?.let {
                                itemsIndexed(items = it) { index, item ->
                                    // Row for each item with a RadioButton and text
                                    CustomRadioButton(
                                        text = item.cardName,
                                        isSelected = selectedIndex == index,
                                        onSelect = { selectedIndex = index }
                                    )
                                }
                            }
                        }

                        Button(
                            modifier = Modifier.constrainAs(btnSubmit){
                                end.linkTo(parent.end)
                                top.linkTo(languageList.bottom, margin = 10.dp)
                            },
                            onClick = {
                                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false

                                        val date = Date(System.currentTimeMillis())
                                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                        val formattedDate = dateFormat.format(date)
                                        viewModel.insertMoney(DepositMoney(money = "$${sliderValue}", card_id = cards?.get(selectedIndex)?.id ?: -1,date = formattedDate))
                                        onBackPressed()
                                    }
                                }
                            }) {
                            Text(stringResource(R.string.confirm))
                        }
                    }
                }
            }
        }
    }
}



