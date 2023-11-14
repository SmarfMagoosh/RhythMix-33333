package com.example.RhythMix.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import com.example.RhythMix.RhythMixViewModel
import androidx.compose.material3.Text
import com.example.RhythMix.getTracks
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.example.RhythMix.Modifiers
import com.example.RhythMix.TrackCard

@Composable
fun EditScreen(
    vm: RhythMixViewModel,
    modifier: Modifier
) {
    LazyColumn(
        content = {
            items(getTracks()) { TrackCard(sound = it, modifier = modifier, vm = vm)}
        }
    )
}