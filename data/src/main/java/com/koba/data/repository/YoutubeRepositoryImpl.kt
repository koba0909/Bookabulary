package com.koba.data.repository

import com.koba.data.service.YoutubeService
import com.koba.data.toYoutubeSearchResults
import com.koba.domain.model.YoutubeSearchResult
import com.koba.domain.repository.YoutubeRepository
import javax.inject.Inject

class YoutubeRepositoryImpl @Inject constructor(
    private val service: YoutubeService
) : YoutubeRepository {
    override suspend fun getYoutubeSearchResult(keyword: String): List<YoutubeSearchResult> {
        return service.getYoutubeVideos(query = keyword).toYoutubeSearchResults()
    }
}