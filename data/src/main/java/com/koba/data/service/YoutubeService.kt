package com.koba.data.service

import com.koba.data.BuildConfig
import com.koba.data.dto.YoutubeSearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeService {
    @GET("/youtube/v3/search")
    fun getYoutubeVideos(
        @Query("q")
        query: String,
        @Query("key")
        key: String = BuildConfig.YOUTUBE_KEY,
        @Query("part")
        part: String = "snippet",
        @Query("type")
        type: String = "video",
        @Query("regionCode")
        regionCode: String = "KR"
    ): YoutubeSearchDto
}
