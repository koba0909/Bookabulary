package com.koba.presenter.main

import androidx.lifecycle.viewModelScope
import com.koba.core.base.BaseMviViewModel
import com.koba.domain.usecase.GetBestSellerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBestSellerUseCase: GetBestSellerUseCase
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
        }
    }
}
