package com.example.RhythMix.screens

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.RhythMix.RhythMixViewModel
import com.example.RhythMix.getTracks
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.example.RhythMix.TrackCard

@Composable
fun EditScreen(
    vm: RhythMixViewModel,
    modifier: Modifier
) {
    val mp = MediaPlayer()
    mp.setAudioAttributes(
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build())
    LazyColumn(
        content = {
            items(getTracks()) {
                TrackCard(sound = it, modifier = modifier, vm = vm, mp = mp)
            }
        }
    )
}