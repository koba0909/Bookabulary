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
    data class RequestYoutubeSearch(val keyword: String) : DetailIntent

    data class SuccessYoutubeSearch(val result: YoutubeSearchResult) : DetailIntent

    data class FailYoutubeSearch(val throwable: Throwable) : DetailIntent
}

sealed interface DetailEffect : MviEffect {
    data class OnClickYoutubeItem(val url: String): DetailEffect
}

sealed interface DetailSideEffect : MviSideEffect {
    data class RequestYoutubeSearch(val keyword: String) : DetailSideEffect
}
