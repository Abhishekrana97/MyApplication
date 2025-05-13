package com.example.myapplication.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.presentation.ui.component.ToolbarView
import com.example.myapplication.presentation.viewmodel.ExpenseScreenViewModel
import com.example.myapplication.theme.Black
import com.example.myapplication.theme.Purple
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


@Composable
fun ExpenseGraphScreen(onBackPressed: () -> Unit) {
    val viewModel = hiltViewModel<ExpenseScreenViewModel>()
    var tabIndex by remember { mutableStateOf(0) }

    val isLoading by viewModel.isLoading.collectAsState()
    val expenseDetails by viewModel.expenseDetails.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getExpenseDetails()
    }

    Column {
        ToolbarView(onBackPressed, stringResource(R.string.expenses))


        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        expenseDetails?.let { expenseDetails ->

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onBackground)
            ) {


                val (userImage, userName, headingTotalBalance, totalBalance, tabView, graphView, totalCredit, totalDebit) = createRefs()



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
                    text = expenseDetails.name,
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
                    text = expenseDetails.totalBalance,
                    color = Purple,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(totalBalance) {
                            top.linkTo(headingTotalBalance.bottom, margin = 10.dp)
                            start.linkTo(userName.start)
                        }
                )


                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .constrainAs(graphView) {
                            top.linkTo(totalBalance.bottom, margin = 40.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    factory = { context ->
                        LineChart(context).apply {
                            setTouchEnabled(false)
                            isDragEnabled = false
                            isScaleXEnabled = false
                            isScaleYEnabled = false
                            description.isEnabled = false
                            legend.isEnabled = false

                            axisLeft.setDrawGridLines(false)
                            xAxis.setDrawGridLines(false)
                            axisLeft.textColor = Black.toArgb()
                            axisRight.isEnabled = false
                            xAxis.textColor = DarkGray.toArgb()
                            xAxis.position = XAxis.XAxisPosition.BOTTOM
                        }
                    },
                    update = { chart ->

                        val entries: List<Entry> =
                            expenseDetails.xAxis.zip(expenseDetails.tabs[tabIndex].yAxis) { x, y ->
                                Entry(x, y)
                            }

                        val dataSet = LineDataSet(entries, "graph").apply {
                            color = Color.Red.toArgb()
                            setDrawValues(false)
//                            setDrawCircles(false)
                            setDrawFilled(true)
                            fillColor = Color.Yellow.toArgb()
                            fillAlpha = 255

                            lineWidth = 2f
                            setCircleColor(Color.Red.toArgb())
                            circleRadius = 4f
                            setDrawCircleHole(false)
                            valueTextColor = Color.Black.toArgb()
                            valueTextSize = 1f
                        }

                        chart.data = LineData(dataSet)
                        chart.animateX(500)
                        chart.animateY(1200)
                        chart.invalidate()  // Refresh the chart to display new data
                    }
                )



                TabRow(selectedTabIndex = tabIndex,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tabView) {
                            top.linkTo(graphView.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                        }
                ) {
                    expenseDetails.tabs.forEachIndexed { index, data ->
                        Tab(modifier = Modifier.background(Color.White),
                            text = { Text(data.heading, fontWeight = FontWeight.Bold) },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index }
                        )
                    }
                }


                Column(modifier = Modifier
                    .constrainAs(totalCredit) {
                        top.linkTo(tabView.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(totalDebit.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
                ) {
                    Text(
                        text = "+",
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Total Income",
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Text(
                        text = "$ " + expenseDetails.tabs[tabIndex].totalCredit,
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                }

                Column(modifier = Modifier
                    .constrainAs(totalDebit) {
                        top.linkTo(totalCredit.top)
                        bottom.linkTo(totalCredit.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(totalCredit.end)
                    }
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)) {

                    Text(
                        text = "-",
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Total Expense",
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Text(
                        text = "$ " + expenseDetails.tabs[tabIndex].totalDebit,
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                }
            }
        }
    }
}