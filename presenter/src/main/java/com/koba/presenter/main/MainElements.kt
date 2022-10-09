package com.koba.presenter.main

import com.koba.core.mvi.MviEffect
import com.koba.core.mvi.MviIntent
import com.koba.core.mvi.MviSideEffect
import com.koba.core.mvi.MviState
import com.koba.domain.model.Book

sealed interface MainIntent : MviIntent {
    object RequestBestSellerBooks : MainIntent
    object RequestRecommendBooks : MainIntent
    object RequestNewBooks : MainIntent
    data class GetBestSellerBooksSuccess(val books: List<Book>) : MainIntent
    data class GetRecommendBooksSuccess(val books: List<Book>) : MainIntent
    data class GetNewBooksSuccess(val books: List<Book>) : MainIntent
    data class GetBestSellerBooksFail(val throwable: Throwable) : MainIntent
    data class GetRecommendBooksFail(val throwable: Throwable) : MainIntent
    data class GetNewBooksFail(val throwable: Throwable) : MainIntent
}

data class MainState(
    val isLoadingBestSeller: Boolean = false,
    val isLoadingRecommend: Boolean = false,
    val isLoadingNew: Boolean = false,
    val bestSellerBooks: List<Book> = emptyList(),
    val recommendBooks: List<Book> = emptyList(),
    val newBooks: List<Book> = emptyList()
) : MviState

sealed interface MainEffect : MviEffect {
    data class ShowToast(val message: String) : MainEffect
}

sealed interface MainSideEffect : MviSideEffect {
    object GetBestSellerBooks : MainSideEffect
    object GetRecommendBooks : MainSideEffect
    object GetNewBooks : MainSideEffect
}
