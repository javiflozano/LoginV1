package com.iessanalberto.dam2.loginv1.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


import com.iessanalberto.dam2.loginv1.states.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private lateinit var auth: FirebaseAuth

    init {
        auth = Firebase.auth
    }


    // Funciones para actualizar el estado
    fun onUsuarioChanged(usuario: String) {
        _loginState.update { currentState ->
            currentState.copy(usuario = usuario)
        }
    }

    fun onPasswordChanged(password: String) {
        _loginState.update { it.copy(password = password) }
    }
    fun clearErrorMessage(){
        _loginState.update { it.copy(errorMessage = "") }
    }

    // Funciones de "negocio"
    fun conectar() {
        // Actualizar el estado a isLoading
        _loginState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val currentState = _loginState.value
            auth.signInWithEmailAndPassword(
                currentState.usuario, currentState.password
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _loginState.update {
                            it.copy(
                                isLogged = true,
                                isLoading = false
                            )
                        }
                    } else {
                        _loginState.update {
                            it.copy(
                                isLogged = false,
                                numIntentos = it.numIntentos.inc(),
                                isLoading = false,
                                errorMessage = "Error usuario/contraseña incorrectos"
                            )
                        }
                    }
                }
        }


    }
    fun otraConexion(){
        _loginState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val currentState = _loginState.value
            try {
                auth.signInWithEmailAndPassword(currentState.usuario, currentState.password)
                    .await()
                _loginState.update { it.copy(
                    isLogged = true,
                    isLoading = false
                ) }
            } catch (excepcion: FirebaseAuthInvalidCredentialsException){
                _loginState.update { it.copy(
                    isLoading = false,
                    errorMessage = "Usuario/contraseña mal escrito",
                    numIntentos = currentState.numIntentos.inc()
                ) }
            }
            catch (excepcion: FirebaseException){
                _loginState.update { it.copy(
                    isLoading = false,
                    errorMessage = "Usuario/contraseña incorrectos",
                    numIntentos = currentState.numIntentos.inc()
                ) }
            }
            catch (excepcion: Exception){
                _loginState.update { it.copy(
                    isLoading = false,
                    errorMessage = "Algo fue mal"
                ) }
            }
        }
    }
}