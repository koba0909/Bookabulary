package com.koba.presenter.detail

import com.koba.core.base.BaseMviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : BaseMviViewModel<DetailIntent, DetailState, DetailEffect, DetailSideEffect>(
    DetailState()
) {
    override fun handleIntent(intent: DetailIntent) {
        TODO("Not yet implemented")
    }

    override fun handleSideEffect(sideEffect: DetailSideEffect) {
        TODO("Not yet implemented")
    }
}
