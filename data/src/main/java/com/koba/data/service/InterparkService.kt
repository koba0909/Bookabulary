package com.koba.data.service

import com.koba.data.BuildConfig
import com.koba.data.dto.BestSellerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface InterparkService {
    @GET("/api/bestSeller.api")
    suspend fun getBestSeller(
        @Query("key")
        key: String = BuildConfig.INTERPARK_KEY,
        @Query("categoryId")
        categoryId: Int = 100,
        @Query("output")
        output: String = "json"
    ): BestSellerDto
}
