package com.thorkane.playground.login.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.thorkane.playground.login.LoginManager
import com.thorkane.playground.login.User
import com.thorkane.playground.presenter.Event
import kotlinx.coroutines.flow.Flow
sealed interface LoginEvent: Event {
    data object LogIn: LoginEvent
    data object LogOut: LoginEvent
}

sealed class LoginModel {
    data class LoggedOut(
        val isLoading: Boolean,
        val login: () -> Unit
    ): LoginModel()

    data class LoggedIn(
        val user: User,
        val login: () -> Unit
    ) : LoginModel()

}

/**
 * The presenter responsible for translating user logged in state into models for consumption
 * by the UI. This presenter is the defacto root presenter of our application.
 */
@Composable
fun loginPresenter(
    events: Flow<Event>,
    loginManager: LoginManager,
    take: (event: LoginEvent) -> Unit
): LoginModel {
    val loggedInState by loginManager.isLoggedIn.collectAsState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        events.collect {
            when (it) {
                is LoginEvent.LogIn -> {
                    isLoading = true
                    loginManager.login()
                }

                is LoginEvent.LogOut -> {
                    loginManager.logout()
                }
            }
        }
    }

    return when(loggedInState) {
        is LoginManager.LoggedInState.LoggedIn -> {
            isLoading = false
            LoginModel.LoggedIn(
                // Not sure why I needed to cast this. Take a look later.
                (loggedInState as LoginManager.LoggedInState.LoggedIn).user
            ) {
                take(LoginEvent.LogOut)
            }
        }
        is LoginManager.LoggedInState.LoggedOut -> {
            LoginModel.LoggedOut(isLoading) {
                take(LoginEvent.LogIn)
            }
        }
    }
}