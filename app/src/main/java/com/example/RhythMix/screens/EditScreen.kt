package com.example.RhythMix.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.RhythMix.RhythMixViewModel
import androidx.compose.ui.Modifier

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditScreen(
    vm: RhythMixViewModel,
    modifier: Modifier
) {
    val state = vm.state.collectAsState().value
    println(vm.state.value)
}