package com.koba.data.network

import retrofit2.Retrofit

interface RetrofitProvider {
    fun getRetrofitBuilder(): Retrofit.Builder
}
