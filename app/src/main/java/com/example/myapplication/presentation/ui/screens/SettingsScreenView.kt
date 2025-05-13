package com.example.myapplication.presentation.ui.screens

import android.app.Activity
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.presentation.ui.component.AlertDialogComponent
import com.example.myapplication.presentation.ui.component.CustomRadioButton
import com.example.myapplication.presentation.ui.component.ToolbarView
import com.example.myapplication.presentation.viewmodel.LoginScreenViewModel
import com.example.myapplication.presentation.viewmodel.SettingsScreenViewModel
import com.example.myapplication.theme.Black
import com.example.myapplication.theme.HorizontalButtonGradient
import com.example.myapplication.utils.Constants.languageListItems
import com.example.myapplication.utils.Constants.updateLocale
import com.example.myapplication.utils.Navigation
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenView(onBackPressed: () -> Unit, navController: NavHostController) {
    val context = LocalContext.current

    val loginViewModel = hiltViewModel<LoginScreenViewModel>()
    val viewModel = hiltViewModel<SettingsScreenViewModel>()
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getCurrentTheme()
        viewModel.getFingerprintStatus()
        viewModel.getLanguagePref()
    }

    val darkModeState = viewModel.isDarkThemeEnabled.collectAsState()
    val languagePref = viewModel.languagePref.collectAsState()
    val fingerPrintState = viewModel.isFingerprintEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
    ) {
        selectedIndex = languagePref.value
        ToolbarView(onBackPressed, stringResource(R.string.settings), false)

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            val (darkModeTitle, darkModeIcon, deposit,
                credit, languageChange, map, viewPdf,
                biometricTitle, biometricIcon, logout,videoView) = createRefs()

            Text(
                text = stringResource(R.string.dark_mode),
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.constrainAs(darkModeTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )

            Switch(
                checked = darkModeState.value,
                onCheckedChange = {
                    viewModel.toggleTheme(it)
                },
                modifier = Modifier
                    .size(SwitchDefaults.IconSize)
                    .constrainAs(darkModeIcon) {
                        top.linkTo(darkModeTitle.top)
                        bottom.linkTo(darkModeTitle.bottom)
                        end.linkTo(parent.end, margin = 20.dp)
                    }
            )


            Text(
                text = stringResource(R.string.biometric),
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.constrainAs(biometricTitle) {
                    top.linkTo(darkModeTitle.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                }
            )

            Switch(
                checked = fingerPrintState.value,
                onCheckedChange = {
                    viewModel.toggleFingerprint(it)
                },
                modifier = Modifier
                    .size(SwitchDefaults.IconSize)
                    .constrainAs(biometricIcon) {
                        top.linkTo(biometricTitle.top)
                        bottom.linkTo(biometricTitle.bottom)
                        end.linkTo(parent.end, margin = 20.dp)
                    }
            )


            Text(
                text = stringResource(R.string.deposit),
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Navigation.Deposit.route)
                    }
                    .constrainAs(deposit) {
                        top.linkTo(biometricIcon.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                    }
            )

            Text(
                text = stringResource(R.string.deposit),
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Navigation.Deposit.route)
                    }
                    .constrainAs(deposit) {
                        top.linkTo(biometricIcon.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                    }
            )

            Text(
                text = stringResource(R.string.credit),
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Navigation.Credit.route)
                    }
                    .constrainAs(credit) {
                        top.linkTo(deposit.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                    }
            )

            Text(
                text = stringResource(R.string.change_language),
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .clickable {
                        showBottomSheet = true
                    }
                    .constrainAs(languageChange) {
                        top.linkTo(credit.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                    }
            )

            Text(
                text = stringResource(R.string.maps),
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Navigation.Maps.route)
                    }
                    .constrainAs(map) {
                        top.linkTo(languageChange.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                    }
            )

            Text(
                text = stringResource(R.string.view_pdf),
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Navigation.ViewPdf.route)
                    }
                    .constrainAs(viewPdf) {
                        top.linkTo(map.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                    }
            )

            Text(
                text = stringResource(R.string.view_video),
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Navigation.VideoView.route)
                    }
                    .constrainAs(videoView) {
                        top.linkTo(viewPdf.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                    }
            )

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
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        val (languageList, btnSubmit) = createRefs()

                        LazyColumn(modifier = Modifier.constrainAs(languageList) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            width = Dimension.fillToConstraints
                        }) {
                            itemsIndexed(items = languageListItems) { index, item ->
                                // Row for each item with a RadioButton and text
                                CustomRadioButton(
                                    text = item.first,
                                    isSelected = selectedIndex == index,
                                    onSelect = { selectedIndex = index }
                                )
                            }
                        }

                        Button(
                            modifier = Modifier.constrainAs(btnSubmit) {
                                end.linkTo(parent.end)
                                top.linkTo(languageList.bottom, margin = 10.dp)
                            },
                            onClick = {
                                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        viewModel.setLanguagePref(selectedIndex)
                                        showBottomSheet = false
                                        updateLocale(
                                            context,
                                            languageListItems[selectedIndex].second
                                        )
                                        (context as Activity).recreate()
                                    }
                                }
                            }) {
                            Text(stringResource(R.string.confirm))
                        }
                    }
                }
            }



            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(90.dp)
                    .padding(10.dp)
                    .background(HorizontalButtonGradient, shape = RoundedCornerShape(10))
                    .clickable {
                        showDialog.value = true
                    }
                    .padding(horizontal = 80.dp, vertical = 10.dp)
                    .constrainAs(logout) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 20.dp)
                    }
            ) {

                Text(
                    text = stringResource(R.string.logout),
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                )

            }

            if (showDialog.value) {
                AlertDialogComponent(
                    stringResource(R.string.logout), stringResource(R.string.logout_msg),
                    onClick = {
                        loginViewModel.logout()
                    },
                    onDismissClick = {
                        showDialog.value = false
                    })
            }
        }
    }
}