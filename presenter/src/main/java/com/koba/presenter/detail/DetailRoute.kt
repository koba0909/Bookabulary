package com.koba.presenter.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.koba.domain.model.Book

@Composable
fun DetailRoute(
    viewModel: DetailViewModel
) {
    val state = viewModel.state.collectAsState()

    DetailScreen(
        state = state.value,
        effectFlow = viewModel.effect
    )
}
