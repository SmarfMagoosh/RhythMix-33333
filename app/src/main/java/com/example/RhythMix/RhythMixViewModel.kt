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
    Song("Reptilia", mutableListOf(), R.raw.reptilia),
    Song("Yellow", mutableListOf(), R.raw.yellow),
    Song("Black Betty", mutableListOf(), R.raw.black_betty),
    Song("Mississippi Queen", mutableListOf(), R.raw.mississippi_queen),
    Song("Head in the Ceiling Fan", mutableListOf(), R.raw.head_in_the_cieling_fan),
    Song("Not the Same Anymore", mutableListOf(), R.raw.not_the_same_anymore)
)
fun getTracks(): List<Track> = listOf(
    Track("The Sickest Riff Known to Mankind", R.raw.sweet_child_of_mine),
    Track("Bass", R.raw.billie_jean),
    Track("Vocals", R.raw.boulevard_of_broken_dreams),
    Track("Saxophone Solo that Goes Crazy", R.raw.careless_whisper),
    Track("Vine Boom Sound Effect", R.raw.vine_boom)
)
class RhythMixViewModel: ViewModel() {
    private var _state = MutableStateFlow( State() )
    val state = _state.asStateFlow()
    // TODO
}