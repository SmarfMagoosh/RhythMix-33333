package com.example.RhythMix.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import com.example.RhythMix.RhythMixViewModel
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditScreen(
    vm: RhythMixViewModel,
    modifier: Modifier
) {
    val currentState by vm.mainCatTitle.collectAsState()
    //Log.d("edit screen state",vm.state.value.toString())
    Log.d("edit screen currentstate",currentState.title)
    Text(currentState.title)
    Text("")
    Text("")

}