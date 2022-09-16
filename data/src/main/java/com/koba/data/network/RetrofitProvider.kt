package com.koba.data.network

import retrofit2.Retrofit

interface RetrofitProvider {
    fun getRetrofitBuilder(
        baseUrl: String = "",
        connectTime: Long = 10L,
        readTime: Long = 10L,
        writeTime: Long = 10L
    ): Retrofit.Builder
}
