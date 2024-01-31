package com.thorkane.playground.networking.models

import com.google.gson.annotations.SerializedName

/**
 * The root POJO for the GBFS response.
 */
data class Root(
    @SerializedName("version")
    val version: String,

    @SerializedName("data")
    val data: Locale
)

/**
 * Pass through object for localization. We could change our selection here based on user locale or
 * language preference.
 */
data class Locale(
    @SerializedName("en")
    val en: GbfsFeeds
)

/**
 * Contains the list of GBFS feeds.
 */
data class GbfsFeeds(
    @SerializedName("feeds")
    val feeds: List<Feeds>
)

/**
 * We choose to fetch provider feeds from the documented GBFS list rather than directly integrating with
 * a particular provider feed URL. These URLs are liable to change, so we reference the GBFS Feeds API to ensure
 * we get the most up to date information.
 */
data class Feeds(
    /**
     * The Feed name.
     */
    @SerializedName("name")
    val name: String,

    /**
     * The url for the feed. This url can be used to fetch data from this feed endpoint.
     */
    @SerializedName("url")
    val url: String
)