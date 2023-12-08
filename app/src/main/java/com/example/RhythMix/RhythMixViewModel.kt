package com.example.RhythMix

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
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
        Song("Drag Queen", mutableListOf(
            Track("Bass", R.raw.drag_queen_bass),
            Track("Riff", R.raw.drag_queen_riff),
            Track("Harmony", R.raw.drag_queen_harmony)
        )),
        Song("Reptilia", mutableListOf(
            Track("The Sickest Riff Known to Mankind", R.raw.sweet_child_of_mine),
            Track("Bass", R.raw.billie_jean),
            Track("Vocals", R.raw.boulevard_of_broken_dreams),
            Track("Saxophone Solo that Goes Crazy", R.raw.careless_whisper),
            Track("Vine Boom Sound Effect", R.raw.vine_boom)
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
    fun play(sound: Sound, ctx: Context) {
        if (sound is Song) {
            val players: MutableList<MediaPlayer> = mutableListOf()
            for (track in sound.tracks) {
                val trackPlayer = MediaPlayer()
                trackPlayer.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                trackPlayer.setDataSource(ctx.resources.openRawResourceFd(track.id))
                trackPlayer.prepare()
                players.add(trackPlayer)
            }
            val scope = CoroutineScope(Dispatchers.Main)
            val pairs = sound.tracks.zip(players)
            viewModelScope.launch {
                coroutineScope {
                    for (pair in pairs) {
                        scope.launch {
                            Thread.sleep(pair.first.start * 6000L / sound.tempo)
                            pair.second.start()
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

    suspend fun playSoundMultipleTimes( timesToPlay: String) {
        withContext(Dispatchers.IO) {
            repeat(timesToPlay.toInt()) {
             //   play(sound, ctx)
                delay(1000) // Adjust the delay based on your requirements
            }
        }
    }
}

