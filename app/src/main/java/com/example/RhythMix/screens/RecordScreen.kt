package com.example.RhythMix.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.RhythMix.R
import com.example.RhythMix.Singleton
import com.example.RhythMix.playback.AndroidAudioPlayer
import com.example.RhythMix.record.AndroidAudioRecorder
import java.io.File
import java.time.format.TextStyle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordScreen(context: Context) {
    val recorder by lazy { AndroidAudioRecorder(context) }
    val player by lazy { AndroidAudioPlayer(context) }
    var audioFile: File? = null
    val cacheDir = context.cacheDir

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Song Creation")
        Text( text = "Recording Controls",)

        Button(
            onClick = {
                //you would change this to store externally
                File(cacheDir, "audio.mp3").also{
                    recorder.start(it)
                    audioFile = it
                }
            }){
            Text(text = "Start")
        }
        Button(onClick = { recorder.stop() }){
            Text(text = "Stop")
        }
        Button(onClick = { player.playFile(audioFile ?: return@Button) }){
            Text(text = "Play")
        }
        Button(onClick = { player.stop() }){
            Text(text = "End")
        }
    }
}

