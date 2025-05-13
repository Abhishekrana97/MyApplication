package com.example.myapplication.presentation.ui.screens


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myapplication.data.model.DepositMoney
import com.example.myapplication.theme.HorizontalButtonGradient


@Composable
fun DepositScreenItem(data: DepositMoney) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 2.dp,
                brush = HorizontalButtonGradient,
                shape = RoundedCornerShape(10)
            )
            .padding(16.dp)
    ) {
        val (tvMoney, tvDate) = createRefs()

        Text(
            text = data.money,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(tvMoney) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )


        Text(
            text = "Deposit on : " + data.date,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.constrainAs(tvDate) {
                start.linkTo(parent.start)
                top.linkTo(tvMoney.bottom, margin = 10.dp)
            }
        )
    }
}