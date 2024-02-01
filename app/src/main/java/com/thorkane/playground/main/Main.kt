package com.thorkane.playground.main

import androidx.compose.runtime.Composable
import com.thorkane.playground.home.HomeScreen
import com.thorkane.playground.login.presenter.LoginModel
import com.thorkane.playground.login.ui.LoginScreen

@Composable
fun Main(model: LoginModel) {
    when(model) {
        is LoginModel.LoggedIn -> HomeScreen()
        is LoginModel.LoggedOut -> LoginScreen(model = model)
    }
}