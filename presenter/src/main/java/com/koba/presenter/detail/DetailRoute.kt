package com.koba.presenter.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun DetailRoute(detailViewModel: DetailViewModel) {
    val state = detailViewModel.state.collectAsState()

    DetailScreen(
        state = state.value,
        effectFlow = detailViewModel.effect
    )
}
