package com.koba.core.base

import androidx.lifecycle.ViewModel
import com.koba.core.mvi.MviEffect
import com.koba.core.mvi.MviIntent
import com.koba.core.mvi.MviSideEffect
import com.koba.core.mvi.MviState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class BaseMviViewModel<I : MviIntent, S : MviState, E : MviEffect, SE : MviSideEffect>(
    initialState: S
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<E>()
    val effect = _effect.asSharedFlow()
}
