package com.iessanalberto.dam2.loginv1.navigation

sealed class AppScreens(val route: String){
    object LoginScreen: AppScreens (route = "login_screen")
    object MenuScreen: AppScreens (route = "menu_screen")
}

