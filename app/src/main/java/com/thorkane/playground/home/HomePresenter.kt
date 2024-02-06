package com.thorkane.playground.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.thorkane.playground.login.LoginManager
import com.thorkane.playground.navigation.Destinations
import com.thorkane.playground.navigation.LoggedInNavigator
import com.thorkane.playground.presenter.Event
import com.thorkane.playground.presenter.Model
import com.thorkane.playground.presenter.Presenter
import com.thorkane.playground.presenter.onEvent
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val loginManager: LoginManager,
    private val loggedInNavigator: LoggedInNavigator,
) : Presenter<HomePresenter.HomeModel> {
    @Composable
    override fun present(): HomeModel {
        var isLoading by remember { mutableStateOf(false) }

        if(isLoading) {
            LaunchedEffect(Unit) {
                loginManager.logout()
                isLoading = false
            }
        }

        return HomeModel(
            onEvent = onEvent {
                when(it) {
                    is HomeEvent.Logout -> isLoading = true
                    is HomeEvent.StartGame -> loggedInNavigator.goTo(
                        Destinations.GAME,
                        mode = LoggedInNavigator.Mode.PUSH
                    )
                }
            }
        )
    }

    data class HomeModel(
        val onEvent: (Event) -> Unit,
    ): Model

    sealed interface HomeEvent: Event {
        data object Logout: HomeEvent

        data object StartGame: HomeEvent
    }
}