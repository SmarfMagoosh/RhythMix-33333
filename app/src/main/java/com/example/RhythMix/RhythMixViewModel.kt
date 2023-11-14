package com.example.RhythMix

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

fun getSongs(): List<Song> = listOf(
    Song("Reptilia"),
    Song("Yellow"),
    Song("Everglow"),
    Song("Let it go"),
    Song("Black Betty"),
    Song("Mississippi Queen"),
    Song("All Along the Watchtower"),
    Song("The Final Countdown"),
    Song("The Immigrant Song"),
    Song("Head in the Ceiling Fan"),
    Song("Not the Same Anymore")
)

fun getTracks(): List<Track> = listOf(
    Track("The Sickest Riff"),
    Track("Bass"),
    Track("Vocals"),
    Track("anotha one")
)

class RhythMixViewModel: ViewModel() {
    private var _state = MutableStateFlow( State() )
    val state = _state.asStateFlow()

    // TODO
}