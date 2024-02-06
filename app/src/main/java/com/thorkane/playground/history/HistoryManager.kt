package com.thorkane.playground.history

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import javax.inject.Inject

typealias HistoryList = List<HistoryManager.HistoryEntry>

interface HistoryManager {
    val history: StateFlow<HistoryList>

    suspend fun recordScore(score: Int)

    data class HistoryEntry(
        val score: Int,
        val time: LocalDateTime
    )
}

/**
 * TODO save the history to the local disk so you can view it across sessions.
 */
class HistoryManagerImpl @Inject constructor(): HistoryManager {
    private val _history: MutableStateFlow<HistoryList> = MutableStateFlow(emptyList())
    override val history: StateFlow<HistoryList> = _history.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun recordScore(score: Int) {
        _history.emit(_history.value + HistoryManager.HistoryEntry(score, LocalDateTime.now()))
    }
}