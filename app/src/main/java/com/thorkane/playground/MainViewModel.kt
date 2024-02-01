package com.thorkane.playground

import android.util.Log
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import com.thorkane.playground.login.LoginManager
import com.thorkane.playground.login.presenter.loginPresenter
import com.thorkane.playground.presenter.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginManager: LoginManager
) : ViewModel() {
    private val scope = CoroutineScope(viewModelScope.coroutineContext + AndroidUiDispatcher.Main)

    /**
     * Events are consumed by presenters, potentially yielding state changes which flow back to the UI.
     */
    private val events = MutableSharedFlow<Event>(extraBufferCapacity = 20)

    /**
     * Route Events to our event flow
     */
    fun take(event: Event) {
        Log.d("Take Event", "$event")
        if (!events.tryEmit(event)) {
            error("Event buffer overflow.")
        }
    }

    val models = scope.launchMolecule(mode = RecompositionMode.ContextClock) {
        loginPresenter(events = events, loginManager = loginManager) {
            take(it)
        }
    }
}
