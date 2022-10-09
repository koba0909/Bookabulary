package com.koba.presenter.main

import androidx.lifecycle.viewModelScope
import com.koba.core.base.BaseMviViewModel
import com.koba.domain.usecase.GetBestSellerUseCase
import com.koba.domain.usecase.GetNewBooksUseCase
import com.koba.domain.usecase.GetRecommendBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBestSellerUseCase: GetBestSellerUseCase,
    private val getRecommendBooksUseCase: GetRecommendBooksUseCase,
    private val getNewBooksUseCase: GetNewBooksUseCase
) : BaseMviViewModel<MainIntent, MainState, MainEffect, MainSideEffect>(MainState()) {
    init {
        handleIntent(
            MainIntent.RequestBestSellerBooks
        )
    }

    override fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.RequestBestSellerBooks -> {
                updateState {
                    it.copy(
                        isLoadingBestSeller = true
                    )
                }
                handleSideEffect(
                    sideEffect = MainSideEffect.GetBestSellerBooks
                )
            }
            is MainIntent.GetBestSellerBooksSuccess -> {
                updateState {
                    it.copy(
                        isLoadingBestSeller = false,
                        bestSellerBooks = intent.books
                    )
                }
                emitEffect(
                    MainEffect.ShowToast("Success get bestseller")
                )
            }
            is MainIntent.GetBestSellerBooksFail -> {
                updateState {
                    it.copy(
                        isLoadingBestSeller = false
                    )
                }
                emitEffect(
                    MainEffect.ShowToast(
                        "Fail get bestseller list, cause : ${intent.throwable.message}"
                    )
                )
            }
            is MainIntent.RequestNewBooks -> {
                updateState {
                    it.copy(
                        isLoadingRecommend = true
                    )
                }

                handleSideEffect(
                    MainSideEffect.GetRecommendBooks
                )
            }
            is MainIntent.GetRecommendBooksSuccess -> {
                updateState {
                    it.copy(
                        isLoadingRecommend = false,
                        recommendBooks = intent.books
                    )
                }
            }
            is MainIntent.GetRecommendBooksFail -> {
                updateState {
                    it.copy(
                        isLoadingBestSeller = false
                    )
                }
                emitEffect(
                    MainEffect.ShowToast(
                        "Fail to get recommend book list, cause : ${intent.throwable.message}"
                    )
                )
            }

            is MainIntent.RequestNewBooks -> {
                updateState {
                    it.copy(
                        isLoadingNew = true
                    )
                }
                handleSideEffect(
                    MainSideEffect.GetNewBooks
                )
            }
            is MainIntent.GetNewBooksSuccess -> {
                updateState {
                    it.copy(
                        isLoadingNew = false,
                        newBooks = intent.books
                    )
                }
            }
            is MainIntent.GetNewBooksFail -> {
                updateState {
                    it.copy(
                        isLoadingNew = false
                    )
                }
                emitEffect(
                    MainEffect.ShowToast(
                        "Fail to get new book list, cause : ${intent.throwable.message}"
                    )
                )
            }
        }
    }

    override fun handleSideEffect(sideEffect: MainSideEffect) {
        when (sideEffect) {
            is MainSideEffect.GetBestSellerBooks -> {
                viewModelScope.launch {
                    runCatching {
                        getBestSellerUseCase()
                    }.onSuccess {
                        handleIntent(
                            MainIntent.GetBestSellerBooksSuccess(it)
                        )
                    }.onFailure {
                        handleIntent(
                            MainIntent.GetBestSellerBooksFail(it)
                        )
                    }
                }
            }
            is MainSideEffect.GetRecommendBooks -> {
                viewModelScope.launch {
                    runCatching {
                        getRecommendBooksUseCase()
                    }.onSuccess {
                        handleIntent(
                            MainIntent.GetRecommendBooksSuccess(it)
                        )
                    }.onFailure {
                        handleIntent(
                            MainIntent.GetNewBooksFail(it)
                        )
                    }
                }
            }
            is MainSideEffect.GetNewBooks -> {
                viewModelScope.launch {
                    runCatching {
                        getNewBooksUseCase()
                    }.onSuccess {
                        handleIntent(
                            MainIntent.GetNewBooksSuccess(it)
                        )
                    }.onFailure {
                        handleIntent(
                            MainIntent.GetNewBooksFail(it)
                        )
                    }
                }
            }
        }
    }
}
