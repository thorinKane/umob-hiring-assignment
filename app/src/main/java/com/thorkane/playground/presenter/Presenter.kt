package com.thorkane.playground.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * We wrap composable functions up in a class so that DI plays along in a more friendly way.
 */
interface Presenter<ModelT : Model> {
    @Composable
    fun present(): ModelT
}

@Composable
fun <EventT : Any> onEvent(
    handler: @DisallowComposableCalls (EventT) -> Unit,
): (EventT) -> Unit {
    val eventFlow = remember { MutableSharedFlow<EventT>() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            handler(event)
        }
    }

    return remember {
        {
            scope.launch {
                eventFlow.emit(it)
            }
        }
    }
}
