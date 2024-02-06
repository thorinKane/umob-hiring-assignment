package com.thorkane.playground.history.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.thorkane.playground.history.HistoryList
import com.thorkane.playground.history.HistoryManager
import com.thorkane.playground.presenter.Model
import com.thorkane.playground.presenter.Presenter
import javax.inject.Inject

class HistoryPresenter @Inject constructor(
    private val historyManager: HistoryManager
): Presenter<HistoryPresenter.HistoryModel> {
    data class HistoryModel(
        val historyList: HistoryList
    ): Model

    @Composable
    override fun present(): HistoryModel {
        val history by historyManager.history.collectAsState()

        return HistoryModel(
            historyList = history
        )
    }
}