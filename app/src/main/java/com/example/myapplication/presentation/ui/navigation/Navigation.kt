package com.example.myapplication.presentation.ui.navigation

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.data.model.AddCards
import com.example.myapplication.data.model.BottomNavigationItem
import com.example.myapplication.presentation.ui.screens.AddCardView
import com.example.myapplication.presentation.ui.screens.CardScreenView
import com.example.myapplication.presentation.ui.screens.DepositListScreen
import com.example.myapplication.presentation.ui.screens.DepositScreen
import com.example.myapplication.presentation.ui.screens.ExchangeCurrencyScreen
import com.example.myapplication.presentation.ui.screens.ExoPlayerView
import com.example.myapplication.presentation.ui.screens.ExpenseGraphScreen
import com.example.myapplication.presentation.ui.screens.GraphScreenView
import com.example.myapplication.presentation.ui.screens.LoginScreenView
import com.example.myapplication.presentation.ui.screens.MapScreen
import com.example.myapplication.presentation.ui.screens.OnBoardingScreenView
import com.example.myapplication.presentation.ui.screens.PdfViewScreen
import com.example.myapplication.presentation.ui.screens.ProfileScreenView
import com.example.myapplication.presentation.ui.screens.SettingsScreenView
import com.example.myapplication.presentation.viewmodel.LoginScreenViewModel
import com.example.myapplication.theme.Purple
import com.example.myapplication.theme.White
import com.example.myapplication.utils.Navigation
import com.google.gson.Gson


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val viewModel = hiltViewModel<LoginScreenViewModel>()

    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    LaunchedEffect(isUserLoggedIn) {
        viewModel.getUserLoginStatus()
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
    ) {

        val (bottomNavigation, containerView) = createRefs()


        NavHost(
            navController = navController,
            startDestination = if (isUserLoggedIn) Navigation.Card.route else Navigation.OnBoarding.route,
            modifier = Modifier.constrainAs(containerView) {
                top.linkTo(parent.top)
                bottom.linkTo(if (isUserLoggedIn) bottomNavigation.top else parent.bottom)
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
            }
        ) {
            composable(Navigation.OnBoarding.route) {
                OnBoardingScreenView(navController)
            }
            composable(
                "${Navigation.Login.route}/{isNewUser}",
                arguments = listOf(navArgument("isNewUser") { type = NavType.BoolType })
            ) { backStackEntry ->
                val isNewUser = backStackEntry.arguments?.getBoolean("isNewUser")
                LoginScreenView(navController,isNewUser)
            }
            composable(Navigation.Card.route) {
                CardScreenView({
                    navController.popBackStack()
                }, navController)
            }
            composable(Navigation.DepositList.route) {
                DepositListScreen({
                    navController.popBackStack()
                })
            }
            composable(Navigation.Expenses.route) {
                ExpenseGraphScreen {
                    navController.popBackStack()
                }
            }
            composable(Navigation.Credit.route) {
                ExchangeCurrencyScreen({
                    navController.popBackStack()
                }, navController)
            }
            composable(Navigation.Graph.route) {
                GraphScreenView {
                    navController.popBackStack()
                }
            }
            composable(Navigation.Profile.route) {
                ProfileScreenView {
                    navController.popBackStack()
                }
            }
            composable(Navigation.Settings.route) {
                SettingsScreenView({
                    navController.popBackStack()
                }, navController)
            }
            composable(Navigation.Deposit.route) {
                DepositScreen {
                    navController.navigateUp()
                }
            }
            composable(Navigation.AddCard.route) {
                AddCardView({
                    navController.navigateUp()
                })
            }
            composable(Navigation.Maps.route) {
                MapScreen{
                    navController.navigateUp()
                }
            }
            composable(Navigation.ViewPdf.route) {
                PdfViewScreen()
            }
            composable(Navigation.VideoView.route) {
                ExoPlayerView()
            }
            composable(
                "${Navigation.EditCard.route}/{card}",
                arguments = listOf(navArgument("card") { type = NavType.StringType })
            ) { backStackEntry ->
                val userJson = backStackEntry.arguments?.getString("card")
                val decodedCardJson = Uri.decode(userJson)

                val card = Gson().fromJson(decodedCardJson, AddCards::class.java)
                AddCardView({
                    navController.navigateUp()
                }, card)
            }
        }

        if (isUserLoggedIn) {
            NavigationBar(containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.constrainAs(bottomNavigation) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                BottomNavigationItem().bottomNavigationItems().forEach { navigationItem ->
                    NavigationBarItem(
                        selected = navigationItem.route == currentDestination?.route,
                        icon = {
                            Icon(
                                painter = painterResource(navigationItem.icon),
                                tint = if (navigationItem.route == currentDestination?.route) Purple else White, // Change color here
                                contentDescription = navigationItem.label,
                                modifier = Modifier.size(32.dp) // Adjust the size here
                            )
                        },
                        onClick = {
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}