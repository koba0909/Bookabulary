package com.koba.presenter.detail

import com.koba.domain.model.Book
import com.koba.domain.model.YoutubeSearchResult

data class DetailState(
    val book: Book,
    val youtubeSearchResult: YoutubeSearchResult?
)

sealed interface DetailIntent {
    data class ShowDetailScreen(val book: Book)

    data class RequestYoutubeSearch(val keyword: String)

    data class SuccessYoutubeSearch(val result: YoutubeSearchResult)

    data class FailYoutubeSearch(val exception: Exception)
}
