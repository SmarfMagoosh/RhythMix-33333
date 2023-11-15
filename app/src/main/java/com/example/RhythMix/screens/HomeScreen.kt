package com.example.RhythMix.screens

import android.content.res.Resources
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.RhythMix.RhythMixViewModel
import com.example.RhythMix.getSongs
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import com.example.RhythMix.R
import com.example.RhythMix.TrackCard

@RequiresApi(34)
@Composable
fun HomeScreen(
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
            items(getSongs()) {
                TrackCard(sound = it, vm = vm, modifier = modifier, mp = mp)
            }
        }
    )
}