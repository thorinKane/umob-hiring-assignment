package com.thorkane.playground.navigation

import kotlinx.coroutines.flow.StateFlow

enum class Destinations {
    HOME,
    GAME,
    HISTORY
}

interface LoggedInNavigator {
    val destinations: StateFlow<List<Destinations>>

    fun goTo(destination: Destinations, mode: Mode = Mode.POP)
    enum class Mode {
        PUSH,
        POP
    }
}