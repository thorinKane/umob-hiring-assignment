package com.thorkane.playground.navigation.impl

import com.thorkane.playground.game.presenter.GamePresenter
import com.thorkane.playground.home.HomePresenter
import com.thorkane.playground.navigation.Destinations
import com.thorkane.playground.navigation.LoggedInNavigator
import com.thorkane.playground.presenter.Presenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * I don't love doing this in this way but I don't have time to build up the needed DI
 * solution to do it more elegantly.
 */
class DestinationResolver @Inject constructor(
    private val homePresenter: HomePresenter,
    private val gamePresenter: GamePresenter
) {
    fun resolveDestination(destination: Destinations): Presenter<*> {
        return when(destination) {
            Destinations.HOME -> homePresenter
            Destinations.GAME -> gamePresenter
            Destinations.HISTORY -> {
                throw Error("Destination Not yet Implemented")
            }
        }
    }
}

/**
 * A super simple navigation solution.
 */
class LoggedInNavigatorImpl @Inject constructor(): LoggedInNavigator {
    private val _destinations = MutableStateFlow(listOf(Destinations.HOME))
    override val destinations: StateFlow<List<Destinations>> = _destinations
    override fun goTo(destination: Destinations, mode: LoggedInNavigator.Mode) {
        val newDestinations = when(mode) {
            LoggedInNavigator.Mode.PUSH -> destinations.value + destination
            LoggedInNavigator.Mode.POP -> {
                val index = destinations.value.indexOfLast { it == destination }
                if (index >= 0) {
                    destinations.value.subList(0, index + 1)
                } else {
                    destinations.value + destination
                }
            }
        }

        setDestinations(newDestinations)
    }

    private fun setDestinations(destinations: List<Destinations>) {
        require(destinations.isNotEmpty()) {
            "There must be at least one destination."
        }

        _destinations.value = destinations
    }
}