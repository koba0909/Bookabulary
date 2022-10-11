package com.koba.data.network.impl

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.koba.data.network.RetrofitProvider
import java.util.concurrent.TimeUnit
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RetrofitProviderImpl : RetrofitProvider {

    private val jsonBuilder = Json {
        ignoreUnknownKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun getRetrofitBuilder(
        baseUrl: String,
        connectTime: Long,
        readTime: Long,
        writeTime: Long
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
