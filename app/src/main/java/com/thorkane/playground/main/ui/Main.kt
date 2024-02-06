package com.thorkane.playground.main.ui

import androidx.compose.runtime.Composable
import com.thorkane.playground.game.presenter.GameModel
import com.thorkane.playground.game.ui.GameScreen
import com.thorkane.playground.history.presenter.HistoryPresenter.HistoryModel
import com.thorkane.playground.history.ui.HistoryScreen
import com.thorkane.playground.home.HomePresenter.HomeModel
import com.thorkane.playground.home.ui.HomeScreen
import com.thorkane.playground.login.presenter.LoginModel
import com.thorkane.playground.login.ui.LoginScreen
import com.thorkane.playground.presenter.Model

@Composable
fun Main(model: Model) {
    when(model) {
        is LoginModel.LoggedIn -> {
            return when(val child = model.delegate()) {
                is HomeModel -> HomeScreen(model = child)
                is GameModel -> GameScreen(model = child)
                is HistoryModel -> HistoryScreen(model = child)
                else -> throw Error("Screen Not yet implemented for model: $child")
            }
        }
        is LoginModel.LoggedOut -> LoginScreen(model = model)
        else -> throw Error("Screen Not yet implemented for model: $model")
    }
}