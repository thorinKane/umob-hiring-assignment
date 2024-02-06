package com.thorkane.playground.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thorkane.playground.home.HomePresenter
import com.thorkane.playground.login.presenter.LoginEvent
import com.thorkane.playground.login.presenter.LoginModel

@Composable
fun HomeScreen(model: HomePresenter.HomeModel) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { model.onEvent(HomePresenter.HomeEvent.StartGame) }) {
            Text("Start Game", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { model.onEvent(HomePresenter.HomeEvent.Logout) }) {
            Text("Logout", fontSize = 18.sp)
        }
    }
}