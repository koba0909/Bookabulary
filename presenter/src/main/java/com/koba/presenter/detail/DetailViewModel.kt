package com.koba.presenter.detail

import androidx.lifecycle.SavedStateHandle
import com.koba.core.base.BaseMviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseMviViewModel<DetailIntent, DetailState, DetailEffect, DetailSideEffect>(
    DetailState(
        book = savedStateHandle[KEY_SELECTED_BOOK]
    )
) {
    override fun handleIntent(intent: DetailIntent) {
        when (intent) {
            else -> {
                // TODO YOUTUBE SEARCH
            }
        }
    }

    override fun handleSideEffect(sideEffect: DetailSideEffect) {
        TODO("Not yet implemented")
    }

    companion object {
        const val KEY_SELECTED_BOOK = "selected_book"
    }
}
