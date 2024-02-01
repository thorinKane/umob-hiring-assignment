package com.thorkane.playground.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thorkane.playground.login.presenter.LoginModel

@Composable
fun LoginScreen(model: LoginModel.LoggedOut) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (model.isLoading) {
            CircularProgressIndicator()
        } else {
            Text("Login Screen", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = model.login) {
                Text("Login", fontSize = 18.sp)
            }
        }
    }
}
