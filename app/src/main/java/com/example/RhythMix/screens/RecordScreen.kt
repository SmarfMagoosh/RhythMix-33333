package com.example.RhythMix.screens

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.RhythMix.MyHelper
import com.example.RhythMix.R
//import com.example.RhythMix.MyDatabaseHelper
import com.example.RhythMix.playback.AndroidAudioPlayer
import com.example.RhythMix.record.AndroidAudioRecorder

import java.io.File
import java.util.Timer
import java.util.TimerTask

abstract class CountUpTimer protected constructor(
    private val duration: Long
) :
    CountDownTimer(duration, INTERVAL_MS) {
    abstract fun onTick(second: Int)
    abstract fun getText(): String
    override fun onTick(msUntilFinished: Long) {
        val second = ((duration - msUntilFinished) / 1000).toInt()
        onTick(second)
    }

    override fun onFinish() {
        onTick(duration / 1000)
    }

    companion object {
        private const val INTERVAL_MS: Long = 1000
    }
}

private val timer: CountUpTimer = object : CountUpTimer(30000) {
    var updateTimerText = ""
    override fun onTick(second: Int) {
        updateTimerText = second.toString()
    }

    override fun getText() : String{
        return updateTimerText
    }
}

private fun createMTimer(context: Context): CountUpTimer {
    return object : CountUpTimer(300000) {
        private val mediaPlayer = MediaPlayer.create(context, R.raw.metronomemp3)
        var updateMetronomeText = ""

        override fun onTick(second: Int) {
            updateMetronomeText = second.toString()
            mediaPlayer.start()
        }

        override fun getText(): String {
            return updateMetronomeText
        }

        override fun onFinish() {
            mediaPlayer.release()
        }
    }
}

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
        Spacer(modifier = Modifier.height(30.dp))
        Row() {
            Column() {

                val custom1ImagePainter: Painter = painterResource(R.drawable.timer)

                IconButton(onClick = {


                }) {

                    Image(
                        painter = custom1ImagePainter,
                        contentDescription = "timer image",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
                var timerText by remember { mutableStateOf(timer.getText()) }
                var count by remember { mutableStateOf(0) }
                val mytimer = Timer()
                val task = object : TimerTask() {
                    override fun run() {
                        count = count + 1
                        println("Count: $count")
                    }
                }
                var clicked = true

                Text(
                    count.toString() + " sec"
                )
                Button(
                    onClick = {

                        timer.start()
                        if(clicked){
                            mytimer.scheduleAtFixedRate(task, 0, 1000)
                        }

                        Log.d("button", "start timer")
                    }
                ) {
                    Text("Start")
                }

                Button(
                    onClick = {
                        clicked = false
                        count = 0
                        timer.cancel()
                        timer.onFinish()
                        Log.d("button", "stop timer")
                    }
                ) {
                    Text("Stop")
                }
            }
            Spacer(modifier = Modifier.width(40.dp))
            Column() {

                val custom2ImagePainter: Painter = painterResource(R.drawable.triangle)
                IconButton(onClick = {

                }) {

                    Image(
                        painter = custom2ImagePainter,
                        contentDescription = "metronome image",
                        modifier = Modifier
                            .size(27.dp)
                            .padding(0.dp,5.dp,0.dp,0.dp)
                    )
                }
                 val mTimer: CountUpTimer = createMTimer(context)
                var mTimerText by remember { mutableStateOf(mTimer.getText()) }

                Text(mTimerText + " 60 bpm")

                Button(
                    onClick = {
                        mTimer.start()
                        Log.d("button", "start metronome")
                    }
                ) {
                    Text("Start")
                }

                Button(
                    onClick = {
                        mTimer.cancel()
                        mTimer.onFinish()
                        Log.d("button", "stop metronome")
                    }
                ) {
                    Text("Stop")
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
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

            Spacer(modifier = Modifier.height(20.dp))
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





