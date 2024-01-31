package com.thorkane.playground.repositories

import com.thorkane.playground.networking.APIResponse
import com.thorkane.playground.networking.GbfsApi
import com.thorkane.playground.networking.models.BikeStatusFeed
import com.thorkane.playground.networking.models.GbfsFeeds

class GbfsService(private val api: GbfsApi) {
    suspend fun getGbfsFeeds(): APIResponse<GbfsFeeds> {
        return try {
            val feeds = api.getFeeds()
            // Probably not great to blindly access these properties but at this point Retrofit assures me they exist
            APIResponse.Success(data = feeds.data.en)
        } catch (exception: Exception) { // TODO more specific exception type
            APIResponse.Error(message = "An Error Occurred: ${exception.message.toString()}")
        }
    }

}