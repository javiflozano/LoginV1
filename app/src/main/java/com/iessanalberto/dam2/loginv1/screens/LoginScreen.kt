package com.iessanalberto.dam2.loginv1.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iessanalberto.dam2.loginv1.navigation.AppScreens
import com.iessanalberto.dam2.loginv1.viewmodels.LoginViewModel

@Composable
fun LoginScreen(navController: NavController) {
    Scaffold() {innerpadding ->
        LoginBodyContent(modifier = Modifier.padding(paddingValues = innerpadding),
            navController = navController)
    }
}

@Composable
fun LoginBodyContent(modifier: Modifier, navController: NavController) {

    val loginViewModel: LoginViewModel = viewModel()
    val loginState by loginViewModel.loginState.collectAsState()

    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        OutlinedTextField(
            value = loginState.usuario,
            label = { Text(text = "Introduce usuario") },
            onValueChange = {loginViewModel.onUsuarioChanged(usuario = it)}
        )
        OutlinedTextField(
            value = loginState.password,
            label = { Text(text = "Introduce contraseña") },
            onValueChange = {loginViewModel.onPasswordChanged(password = it)}
        )
        Button(onClick = {
            if (loginViewModel.conectar()){
                navController.navigate(AppScreens.MenuScreen.route)
            } else {
                // TODO MENSAJE DE ERROR
            }
        }) {
            Text(text = "Conectar")
        }
    }
}