package com.example.RhythMix

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.RhythMix.classes.Song
import com.example.RhythMix.classes.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class State(
    val foo: Int = 0,
    val bar: Int = 1
    // TODO
)

object Modifiers {
    val cardModifier: Modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(10.dp)
}

fun getSongs(): List<Song> = listOf(
    Song("Reptilia", mutableListOf(), null),
    Song("Yellow", mutableListOf(), null),
    Song("Everglow", mutableListOf(), null),
    Song("Let it go", mutableListOf(), null),
    Song("Black Betty", mutableListOf(), null),
    Song("Mississippi Queen", mutableListOf(), null),
    Song("All Along the Watchtower", mutableListOf(), null),
    Song("The Final Countdown", mutableListOf(), null),
    Song("The Immigrant Song", mutableListOf(), null),
    Song("Head in the Ceiling Fan", mutableListOf(), null),
    Song("Not the Same Anymore", mutableListOf(), null)
)

fun getTracks(): List<Track> = listOf(
    Track("The Sickest Riff", null),
    Track("Bass", null),
    Track("Vocals", null),
    Track("anotha one", null)
)

class RhythMixViewModel: ViewModel() {
    private var _state = MutableStateFlow( State() )
    val state = _state.asStateFlow()

    // TODO
}