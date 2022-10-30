package com.koba.presenter.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.koba.domain.model.Book

@Composable
fun MainRoute(
    viewModel: MainViewModel,
    onNavigateToBookDetail: (Book) -> Unit
) {
    val state = viewModel.state.collectAsState()

    MainScreen(
        state = state,
        effectFlow = viewModel.effect,
        onRequestRefreshBookList = {
            viewModel.handleIntent(
                MainIntent.RequestBestSellerBooks
            )
        },
        onRequestRecommendBookList = {
            viewModel.handleIntent(
                MainIntent.RequestRecommendBooks
            )
        },
        onRequestNewBookList = {
            viewModel.handleIntent(
                MainIntent.RequestNewBooks
            )
        },
        onNavigateToBookDetail = onNavigateToBookDetail
    )
}
