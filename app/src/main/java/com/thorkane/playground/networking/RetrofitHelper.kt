package com.thorkane.playground.networking

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val defaultUrl = ""

    fun <T> getRetrofitInstance(clazz: Class<T>): T {
        return Retrofit
            .Builder()
            .baseUrl(getApiUrlAnnotation(clazz))
            .addConverterFactory(
                GsonConverterFactory
                    .create(
                        GsonBuilder()
                    .create()
                    )
            )
            .build()
            .create(clazz)
    }

    private fun <T> getApiUrlAnnotation(clazz: Class<T>): String {
        val apiUrlAnnotation = clazz.annotations.find { it is ApiUrl} as ApiUrl?
        Log.d("API URL", "${apiUrlAnnotation?.url}")
        return apiUrlAnnotation?.url ?: defaultUrl
    }
}