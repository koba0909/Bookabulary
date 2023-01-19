package com.koba.presenter.detail

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.koba.domain.model.Book
import kotlin.math.log

@Composable
fun DetailRoute(
    viewModel: DetailViewModel
) {
    val state = viewModel.state.collectAsState()

    Log.d("koba", state.value.youtubeSearchResults.toString())

    DetailScreen(
        state = state.value,
        effectFlow = viewModel.effect
    )
}
