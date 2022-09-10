package com.koba.data.service

import com.koba.data.BuildConfig
import com.koba.data.dto.BestSellerDto
import retrofit2.http.GET
import retrofit2.http.Path

interface InterparkService {
    @GET("/api/bestSeller.api")
    suspend fun getBestSeller(
        @Path("key")
        key: String = BuildConfig.INTERPARK_KEY,
        @Path("categoryId")
        categoryId: Int = 100,
        @Path("output")
        output: String = "json"
    ): BestSellerDto
}
