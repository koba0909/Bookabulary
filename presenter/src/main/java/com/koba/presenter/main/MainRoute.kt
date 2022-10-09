package com.koba.presenter.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun MainRoute(viewModel: MainViewModel) {
    val state = viewModel.state.collectAsState()

    MainScreen(
        state = state,
        effectFlow = viewModel.effect,
        onRequestRefreshBookList = {
            viewModel.handleIntent(MainIntent.RequestBestSellerBooks)
        }
    )
}
