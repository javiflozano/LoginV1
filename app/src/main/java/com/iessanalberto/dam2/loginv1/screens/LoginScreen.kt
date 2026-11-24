package com.iessanalberto.dam2.loginv1.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iessanalberto.dam2.loginv1.navigation.AppScreens
import com.iessanalberto.dam2.loginv1.viewmodels.LoginViewModel

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    Scaffold() {innerpadding ->
        LoginBodyContent(modifier = Modifier.padding(paddingValues = innerpadding),
            onLoginSuccess = onLoginSuccess,
            )
    }
}

@Composable
fun LoginBodyContent(modifier: Modifier, onLoginSuccess: () -> Unit) {

    val context = LocalContext.current

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
                onLoginSuccess()  // Navega a la ventana de menú (MenuScreen)
            } else {
                Toast.makeText(context,"Usuario/Contraseña incorrecto Intento: ${loginState.numIntentos}", Toast.LENGTH_SHORT).show()
                if (loginState.numIntentos >= 2){
                    Toast.makeText(context,"Has superado el número de intentos", Toast.LENGTH_SHORT).show()
                    System.exit(10)
                }
            }
        }) {
            Text(text = "Conectar")
        }
    }
}