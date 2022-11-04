package com.koba.domain.repository

import com.koba.domain.model.YoutubeSearchResult

interface YoutubeRepository {
    suspend fun getYoutubeSearchResult(keyword: String): List<YoutubeSearchResult>
}
