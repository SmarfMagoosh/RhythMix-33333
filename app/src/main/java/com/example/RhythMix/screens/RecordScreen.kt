package com.example.RhythMix.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.RhythMix.MyHelper
//import com.example.RhythMix.MyDatabaseHelper
import com.example.RhythMix.playback.AndroidAudioPlayer
import com.example.RhythMix.record.AndroidAudioRecorder
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(context: Context) {

    val recorder by lazy { AndroidAudioRecorder(context) }
    val player by lazy { AndroidAudioPlayer(context) }
    var audioFile: File? = null
    val cacheDir = context.cacheDir
    var recordingTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Track Creation",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(60.dp))
        Text(text = "Recording Controls")
        Spacer(modifier = Modifier.height(16.dp))
        Column() {
            TextField(
                value = recordingTitle,
                onValueChange = { recordingTitle = it },
                label = { Text("Recording Title") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {

                    File(cacheDir, "audio.mp3").also {
                        recorder.start(it)
                        audioFile = it
                    }
                }) {
                Text(text = "Start")
            }
            Button(onClick = { recorder.stop() }) {
                val dbHelper = MyHelper(context)
                val database = dbHelper.writableDatabase

                // Convert audio file contents to byte array
                val audioData = audioFile?.readBytes()

                // Save the track to database
                dbHelper.addTrack(
                    recordingTitle,
                    -1,
                    -1,
                    -1F,
                    audioData ?: byteArrayOf()
                )
                Text(text = "Save")
            }
            Button(onClick = { player.playFile(audioFile ?: return@Button) }) {
                Text(text = "Play")
            }
            Button(onClick = { player.stop() }) {
                Text(text = "End")
            }

            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = {
                val url = "https://www.musictheory.net/"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }) {
                Text(text = "Music Theory Tips")
            }
        }

    }
}



