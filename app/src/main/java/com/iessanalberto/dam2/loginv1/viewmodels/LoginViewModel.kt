package com.iessanalberto.dam2.loginv1.viewmodels

import androidx.lifecycle.ViewModel
import com.iessanalberto.dam2.loginv1.states.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {
    private  val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> =_loginState.asStateFlow()
    // Funciones para actualizar el estado
    fun onUsuarioChanged(usuario: String){
        _loginState.update { currentState ->
            currentState.copy(usuario = usuario)
        }
    }
    fun onPasswordChanged(password: String){
        _loginState.update { it.copy(password = password) }
    }

    // Funciones de "negocio"
    fun conectar(): Boolean{
        return (_loginState.value.usuario == "Admin" && _loginState.value.password == "Admin")
    }
}