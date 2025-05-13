package com.example.myapplication.data.model

data class ExpenseDetails(
    val name: String,
    val totalBalance: String,
    val xAxis: List<Float>,
    val tabs: ArrayList<ExpenseTabDetails>
)