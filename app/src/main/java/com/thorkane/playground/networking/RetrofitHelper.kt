package com.thorkane.playground.networking

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofitInstance(url: String): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(url)
            .addConverterFactory(
                GsonConverterFactory
                    .create(
                        GsonBuilder()
                    .create()
                    )
            )
            .build()
    }
}