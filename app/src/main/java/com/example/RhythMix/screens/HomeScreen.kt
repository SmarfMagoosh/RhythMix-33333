package com.example.RhythMix.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.RhythMix.RhythMixViewModel
import com.example.RhythMix.getSongs
import androidx.compose.foundation.lazy.items
import com.example.RhythMix.Modifiers
import com.example.RhythMix.TrackCard

@Composable
fun HomeScreen(
    vm: RhythMixViewModel,
    modifier: Modifier
) {
    LazyColumn(
        content = {
            items(getSongs()) { TrackCard(sound = it, modifier = modifier, vm = vm)}
        }
    )
}


@Composable
fun SongRow(songTitle: String) {
    Row(
        modifier = Modifier
    ){
        Text(songTitle)
    }

}