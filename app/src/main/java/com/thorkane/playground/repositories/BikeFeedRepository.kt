package com.thorkane.playground.repositories

import com.thorkane.playground.networking.models.Bike
import kotlinx.coroutines.flow.StateFlow

typealias BikeList = List<Bike>

interface BikeFeedRepository {
    val feed: StateFlow<BikeList>
}
