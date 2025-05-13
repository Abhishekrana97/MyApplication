package com.example.myapplication.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.presentation.ui.component.ToolbarView
import com.example.myapplication.presentation.viewmodel.CardScreenViewModel
import com.example.myapplication.utils.Navigation


@Composable
fun CardScreenView(onBackPressed: () -> Unit, navController: NavHostController) {
    val viewModel = hiltViewModel<CardScreenViewModel>()
    LaunchedEffect(Unit) {
        viewModel.getCardsFromDB()
    }
    val cards by viewModel.cards.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onBackground)) {

        Column {
            ToolbarView(onBackPressed, stringResource(R.string.cards),false)

            cards?.let {
                LazyColumn {
                    itemsIndexed(items = it) { _, item ->
                        CardViewItem(item, navController)
                    }
                }
            }

        }


        FloatingActionButton(
            onClick = {
                navController.navigate(Navigation.AddCard.route)
            },
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Card",
                tint = MaterialTheme.colorScheme.background
            )
        }

    }
}





