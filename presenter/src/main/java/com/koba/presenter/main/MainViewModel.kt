package com.koba.presenter.main

import com.koba.core.base.BaseMviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseMviViewModel
<MainIntent, MainState, MainEffect, MainSideEffect>(MainState()) {
    override fun handleIntent(intent: MainIntent) {
        TODO("Not yet implemented")
    }

    override fun handleSideEffect(sideEffect: MainSideEffect) {
        TODO("Not yet implemented")
    }
}
