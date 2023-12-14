package com.example.RhythMix

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.RhythMix.classes.Song
import com.example.RhythMix.classes.Sound
import com.example.RhythMix.classes.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class State(
    var editing: Song? = null,
    var editSongSettings: Boolean = false
)
object Modifiers {
    val cardModifier: Modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(5.dp)
}
class RhythMixViewModel: ViewModel() {
    private var _state = MutableStateFlow( State() )
    val state = _state.asStateFlow()
    val mp = MediaPlayer()
    init {
        mp.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }
    fun getSongs(): List<Song> = listOf(
        Song("Luck", mutableListOf(
            Track("Bass", R.raw.bass, loops = 3),
            Track("Riff", R.raw.riff),
            Track("Vocals", R.raw.vocals),
            Track("Drums", R.raw.drums, start = 16)
        )),
        Song("Yellow", mutableListOf()),
        Song("Black Betty", mutableListOf()),
        Song("Mississippi Queen", mutableListOf()),
        Song("Head in the Ceiling Fan", mutableListOf()),
        Song("Not the Same Anymore", mutableListOf())
    )
    fun getTracks(): List<Track> = listOf(
        Track("The Sickest Riff Known to Mankind", R.raw.sweet_child_of_mine),
        Track("Bass", R.raw.billie_jean),
        Track("Vocals", R.raw.boulevard_of_broken_dreams),
        Track("Saxophone Solo that Goes Crazy", R.raw.careless_whisper),
        Track("Vine Boom Sound Effect", R.raw.vine_boom)
    )
    fun play(sound: Sound, ctx: Context, players: List<MediaPlayer> = listOf()) {
        if (sound is Song) {
            val pairs = players.zip(sound.tracks)
            viewModelScope.launch {
                coroutineScope {
                    for (pair in pairs) {
                        Singleton.scope.launch {
                            val mspb: Double = (sound.tempo * 50.0 / 3.0)
                            val sleep = (pair.second.start * mspb).toLong()
                            Thread.sleep(sleep)
                            var cntr = 0
                            pair.first.setOnCompletionListener {
                                cntr++
                                if (cntr < pair.second.loops) {
                                    it.start()
                                }
                            }
                            pair.first.start()
                        }
                    }
                }
            }
        } else if (sound is Track) {
            mp.reset()
            mp.setDataSource(ctx.resources.openRawResourceFd(sound.id))
            mp.prepare()
            mp.start()
        }
    }
    fun setSong(song: Song) = _state.update { it.copy(editing = song) }
    fun editSong() = _state.update { it.copy(editSongSettings = !_state.value.editSongSettings) }
}

