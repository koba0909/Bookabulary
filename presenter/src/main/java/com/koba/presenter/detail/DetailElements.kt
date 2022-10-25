package com.koba.presenter.detail

import com.koba.core.mvi.MviEffect
import com.koba.core.mvi.MviIntent
import com.koba.core.mvi.MviSideEffect
import com.koba.core.mvi.MviState
import com.koba.domain.model.Book
import com.koba.domain.model.YoutubeSearchResult

data class DetailState(
    val book: Book? = null,
    val youtubeSearchResult: YoutubeSearchResult? = null
) : MviState

sealed interface DetailIntent : MviIntent {
    data class ShowDetailScreen(val book: Book)

    data class RequestYoutubeSearch(val keyword: String)

    data class SuccessYoutubeSearch(val result: YoutubeSearchResult)

    data class FailYoutubeSearch(val exception: Exception)
}

sealed interface DetailEffect : MviEffect

sealed interface DetailSideEffect : MviSideEffect {
    data class RequestYoutubeSearch(val keyword: String)
}
