package com.thorkane.playground.login

import kotlinx.coroutines.flow.StateFlow

interface LoginManager {
    val isLoggedIn: StateFlow<LoggedInState>

    suspend fun login()

    suspend fun logout()

    sealed interface LoggedInState {
        data object LoggedOut: LoggedInState

        data class LoggedIn(
            private val user: User
        ): LoggedInState
    }
}