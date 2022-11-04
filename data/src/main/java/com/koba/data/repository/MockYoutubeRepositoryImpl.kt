package com.koba.data.repository

import com.koba.data.dto.YoutubeSearchDto
import com.koba.data.mock.MockData
import com.koba.data.toYoutubeSearchResults
import com.koba.domain.model.YoutubeSearchResult
import com.koba.domain.repository.YoutubeRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MockYoutubeRepositoryImpl @Inject constructor(): YoutubeRepository {
    val jsonBuilder = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun getYoutubeSearchResult(keyword: String): List<YoutubeSearchResult> {
        return jsonBuilder.decodeFromString<YoutubeSearchDto>(
            MockData.youtubeSearchMockData
        ).toYoutubeSearchResults()
    }
}