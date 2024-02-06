package com.thorkane.playground.history.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thorkane.playground.history.presenter.HistoryPresenter

@Composable
fun HistoryScreen(model: HistoryPresenter.HistoryModel) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
    ) {
        model.historyList.forEach {
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = it.time.toString())
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = it.score.toString())
            }
        }
    }
}