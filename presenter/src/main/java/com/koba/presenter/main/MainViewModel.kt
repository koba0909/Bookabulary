package com.koba.presenter.main

import androidx.lifecycle.viewModelScope
import com.koba.core.base.BaseMviViewModel
import com.koba.domain.usecase.GetBestSellerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBestSellerUseCase: GetBestSellerUseCase
) : BaseMviViewModel<MainIntent, MainState, MainEffect, MainSideEffect>(MainState()) {
    init {
        handleIntent(
            MainIntent.RequestBestSellerList
        )
    }

    override fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.RequestBestSellerList -> {
                updateState {
                    it.copy(
                        isLoading = true
                    )
                }
                handleSideEffect(
                    sideEffect = MainSideEffect.GetBestSellerList
                )
            }
            is MainIntent.GetBestSellerListSuccess -> {
                updateState {
                    it.copy(
                        isLoading = false,
                        bestSellers = intent.books
                    )
                }
            }
            is MainIntent.GetBestSellerListFail -> {
                updateState {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun handleSideEffect(sideEffect: MainSideEffect) {
        when (sideEffect) {
            is MainSideEffect.GetBestSellerList -> {
                viewModelScope.launch {
                    runCatching {
                        getBestSellerUseCase()
                    }.onSuccess {
                        handleIntent(
                            MainIntent.GetBestSellerListSuccess(it)
                        )
                    }.onFailure {
                        handleIntent(
                            MainIntent.GetBestSellerListFail(it)
                        )
                    }
                }
            }
        }
    }
}
