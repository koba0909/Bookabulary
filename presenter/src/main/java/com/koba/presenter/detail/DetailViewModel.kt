package com.koba.presenter.detail

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.koba.core.base.BaseMviViewModel
import com.koba.domain.usecase.GetSearchOnYoutubeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSearchOnYoutubeUseCase: GetSearchOnYoutubeUseCase,
    savedStateHandle: SavedStateHandle
) : BaseMviViewModel<DetailIntent, DetailState, DetailEffect, DetailSideEffect>(
    DetailState(
        book = Json.decodeFromString(
            Uri.decode(savedStateHandle[KEY_SELECTED_BOOK] ?: "")
        )
    )
) {

    init {
        handleIntent(
            DetailIntent.RequestYoutubeSearch(
                keyword = state.value.book?.title + POST_FIX_BOOK_REVIEW_SEARCH
            )
        )
    }

    override fun handleIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.RequestYoutubeSearch -> {
                handleSideEffect(
                    DetailSideEffect.RequestYoutubeSearch(intent.keyword)
                )
            }
            is DetailIntent.SuccessYoutubeSearch -> {
                Log.d("hugh", "youtube : ${intent.result}")

                updateState {
                    it.copy(
                        youtubeSearchResults = it.youtubeSearchResults
                    )
                }
            }
            is DetailIntent.FailYoutubeSearch -> {
                Log.e(TAG, "FailYoutubeSearch : ${intent.throwable.message}")
            }
        }
    }

    override fun handleSideEffect(sideEffect: DetailSideEffect) {
        when (sideEffect) {
            is DetailSideEffect.RequestYoutubeSearch -> {
                viewModelScope.launch {
                    runCatching {
                        getSearchOnYoutubeUseCase(sideEffect.keyword)
                    }.onSuccess {
                        handleIntent(
                            DetailIntent.SuccessYoutubeSearch(it)
                        )
                    }.onFailure {
                        handleIntent(
                            DetailIntent.FailYoutubeSearch(it)
                        )
                    }
                }
            }
        }
    }

    fun onClickYoutubeItem(url: String) {
        emitEffect(
            DetailEffect.OnClickYoutubeItem(url)
        )
    }

    companion object {
        const val TAG = "DetailViewModel"
        const val KEY_SELECTED_BOOK = "selected_book"
        const val POST_FIX_BOOK_REVIEW_SEARCH = " 책 리뷰"
    }
}
