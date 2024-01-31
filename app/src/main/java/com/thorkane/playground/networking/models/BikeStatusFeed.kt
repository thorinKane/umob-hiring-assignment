package com.thorkane.playground.networking.models

import com.google.gson.annotations.SerializedName

data class BikeStatusFeed(
    @SerializedName("version")
    private val version: String,

    @SerializedName("data")
    private val data: Bikes
)

data class Bikes(
    @SerializedName("bikes")
    private val bikes: List<Bike>
)

/**
 * Defines the status of a particular Bike from the "Free_Bike_Status" Feed of
 * GBFS. For more details see [https://github.com/MobilityData/gbfs/blob/master/systems.csv]
 */
data class Bike(
    @SerializedName("bike_id")
    private val bikeId: String,

    @SerializedName("lat")
    private val lat: Double,

    @SerializedName("lon")
    private val lon: Double,

    @SerializedName("is_disabled")
    private val isDisabled: Boolean,

    @SerializedName("is_reserved")
    private val isReserved: Boolean,

    @SerializedName("current_range_meters")
    private val currentRangeMeters: Int,

    @SerializedName("current_fuel_percent")
    private val currentFuelPercent: Float,
    // Add more properties here as needed
)