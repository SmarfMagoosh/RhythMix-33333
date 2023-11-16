package com.example.RhythMix.screens

import android.media.MediaPlayer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.RhythMix.RhythMixViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.example.RhythMix.TrackCard

@Composable
fun EditScreen(
    vm: RhythMixViewModel,
    modifier: Modifier,
    mp: MediaPlayer
) {
    LazyColumn(
        content = {
            items(vm.getTracks()) {
                TrackCard(sound = it, modifier = modifier, vm = vm, mp = mp)
            }
        }
    )
}