package com.example.RhythMix

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class State(
    val foo: Int = 0,
    val bar: Int = 1
    // TODO
)

class RhythMixViewModel: ViewModel() {
    private var _state = MutableStateFlow( State() )
    val state = _state.asStateFlow()

    // TODO
}