package com.example.RhythMix.screens

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.RhythMix.singleton

@Composable
fun EditScreen(
    modifier: Modifier
) {
    val state = singleton.vm.state.collectAsState()
    Text(state.value.editing?.title ?: "hello")
}