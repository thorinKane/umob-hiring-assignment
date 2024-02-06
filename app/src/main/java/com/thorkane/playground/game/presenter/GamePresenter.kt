package com.thorkane.playground.game.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.thorkane.playground.data.Choice
import com.thorkane.playground.data.Question
import com.thorkane.playground.data.questions
import com.thorkane.playground.game.GameManager
import com.thorkane.playground.navigation.Destinations
import com.thorkane.playground.navigation.LoggedInNavigator
import com.thorkane.playground.presenter.Event
import com.thorkane.playground.presenter.Model
import com.thorkane.playground.presenter.Presenter
import com.thorkane.playground.presenter.onEvent
import javax.inject.Inject

sealed class GameModel: Model {
    data class Started(
        val question: Question,
        val time: Int,
        val onEvent: (GameEvent) -> Unit,
    ): GameModel()

    data class Finished(
        val score: Int,
        val onEvent: (GameEvent) -> Unit,
    ): GameModel()
}

sealed interface GameEvent: Event {
    data class Next(val choice: Choice): GameEvent

    data object Previous: GameEvent

    data object Quit: GameEvent

    data object ViewHistory: GameEvent
}

class GamePresenter @Inject constructor(
    private val loggedInNavigator: LoggedInNavigator,
    private val gameManager: GameManager,
) : Presenter<GameModel> {
    @Composable
    override fun present(): GameModel {
        val timerModel = timerPresenter()
        val questionList = remember { questions }
        var currentQuestion by remember { mutableIntStateOf(0) }
        var score by remember { mutableIntStateOf(0) }

        return if (currentQuestion == questionList.size || timerModel.time == 0) {
            GameModel.Finished(
                score = score,
                onEvent = onEvent {
                    when (it) {
                        is GameEvent.Quit -> {
                            gameManager.recordScore(score)
                            loggedInNavigator.goTo(Destinations.HOME)
                        }

                        is GameEvent.ViewHistory -> {
                            gameManager.recordScore(score)
                            loggedInNavigator.goTo(Destinations.HISTORY)
                        }

                        else -> {
                            throw Error("Not Supported.") // This shouldn't happen. Only using an else to save time.
                        }
                    }
                }
            )
        } else {
            GameModel.Started(
                questionList[currentQuestion],
                time = timerModel.time,
                onEvent = onEvent {
                    when (it) {
                        is GameEvent.Next -> {
                            if(questionList[currentQuestion].verify()) {
                                score += 50
                            } else {
                                score -= 20
                            }
                            currentQuestion++
                        }

                        is GameEvent.Quit -> {
                            loggedInNavigator.goTo(Destinations.HOME)
                        }

                        else -> {
                            throw Error("Not Supported.") // This shouldn't happen. Only using an else to save time.
                        }
                    }
                }
            )
        }
    }
}

