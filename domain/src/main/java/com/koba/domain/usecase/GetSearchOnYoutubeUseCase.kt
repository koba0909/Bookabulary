package com.koba.domain.usecase

import com.koba.domain.model.YoutubeSearchResult
import com.koba.domain.repository.YoutubeRepository
import javax.inject.Inject

class GetSearchOnYoutubeUseCase @Inject constructor(
    private val youtubeRepository: YoutubeRepository
) {
    suspend operator fun invoke(keyword: String): YoutubeSearchResult {
        return youtubeRepository.getYoutubeSearchResult(keyword)
    }
}
