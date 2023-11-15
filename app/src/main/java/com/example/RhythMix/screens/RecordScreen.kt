package com.example.RhythMix.screens

import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import com.example.RhythMix.RhythMixViewModel
import androidx.compose.material3.Text
import java.io.File
import java.io.IOException



@Composable
fun RecordScreen(
    vm: RhythMixViewModel
) {
    Text("Record Audio")

}

class AudioManager(private val context: Context){

    var mediaRecorder: MediaRecorder? = null
    var mediaPlayer: MediaPlayer? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mediaRecorder = MediaRecorder(context)
        }
        mediaPlayer = MediaPlayer()
    }

    // Initialize MediaPlayer


    //get filepath using their id for a saved song
    private fun filepathId(id: Int) : String{
        return context.getExternalFilesDir(null)!!.absolutePath + "/$id.aac"
    }

    fun startRecording(id: Int): Boolean {
        if(context.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            }else{
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

            return true
        }else{
            return false
        }
    }

    fun stopRecording(){
        mediaRecorder?.stop()
        mediaRecorder?.release()
        mediaRecorder = null
    }

    fun startPlayback(id: Int): Boolean{
        val path = filepathId(id)
        if(File(path).exists()){
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(path)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
            return true
        }
        return false
    }

    fun stopPlayback(){
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}