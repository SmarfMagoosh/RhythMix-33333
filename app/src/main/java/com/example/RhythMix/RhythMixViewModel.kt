package com.example.RhythMix

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
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


class AudioManager(private val context: Context) {

    var mediaRecorder: MediaRecorder? = null

    //use Evan's media player
    //var mediaPlayer: MediaPlayer? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mediaRecorder = MediaRecorder(context)
        }
        // mediaPlayer = MediaPlayer()
    }


    //get filepath using their id for a saved song
    private fun filepathId(id: Int): String {
        return context.getExternalFilesDir(null)!!.absolutePath + "/$id.aac"
    }

    fun startRecording(id: Int): Boolean {
        if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)

            } else {
                @Suppress("DEPRECATION")
                (MediaRecorder())
            }

            //name source of audio
            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)

            //sampling rate and encoding bit
            mediaRecorder?.setAudioSamplingRate(48000)
            mediaRecorder?.setAudioEncodingBitRate(128000)

            //where to save file
            val fileLoc = filepathId(id)
            mediaRecorder?.setOutputFile(fileLoc)

            //start recording
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            Log.e(ContentValues.TAG, "starting recording")
            return true
        } else {
            return false
        }
    }

    fun stopRecording() {
        try {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            mediaRecorder = null
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Error on stop recording: ${e.message}")
        }
    }
}