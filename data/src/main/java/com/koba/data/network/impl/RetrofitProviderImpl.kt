package com.koba.data.network.impl

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RetrofitProviderImpl(
    private val baseUrl: String
) {

    private val jsonBuilder = Json {
        ignoreUnknownKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun getRetrofitBuilder(
        connectTime: Long = 10L,
        readTime: Long = 10L,
        writeTime: Long = 10L
    ): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(connectTime, TimeUnit.SECONDS)
                    .readTimeout(readTime, TimeUnit.SECONDS)
                    .writeTimeout(writeTime, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(
                jsonBuilder.asConverterFactory("application/json".toMediaType())
            )
}
