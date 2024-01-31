package com.thorkane.playground.networking

import com.thorkane.playground.networking.models.BikeStatusFeed
import com.thorkane.playground.networking.models.Root
import retrofit2.http.GET

/**
 * Defines the networking interface with GBFS.
 */
interface GbfsApi {
    /**
     * Get request for Feeds
     */
    @GET("gbfs.json")
    suspend fun getFeeds(): Root

    /**
     * Get request for Free Bike Status Feed.
     */
    @GET("free_bike_status.json")
    suspend fun getFreeBikeStatus(): BikeStatusFeed
}

/**
 * A better way to do this might be some sort of registry. Potentially a registry could be
 * built up using codegen like in Anvil, see [https://github.com/square/anvil]. For now we will just statically declare
 * our endpoints we are interested in.
 *
 * Below are the three chosen provider integrations per the requirements. I focused on a single locale,
 * Brussels, in order to allow for easy comparison of service level across the providers. In addition
 * to this these 3 provider APIs had a high degree of uniformity which limits the number of edge cases
 * we need to handle in our models; this is ideal for an exercise of this scope.
 *
 * See [https://github.com/umob-app/hiring-assignment] for more info.
 */
@ApiUrl("https://gbfs.api.ridedott.com/public/v2/brussels/")
interface RideDottApi: GbfsApi

@ApiUrl("https://data.lime.bike/api/partners/v2/gbfs/brussels/")
interface LimeApi: GbfsApi

@ApiUrl("https://gbfs.getapony.com/v1/Brussels/en/")
interface PonyApi: GbfsApi
