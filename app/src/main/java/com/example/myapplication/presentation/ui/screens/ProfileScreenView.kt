package com.example.myapplication.presentation.ui.screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.presentation.ui.component.ToolbarView
import com.example.myapplication.presentation.viewmodel.ProfileScreenViewModel
import com.example.myapplication.theme.Purple
import com.example.myapplication.theme.Purple80


@Composable
fun ProfileScreenView(onBackPressed: () -> Unit) {
    val viewModel = hiltViewModel<ProfileScreenViewModel>()
    var tabIndex by remember { mutableStateOf(0) }

    val isLoading by viewModel.isLoading.collectAsState()
    val profileDetails by viewModel.profileDetail.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProfileDetails()
    }

    Column {
        ToolbarView(onBackPressed, stringResource(R.string.profile),false)


        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        profileDetails?.let { profileDetails ->

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onBackground)
            ) {


                val (userImage, userName, headingTotalBalance, totalBalance, tabView, progress, percentage, incomeHeading, incomeValue, expenseHeading, expenseValue, divider) = createRefs()



                Image(
                    painter = painterResource(R.drawable.ic_user),
                    contentDescription = "user",
                    modifier = Modifier
                        .size(100.dp)
                        .constrainAs(userImage) {
                            top.linkTo(parent.top, margin = 16.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                        }
                )

                Text(
                    text = profileDetails.name,
                    color = Purple,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(userName) {
                            top.linkTo(userImage.top, margin = 8.dp)
                            start.linkTo(userImage.end, margin = 16.dp)
                        }
                )

                Text(
                    text = "Total Balance",
                    color = Purple,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .constrainAs(headingTotalBalance) {
                            top.linkTo(userName.bottom, margin = 10.dp)
                            start.linkTo(userName.start)
                        }
                )

                Text(
                    text = profileDetails.totalBalance,
                    color = Purple,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(totalBalance) {
                            top.linkTo(headingTotalBalance.bottom, margin = 10.dp)
                            start.linkTo(userName.start)
                        }
                )



                TabRow(selectedTabIndex = tabIndex,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tabView) {
                            top.linkTo(totalBalance.bottom, margin = 40.dp)
                            start.linkTo(parent.start)
                        }
                ) {
                    profileDetails.tabs.forEachIndexed { index, data ->
                        Tab(modifier = Modifier.background(Color.White),
                            text = { Text(data.heading, fontWeight = FontWeight.Bold) },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index }
                        )
                    }
                }



                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                        .requiredHeight(230f.dp)
                        .constrainAs(progress) {
                            top.linkTo(tabView.bottom, margin = 40.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {

                    val sweepAngle =
                        profileDetails.tabs[tabIndex].progress.toFloat() * 360 / 100  // Calculate sweep angle from percentage

                    inset(size.width / 2 - 230f, size.height / 2 - 230f) {
                        drawCircle(
                            color = Purple80,
                            radius = 230f,
                            center = center,
                            style = Stroke(width = 40f, cap = StrokeCap.Round)
                        )

                        drawArc(
                            startAngle = 270f,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            color = Purple,
                            style = Stroke(width = 40f, cap = StrokeCap.Round)
                        )
                    }
                }


                Text(
                    text = profileDetails.tabs[tabIndex].progress + "%",
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


                Text(
                    text = "Income",
                    color = Purple,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(incomeHeading) {
                            top.linkTo(progress.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(expenseHeading.start)
                        }
                )
                Text(
                    text = profileDetails.tabs[tabIndex].Income,
                    color = Purple,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(incomeValue) {
                            top.linkTo(incomeHeading.bottom, margin = 4.dp)
                            start.linkTo(incomeHeading.start)
                        }
                )


                Text(
                    text = "Expense",
                    color = Purple,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(expenseHeading) {
                            top.linkTo(incomeHeading.top)
                            bottom.linkTo(incomeHeading.bottom)
                            start.linkTo(incomeHeading.end)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    text = profileDetails.tabs[tabIndex].Expense,
                    color = Purple,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(expenseValue) {
                            top.linkTo(expenseHeading.bottom, margin = 4.dp)
                            start.linkTo(expenseHeading.start)
                        }
                )

                HorizontalDivider(color = Color.Cyan, thickness = 1.5.dp,
                    modifier = Modifier
                        .constrainAs(divider) {
                            top.linkTo(incomeValue.bottom, margin = 10.dp)
                            start.linkTo(incomeValue.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                )


            }
        }
    }
}

