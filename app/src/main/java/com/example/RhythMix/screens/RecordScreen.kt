package com.example.RhythMix.screens

import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.example.RhythMix.RhythMixViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.RhythMix.playback.AndroidAudioPlayer
import com.example.RhythMix.record.AndroidAudioRecorder
import java.io.File
import java.io.IOException



@Composable
fun RecordScreen(
    vm: RhythMixViewModel,
    context: Context
) {
     val recorder by lazy{
        AndroidAudioRecorder(context)
    }

   val player by lazy{
        AndroidAudioPlayer(context)
    }

    var audioFile: File? = null

    val cacheDir = context.cacheDir

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = {
                //you would change this to store externally
                File(cacheDir, "audio.mp3").also{
                    recorder.start(it)
                    audioFile = it

                }

            }){
            Text( text = "Start")
        }
        Button(
            onClick = {
                recorder.stop()
            }){
            Text( text = "Stop")
        }
        Button(
            onClick = {
                player.playFile(audioFile ?: return@Button)

            }){
            Text( text = "Play")
        }
        Button(
            onClick = {
                player.stop()
            }){
            Text( text = "End")
        }

    }
}

