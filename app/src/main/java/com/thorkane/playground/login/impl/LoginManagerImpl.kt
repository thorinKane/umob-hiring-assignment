package com.thorkane.playground.login.impl

import android.app.Application
import android.content.Context
import com.thorkane.playground.login.LoginManager
import com.thorkane.playground.login.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random

class LoginManagerImpl @Inject constructor(
    context: Application,
): LoginManager {
    private companion object {
        const val USER_KEY = "user-token"
    }

    private val _isLoggedIn: MutableStateFlow<LoginManager.LoggedInState>
    private val prefs = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    init {
        val userToken = prefs.getString(USER_KEY, null)
        val loggedInState = if (userToken != null) {
            LoginManager.LoggedInState.LoggedIn(User(userToken))
        } else {
            LoginManager.LoggedInState.LoggedOut
        }

        _isLoggedIn = MutableStateFlow(loggedInState)
    }

    override val isLoggedIn: StateFlow<LoginManager.LoggedInState> = _isLoggedIn.asStateFlow()

    override suspend fun logout() {
        when (isLoggedIn.value) {
            is LoginManager.LoggedInState.LoggedIn -> {
                check(prefs.edit().remove(USER_KEY).commit()) {
                    "Could not delete user token."
                }
                _isLoggedIn.value = LoginManager.LoggedInState.LoggedOut
            }
            is LoginManager.LoggedInState.LoggedOut -> Unit
        }
    }

    override suspend fun login() {
        when (isLoggedIn.value) {
            is LoginManager.LoggedInState.LoggedIn -> Unit
            is LoginManager.LoggedInState.LoggedOut -> {
                delay(2000)
                // Generate a random token. In reality this would be the result of some authentication request.
                val user = User(userToken = Random.nextInt(10_000).toString())
//                check(prefs.edit().putString(USER_KEY, user.userToken).commit()) {
//                    "Could not persist user token."
//                }

                _isLoggedIn.value = LoginManager.LoggedInState.LoggedIn(user)
            }
        }
    }
}