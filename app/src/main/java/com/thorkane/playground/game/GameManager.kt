package com.thorkane.playground.game

import android.os.Build
import android.util.Log
import com.thorkane.playground.coroutines.IODispatcher
import com.thorkane.playground.history.HistoryManager
import com.thorkane.playground.repositories.BikeList
import com.thorkane.playground.repositories.DottRepository
import com.thorkane.playground.repositories.LimeRepository
import com.thorkane.playground.repositories.PonyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameManager @Inject constructor(
    private val dottRepository: DottRepository,
    private val limeRepository: LimeRepository,
    private val ponyRepository: PonyRepository,
    private val historyManager: HistoryManager,
    @IODispatcher dispatcher: CoroutineDispatcher
) {
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
    private val _limeFeed: MutableStateFlow<BikeList> = MutableStateFlow(emptyList())
    private val _ponyFeed: MutableStateFlow<BikeList> = MutableStateFlow(emptyList())
    private val _dottFeed: MutableStateFlow<BikeList> = MutableStateFlow(emptyList())

    /**
     * Exposes the full data feed for all bike feed providers.
     */
    val feed = combine(_limeFeed, _ponyFeed, _dottFeed) { elements ->
        elements.flatMap { it }
    }

    init {
        coroutineScope.launch {
            feed.collect {
                Log.d("Game Manager", "Bike List: $it")
            }
        }
        coroutineScope.launch {
            limeRepository.feed.collect {
                _limeFeed.emit(it)
            }
        }
        coroutineScope.launch {
            dottRepository.feed.collect {
                _dottFeed.emit(it)
            }
        }
        coroutineScope.launch {
            ponyRepository.feed.collect {
                _ponyFeed.emit(it)
            }
        }
    }

    fun recordScore(score: Int) {
        coroutineScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                historyManager.recordScore(score)
            }
        }
    }
}