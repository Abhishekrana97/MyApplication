package com.example.myapplication.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.presentation.ui.component.ToolbarView
import com.example.myapplication.presentation.viewmodel.CurrencyScreenViewModel
import com.example.myapplication.theme.Black
import com.example.myapplication.theme.HorizontalButtonGradient
import com.example.myapplication.theme.Purple
import com.example.myapplication.utils.Navigation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeCurrencyScreen(onBackPressed: () -> Unit, navController: NavHostController) {

    val viewModel = hiltViewModel<CurrencyScreenViewModel>()

    val isLoading by viewModel.isLoading.collectAsState()
    val currencyDetails by viewModel.updatedCurrencyValue.collectAsState()
    val currentCurrencyKey by viewModel.currentCurrencyKey.collectAsState()
    val targetCurrencyKey by viewModel.targetCurrencyKey.collectAsState()
    val sliderValue by viewModel.sliderValue.collectAsState()
    val convertedValue by viewModel.convertedValue.collectAsState()

    LaunchedEffect(currentCurrencyKey) {
        if (currencyDetails != null)
            viewModel.getCurrencyDetails(currentCurrencyKey)
        else
            viewModel.getCurrencyDetails("USD")
    }

    val isCurrentDropDownExpanded = remember { mutableStateOf(false) }
    val isTargetDropDownExpanded = remember { mutableStateOf(false) }

    Column {
        ToolbarView(onBackPressed, stringResource(R.string.exchange_currency))

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(20.dp)
            ) {

                val (tvCurrentCurrency, tvTargetCurrency,
                    tvCurrencyDetails,
                    slider, btnNext,note, tvCurrentCurrencyValue, tvTargetCurrencyValue) = createRefs()


                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .constrainAs(tvCurrentCurrency) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top, margin = 30.dp)
                            end.linkTo(tvTargetCurrency.start)
                        }
                        .width(150.dp),
                    expanded = isCurrentDropDownExpanded.value,
                    onExpandedChange = {
                        isCurrentDropDownExpanded.value = !isCurrentDropDownExpanded.value
                    },
                ) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        readOnly = true,
                        value = currentCurrencyKey,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.current_currency)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCurrentDropDownExpanded.value) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = isCurrentDropDownExpanded.value,
                        onDismissRequest = { isCurrentDropDownExpanded.value = false },
                    ) {
                        currencyDetails?.rates?.keys?.forEach { value ->
                            DropdownMenuItem(text = { Text(text = value) },
                                onClick = {
                                    isCurrentDropDownExpanded.value = false
                                    viewModel.onCurrentCurrencySelected(value)
                                })
                        }
                    }
                }


                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .constrainAs(tvTargetCurrency) {
                            start.linkTo(tvCurrentCurrency.end)
                            top.linkTo(tvCurrentCurrency.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(tvCurrentCurrency.bottom)
                        }
                        .width(150.dp),
                    expanded = isTargetDropDownExpanded.value,
                    onExpandedChange = {
                        isTargetDropDownExpanded.value = !isTargetDropDownExpanded.value
                    },
                ) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        readOnly = true,
                        value = targetCurrencyKey,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.target_currency)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isTargetDropDownExpanded.value) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = isTargetDropDownExpanded.value,
                        onDismissRequest = { isTargetDropDownExpanded.value = false },
                    ) {
                        currencyDetails?.rates?.keys?.forEach { value ->
                            DropdownMenuItem(text = { Text(text = value) },
                                onClick = {
                                    isTargetDropDownExpanded.value = false
                                    viewModel.onTargetCurrencySelected(value)
                                })
                        }
                    }
                }

                Text(
                    text = "1 $currentCurrencyKey = ${currencyDetails?.rates?.get(targetCurrencyKey)} $targetCurrencyKey",
                    color = Purple,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .constrainAs(tvCurrencyDetails) {
                            top.linkTo(tvTargetCurrency.bottom, margin = 50.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Slider(
                    value = sliderValue,
                    onValueChange = { viewModel.onSliderValueChanged(it) },
                    valueRange = 0f..24f,
                    modifier = Modifier
                        .constrainAs(slider) {
                            top.linkTo(tvCurrencyDetails.bottom, margin = 50.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Text(
                    text = stringResource(R.string.currency_limit_note),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .constrainAs(note) {
                            top.linkTo(slider.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }

                )

                Text(
                    text = "$sliderValue $currentCurrencyKey",
                    color = Purple,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(16.dp)
                        .width(160.dp)
                        .constrainAs(tvCurrentCurrencyValue) {
                            top.linkTo(note.bottom, margin = 60.dp)
                            start.linkTo(parent.start)
                            end.linkTo(tvTargetCurrencyValue.start)
                        }
                        .border(
                            width = 2.dp,
                            brush = HorizontalButtonGradient,
                            shape = RoundedCornerShape(10)
                        )
                        .padding(16.dp)
                )

                Text(
                    text = "$convertedValue $targetCurrencyKey",
                    color = Purple,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(16.dp)
                        .width(160.dp)
                        .constrainAs(tvTargetCurrencyValue) {
                            top.linkTo(tvCurrentCurrencyValue.top)
                            start.linkTo(tvCurrentCurrencyValue.end)
                            bottom.linkTo(tvCurrentCurrencyValue.bottom)
                            end.linkTo(parent.end)
                        }
                        .border(
                            width = 2.dp,
                            brush = HorizontalButtonGradient,
                            shape = RoundedCornerShape(10)
                        )
                        .padding(16.dp)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(90.dp)
                        .padding(10.dp)
                        .constrainAs(btnNext) {
                            top.linkTo(tvCurrentCurrencyValue.bottom, margin = 40.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .background(HorizontalButtonGradient, shape = RoundedCornerShape(10))
                        .clickable {
                            navController.navigate(Navigation.Expenses.route)
                        }
                        .padding(horizontal = 40.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.next_step),
                        color = Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                    )
                }
            }
        }
    }
}




