package com.example.myapplication.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.presentation.ui.component.ToolbarView
import com.example.myapplication.presentation.viewmodel.DepositListViewModel

@Composable
fun DepositListScreen(onBackPressed: () -> Unit) {

    val viewModel = hiltViewModel<DepositListViewModel>()

    LaunchedEffect(Unit) {
        viewModel.getDepositMoney()
    }

    val cards by viewModel.depositMoney.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
    ) {
        ToolbarView(onBackPressed, stringResource(R.string.deposit_list),false)

        cards?.let {
            LazyColumn {
                itemsIndexed(items = it.reversed()) { index, item ->
                    DepositScreenItem(item)
                }
            }
        }

    }
}