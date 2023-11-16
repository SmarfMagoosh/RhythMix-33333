package com.example.RhythMix.screens

import android.content.res.Configuration
import android.media.MediaPlayer
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.RhythMix.RhythMixViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import com.example.RhythMix.TrackCard
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@RequiresApi(34)
@Composable
fun HomeScreen(
    vm: RhythMixViewModel,
    modifier: Modifier,
    mp: MediaPlayer
) {
    when(LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                modifier = modifier.fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = modifier.fillMaxWidth(0.5F),
                    content = {
                        items(vm.getSongs()) {
                            TrackCard(sound = it, vm = vm, modifier = modifier, mp = mp)
                        }
                    }
                )
                LazyColumn(
                    modifier = modifier.fillMaxWidth(),
                    content = {
                        items(vm.getTracks()) {
                            TrackCard(sound = it, modifier = modifier, vm = vm, mp = mp)
                        }
                    }
                )
            }
        }
        else -> {
            val viewingTracks = remember { mutableStateOf(false) }
            val tracks = vm.getTracks()
            val songs = vm.getSongs()
            Column(modifier = modifier.fillMaxSize()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Your Songs",
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .fillMaxWidth(0.5F)
                            .background(color = if (viewingTracks.value) Color.Black else Color.DarkGray)
                            .padding(horizontal = 0.dp, vertical = 20.dp)
                            .clickable { viewingTracks.value = false })
                    Text(
                        text = "Your Tracks",
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .fillMaxWidth()
                            .background(color = if (viewingTracks.value) Color.DarkGray else Color.Black)
                            .padding(horizontal = 0.dp, vertical = 20.dp)
                            .clickable { viewingTracks.value = true })
                }
                LazyColumn(
                    modifier = modifier.fillMaxWidth().padding(15.dp),
                    content = {
                        items(if (viewingTracks.value) tracks else songs) {
                            TrackCard(sound = it, modifier = modifier, vm = vm, mp = mp)
                        }
                    })
            }
        }
    }
}
