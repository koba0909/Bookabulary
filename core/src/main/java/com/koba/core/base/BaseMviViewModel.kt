package com.koba.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koba.core.mvi.MviEffect
import com.koba.core.mvi.MviIntent
import com.koba.core.mvi.MviSideEffect
import com.koba.core.mvi.MviState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseMviViewModel<I : MviIntent, S : MviState, E : MviEffect, SE : MviSideEffect>(
    initialState: S
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<E>()
    val effect = _effect.asSharedFlow()

    private inline fun updateState(function: (S) -> S) {
        _state.update(function)
    }

    private fun emitEffect(effect: E) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}
