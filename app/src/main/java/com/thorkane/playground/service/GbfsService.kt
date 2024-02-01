package com.thorkane.playground.service

import com.thorkane.playground.networking.APIResponse
import com.thorkane.playground.networking.GbfsApi
import com.thorkane.playground.networking.models.BikeStatusFeed
import com.thorkane.playground.networking.models.GbfsFeeds
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GbfsService @AssistedInject constructor(
    @Assisted private val api: GbfsApi
) {
    suspend fun getGbfsFeeds(): APIResponse<GbfsFeeds> {
        return try {
            val feeds = api.getFeeds()
            // Probably not great to blindly access these properties but at this point Retrofit assures me they exist
            APIResponse.Success(data = feeds.data.en)
        } catch (exception: Exception) { // TODO more specific exception type
            APIResponse.Error(message = "An Error Occurred: ${exception.message.toString()}")
        }
    }

    suspend fun getBikeStatusFeed(): APIResponse<BikeStatusFeed> {
        return try {
            val feeds = api.getFreeBikeStatus()
            APIResponse.Success(data = feeds)
        } catch (exception: Exception) { // TODO more specific exception type
            APIResponse.Error(message = "An Error Occurred: ${exception.message.toString()}")
        }
    }

    @AssistedFactory
    interface GbfsServiceFactory {
        fun create(api: GbfsApi): GbfsService
    }
}