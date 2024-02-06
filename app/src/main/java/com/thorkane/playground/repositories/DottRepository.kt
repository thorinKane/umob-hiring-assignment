package com.thorkane.playground.repositories

import android.util.Log
import com.google.gson.GsonBuilder
import com.thorkane.playground.coroutines.IODispatcher
import com.thorkane.playground.networking.APIResponse
import com.thorkane.playground.networking.RetrofitHelper
import com.thorkane.playground.networking.RideDottApi
import com.thorkane.playground.service.GbfsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DottRepository @Inject constructor(
    gbfsServiceFactory: GbfsService.GbfsServiceFactory,
    @IODispatcher dispatcher: CoroutineDispatcher
): BikeFeedRepository {
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
    private val gbfsService: GbfsService = gbfsServiceFactory.create(
        RetrofitHelper.getRetrofitInstance(RideDottApi::class.java)
    )

    private val _feed: MutableStateFlow<BikeList> = MutableStateFlow(emptyList())
    override val feed: StateFlow<BikeList> = _feed

    init {
        fetchBikeFeed()
    }

    fun fetchBikeFeed() {
        coroutineScope.launch {
            val response = gbfsService.getBikeStatusFeed()
            Log.d("GBFS RES", "onCreate: ${GsonBuilder().setPrettyPrinting().create().toJson(response)}")

            when(response) {
                is APIResponse.Success -> {
                    // TODO represent success state
                }
                is APIResponse.Error -> {
                    // TODO represent error state
                }
            }
        }
    }
}