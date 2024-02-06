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
import com.thorkane.playground.navigation.impl.LoggedInNavigationPresenter
import com.thorkane.playground.presenter.Event
import com.thorkane.playground.presenter.Model
import com.thorkane.playground.presenter.ModelDelegate
import com.thorkane.playground.presenter.Presenter
import com.thorkane.playground.presenter.onEvent
import javax.inject.Inject

sealed interface LoginEvent: Event {
    data object LogIn: LoginEvent
    data object LogOut: LoginEvent
}

sealed class LoginModel: Model {
    data class LoggedOut(
        val isLoading: Boolean,
        val onEvent: (Event) -> Unit,
    ): LoginModel()

    class LoggedIn(
        val user: User,
        val onEvent: (Event) -> Unit,
        private val child: Model,
    ) : LoginModel(), ModelDelegate {
        override fun delegate(): Model {
            return child
        }
    }
}

class LoginPresenter @Inject constructor(
    private val loginManager: LoginManager,
    private val loggedInNavigationPresenter: LoggedInNavigationPresenter
) : Presenter<LoginModel> {
    @Composable
    override fun present(): LoginModel {
        val loggedInState by loginManager.isLoggedIn.collectAsState()
        var isLoading by remember { mutableStateOf(false) }

        if(isLoading) {
            LaunchedEffect(Unit) {
                isLoading = when (loggedInState) {
                    is LoginManager.LoggedInState.LoggedOut -> {
                        loginManager.login()
                        false
                    }

                    is LoginManager.LoggedInState.LoggedIn -> {
                        loginManager.logout()
                        false
                    }
                }
            }
        }

        return when (loggedInState) {
            is LoginManager.LoggedInState.LoggedIn -> {
                LoginModel.LoggedIn(
                    // Not sure why I needed to cast this. Take a look later.
                    user = (loggedInState as LoginManager.LoggedInState.LoggedIn).user,
                    onEvent = onEvent {
                        when (it) {
                            is LoginEvent.LogOut -> {
                                isLoading = true
                            }
                        }
                    },
                    loggedInNavigationPresenter.present()
                )
            }

            is LoginManager.LoggedInState.LoggedOut -> {
                LoginModel.LoggedOut(
                    isLoading = isLoading,
                    onEvent = onEvent {
                        when (it) {
                            is LoginEvent.LogIn -> {
                                isLoading = true
                            }
                        }
                    }
                )
            }
        }
    }
}