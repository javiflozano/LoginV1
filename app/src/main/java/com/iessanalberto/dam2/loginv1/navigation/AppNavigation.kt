package com.iessanalberto.dam2.loginv1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iessanalberto.dam2.loginv1.screens.LoginScreen
import com.iessanalberto.dam2.loginv1.screens.MenuScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route){
        composable(route = AppScreens.LoginScreen.route) { LoginScreen(navController = navController) }
        composable(route = AppScreens.MenuScreen.route){ MenuScreen(navController = navController) }
    }
}

