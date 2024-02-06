package com.thorkane.playground.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thorkane.playground.data.Choice
import com.thorkane.playground.game.presenter.GameEvent
import com.thorkane.playground.game.presenter.GameModel
import com.thorkane.playground.ui.components.RadioButtonList

@Composable
fun GameScreen(model: GameModel) {
    var selectedOption by remember {
        mutableStateOf<Choice?>(null)
    }

    when (model) {
        is GameModel.Started -> {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Game Screen", fontSize = 32.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text("${model.time}", fontSize = 32.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(model.question.prompt, fontSize = 24.sp)
                RadioButtonList(
                    items = model.question.choices,
                    selectedOption = selectedOption
                ) {
                    selectedOption = it
                }
                Button(onClick = {
                    selectedOption?.let { model.onEvent(GameEvent.Next(it)) }
                }) {
                    Text("Next", fontSize = 18.sp) // TODO update text when we are on last question.
                }
                Button(onClick = { model.onEvent(GameEvent.Quit) }) {
                    Text("Quit", fontSize = 16.sp)
                }
            }
        }

        is GameModel.Finished -> {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Finished", fontSize = 32.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Score: ${model.score}", fontSize = 32.sp)
                Button(onClick = { model.onEvent(GameEvent.ViewHistory) }) {
                    Text("History", fontSize = 18.sp)
                }
                Button(onClick = { model.onEvent(GameEvent.Quit) }) {
                    Text("Quit", fontSize = 16.sp)
                }
            }
        }
    }

}