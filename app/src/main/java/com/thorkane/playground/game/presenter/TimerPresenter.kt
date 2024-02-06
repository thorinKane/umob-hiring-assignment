package com.thorkane.playground.game.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.thorkane.playground.presenter.Model
import kotlinx.coroutines.delay

data class TimeModel(
    val time: Int
): Model

/**
 * A basic countdown timer. Emits a new state [TimeModel] once every
 * second. Counting down from 60.
 */
@Composable
fun timerPresenter(): TimeModel {
    var time by remember { mutableIntStateOf(60) }

    LaunchedEffect(key1 = time) {
        while (time > 0) {
            delay(1000L)
            time--
        }
    }

    return TimeModel(
        time
    )
}
