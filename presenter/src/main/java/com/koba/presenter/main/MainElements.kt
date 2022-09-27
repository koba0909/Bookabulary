package com.koba.presenter.main

import com.koba.core.mvi.MviEffect
import com.koba.core.mvi.MviIntent
import com.koba.core.mvi.MviSideEffect
import com.koba.core.mvi.MviState
import com.koba.domain.model.Book

sealed interface MainIntent : MviIntent {
    object RequestBestSellerList : MainIntent

    data class GetBestSellerListSuccess(val books: List<Book>) : MainIntent

    data class GetBestSellerListFail(val throwable: Throwable) : MainIntent
}

data class MainState(
    val isLoading: Boolean = false,
    val bestSellers: List<Book> = emptyList()
) : MviState

sealed interface MainEffect : MviEffect {
    data class ShowToast(val message: String) : MainEffect
}

sealed interface MainSideEffect : MviSideEffect {
    object GetBestSellerList : MainSideEffect
}
