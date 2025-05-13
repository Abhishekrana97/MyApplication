package com.example.myapplication.utils

sealed class Navigation(val route : String) {
    data object Card : Navigation("card_screen")
    data object Profile : Navigation("profile_screen")
    data object Graph : Navigation("graph_screen")
    data object DepositList : Navigation("deposit_list_screen")
    data object Credit : Navigation("credit_screen")
    data object Settings : Navigation("settings_screen")
    data object AddCard : Navigation("add_card_screen")
    data object EditCard : Navigation("edit_card_screen")
    data object OnBoarding : Navigation("onboarding_screen")
    data object Login : Navigation("login_screen")
    data object Deposit : Navigation("deposit_screen")
    data object Expenses : Navigation("expense_detail_screen")
    data object Maps : Navigation("map_screen")
    data object ViewPdf : Navigation("view_pdf")
    data object VideoView : Navigation("video_view")
}