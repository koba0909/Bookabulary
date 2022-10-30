package com.koba.presenter.detail

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.koba.core.base.BaseMviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseMviViewModel<DetailIntent, DetailState, DetailEffect, DetailSideEffect>(
    DetailState(
        book = Json.decodeFromString(
            Uri.decode(savedStateHandle[KEY_SELECTED_BOOK] ?: "")
        )
    )
) {

    init {
        Log.d("koba", state.value.toString())
    }

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
