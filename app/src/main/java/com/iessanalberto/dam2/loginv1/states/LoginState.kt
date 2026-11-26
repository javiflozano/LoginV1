package com.iessanalberto.dam2.loginv1.states

data class LoginState(
    val usuario: String = "",
    val password: String = "",
    val numIntentos: Int = 0,
    val isLogged: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
