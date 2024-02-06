package com.thorkane.playground.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.thorkane.playground.navigation.LoggedInNavigator
import com.thorkane.playground.presenter.Model
import com.thorkane.playground.presenter.Presenter
import javax.inject.Inject

/**
 * Translates [Destinations] into Presenters.
 */
class LoggedInNavigationPresenter @Inject constructor(
    private val loggedInNavigator: LoggedInNavigator,
    private val destinationResolver: DestinationResolver
): Presenter<Model> {
    @Composable
    override fun present(): Model {
        val destinations by loggedInNavigator.destinations.collectAsState()
        val destination = destinations.last()

        val presenter = remember(destination) {
            destinationResolver.resolveDestination(destination)
        }

        return presenter.present()
    }
}