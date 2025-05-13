package com.example.myapplication.data.model


import com.example.myapplication.R
import com.example.myapplication.utils.Navigation


data class BottomNavigationItem(
    val label: String = "",
    val icon: Int = R.drawable.ic_card,
    val route: String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Card",
                icon = R.drawable.ic_card,
                route = Navigation.Card.route
            ),
            BottomNavigationItem(
                label = "Deposit",
                icon = R.drawable.ic_deposit,
                route = Navigation.DepositList.route
            ) ,
            BottomNavigationItem(
                label = "Graph",
                icon = R.drawable.ic_graph,
                route = Navigation.Graph.route
            ) ,
            BottomNavigationItem(
                label = "Profile",
                icon = R.drawable.ic_person,
                route = Navigation.Profile.route
            ) ,
            BottomNavigationItem(
                label = "Settings",
                icon = R.drawable.ic_settings,
                route = Navigation.Settings.route
            )
        )
    }
}