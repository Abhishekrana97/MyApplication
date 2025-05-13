package com.example.myapplication.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.presentation.ui.component.ToolbarView
import com.example.myapplication.presentation.viewmodel.GraphScreenViewModel
import com.example.myapplication.theme.Black
import com.example.myapplication.theme.Purple
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


@Composable
fun GraphScreenView(onBackPressed: () -> Unit) {
    val viewModel = hiltViewModel<GraphScreenViewModel>()
    var tabIndex by remember { mutableStateOf(0) }

    val isLoading by viewModel.isLoading.collectAsState()
    val graphDetails by viewModel.graphDetail.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getGraphDetails()
    }



    Column {
        ToolbarView(onBackPressed, stringResource(R.string.graph),false)

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground)
        ) {


            val (totalBalance, graph, tabView) = createRefs()
            graphDetails?.let { graphDetails ->
                Text(
                    text = graphDetails.totalBalance,
                    color = Color.Black,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(totalBalance) {
                            top.linkTo(parent.top, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .constrainAs(graph) {
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

//                            axisLeft.setDrawGridLines(false)
                            xAxis.setDrawGridLines(false)
                            axisLeft.textColor = Black.toArgb()
                            axisRight.isEnabled = false
                            xAxis.textColor = DarkGray.toArgb()
                            xAxis.position = XAxis.XAxisPosition.BOTTOM
                        }
                    },
                    update = { chart ->
                        val entries: List<Entry> =
                            graphDetails.xAxis.zip(graphDetails.tabs[tabIndex].yAxis) { x, y ->
                                Entry(x, y)
                            }

                        val dataSet = LineDataSet(entries, "graph").apply {
                            color = Color.Red.toArgb()
                            setDrawValues(false)
//                            setDrawCircles(false)
                            setDrawFilled(true)
                            fillColor = Color.Cyan.toArgb()
                            fillAlpha = 255

                            lineWidth = 2f
                            setCircleColor(Color.Magenta.toArgb())
                            circleRadius = 4f
                            setDrawCircleHole(false)
                            valueTextColor = Color.Black.toArgb()
                            valueTextSize = 1f
                        }

                        chart.data = LineData(dataSet)
                        chart.animateX(1200)
                        chart.animateY(1200)
                        chart.invalidate()  // Refresh the chart to display new data
                    }
                )

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(tabView) {
                        top.linkTo(graph.bottom, margin = 40.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }

                ) {


                    TabRow(
                        selectedTabIndex = tabIndex,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(White),
                        indicator = { }
                    ) {
                        graphDetails.tabs.forEachIndexed { index, data ->
                            Tab(
                                selected = tabIndex == index,
                                onClick = { tabIndex = index },
                                text = {
                                    Text(
                                        text = data.heading,
                                        color = if (tabIndex == index) White else Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                },
                                modifier = Modifier
                                    .background(White)
                                    .padding(8.dp) // Padding for the tab
                                    .size(50.dp) // Make the tab a fixed circular size
                                    .background(
                                        color = if (tabIndex == index) Purple else Color.Transparent,
                                        shape = CircleShape // Use CircleShape for the circular tab
                                    )
                                    .padding(8.dp) // Inner padding for the text
                            )
                        }
                    }
                }
            }
        }
    }


//    fun generateLineData(): LineData {
//        val transactionData = listOf(
//            Pair(1f, 1200f),
//            Pair(2f, 1400f),
//            Pair(3f, 800f),
//            Pair(4f, 1700f),
//            Pair(5f, 2000f),
//            Pair(6f, 1300f),
//            Pair(7f, 1500f),
//            Pair(8f, 900f),
//            Pair(9f, 1100f),
//            Pair(10f, 1800f),
//            Pair(11f, 1600f),
//            Pair(12f, 1200f),
//            Pair(13f, 2100f),
//            Pair(14f, 1900f),
//            Pair(15f, 1000f),
//            Pair(16f, 2200f),
//            Pair(17f, 2400f),
//            Pair(18f, 1300f),
//            Pair(19f, 1800f),
//            Pair(20f, 2500f)
//        )
//        val entries = transactionData.map { (x, y) ->
//            Entry(
//                x,
//                y
//            )
//        }
//
//        val dataSet = LineDataSet(entries, "Transactions").apply {
//            color = Color.Cyan.toArgb()
//            lineWidth = 2f
//            setCircleColor(Color.Magenta.toArgb())
//            circleRadius = 4f
//            setDrawCircleHole(false)
//            valueTextColor = Color.Black.toArgb()
//            valueTextSize = 1f
//        }
//        return LineData(dataSet)
//    }
}
