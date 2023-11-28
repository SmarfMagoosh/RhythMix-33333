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
import com.example.RhythMix.classes.Song
import com.example.RhythMix.classes.Sound
import com.example.RhythMix.classes.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

data class State(var editing: Song? = null)
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
        Song("Reptilia", mutableListOf()),
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
            // TODO launch coroutines :(
        } else if (sound is Track) {
            mp.reset()
            mp.setDataSource(ctx.resources.openRawResourceFd(sound.id))
            mp.setVolume(sound.volume, sound.volume)
            mp.prepare()
            Thread.sleep(sound.start)
            mp.start()
        }
    }
    fun setSong(song: Song) = _state.update { it.copy(editing = song) }
    suspend fun playSoundMultipleTimes( timesToPlay: String) {
        withContext(Dispatchers.IO) {
            repeat(timesToPlay.toInt()) {
             //   play(sound, ctx)
                delay(1000) // Adjust the delay based on your requirements
            }
        }
    }
}

