package com.example.myapplication.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.theme.Black
import com.example.myapplication.theme.HorizontalButtonGradient
import com.example.myapplication.theme.White
import com.example.myapplication.utils.Navigation


@Composable
fun OnBoardingScreenView(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(50.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(250.dp)
                .background(
                    Color.White, shape = CircleShape
                )
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "Contact profile picture",
                modifier = Modifier.size(170.dp),
                alignment = Alignment.Center
            )
        }
        Spacer(modifier = Modifier.size(80.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(90.dp)
                .padding(10.dp)
                .background(HorizontalButtonGradient, shape = RoundedCornerShape(10))
                .clickable {
                    navController.navigate("${Navigation.Login.route}/${true}")

                }
                .padding(horizontal = 80.dp, vertical = 10.dp)
        ) {

            Text(
                text = stringResource(R.string.next),
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
            )

        }

        Spacer(modifier = Modifier.size(80.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.already_have_an_account),
                color = White,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(R.string.login),
                color = Color.Cyan,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.clickable {
                    navController.navigate("${Navigation.Login.route}/${false}")
                }
            )
        }
    }
}