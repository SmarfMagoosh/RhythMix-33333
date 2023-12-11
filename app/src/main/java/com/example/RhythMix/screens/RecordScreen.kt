package com.example.RhythMix.screens

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.RhythMix.CountUpTimer
import com.example.RhythMix.MetronomeDialog
import com.example.RhythMix.MyDatabaseHelper
import com.example.RhythMix.MyHelper
//import com.example.RhythMix.MyDatabaseHelper
import com.example.RhythMix.R
import com.example.RhythMix.Screens
import com.example.RhythMix.Singleton
import com.example.RhythMix.playback.AndroidAudioPlayer
import com.example.RhythMix.record.AndroidAudioRecorder
import kotlinx.coroutines.withContext
import java.io.File
import java.time.format.TextStyle

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(context: Context) {
    val recorder by lazy { AndroidAudioRecorder(context) }
    val player by lazy { AndroidAudioPlayer(context) }
    var audioFile: File? = null
    val cacheDir = context.cacheDir

    var showAddDialog by remember { mutableStateOf(false) }
    var recordingTitle by remember { mutableStateOf("") }
    var authorName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
//        Text(text = "Song Creation")
//        IconButton(onClick = {
//            showAddDialog = true;
//
//        }) {
//            Icon(
//                imageVector = Icons.Default.Add,
//                contentDescription = "Delete"
//            )
//        }

        Text( text = "Recording Controls",)
        Column(){
            TextField(
                value = recordingTitle,
                onValueChange = { recordingTitle = it },
                label = { Text("Recording Title") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = authorName,
                onValueChange = { authorName = it },
                label = { Text("Author") }
            )
        }

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
            val dbHelper = MyHelper(context)
            val database = dbHelper.writableDatabase

            // Convert audio file contents to byte array
            val audioData = audioFile?.readBytes()

            // Save the song details and audio data to the database
            dbHelper.addTrackWithAudioData(recordingTitle, authorName, "Track", audioData ?: byteArrayOf())
            Text(text = "Save")
        }
        Button(onClick = { player.playFile(audioFile ?: return@Button) }){
            Text(text = "Play")
        }
        Button(onClick = { player.stop() }){
            Text(text = "End")
        }
    }
//    if(showAddDialog){
//        AddSongDialog(
//            onDismiss = {
//                showAddDialog = false
//            },
//            onConfirm = {
//                    userInput ->
//
//            },
//            context, audioFile
//            )
//    }
}


////@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddSongDialog(
//    onDismiss: () -> Unit,
//    onConfirm: (String) -> Unit,
//    context: Context,
//    audioFile: File?
//
//) {
//  //  var recordingTitle by remember { mutableStateOf("") }
//    //var authorName by remember { mutableStateOf("") }
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = {
//            Text("Save Recording")
//        },
//        text = {
//            Column(){
//                TextField(
//                    value = recordingTitle,
//                    onValueChange = { recordingTitle = it },
//                    label = { Text("Recording Title") }
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                TextField(
//                    value = authorName,
//                    onValueChange = { authorName = it },
//                    label = { Text("Author") }
//                )
//            }
//
//        },
//
//        confirmButton = {
//            Button(
//                onClick = {
//
//                }
//            ) {
//                Text("Start")
//            }
//
//
//            Button(
//                onClick = {
//                   // var testTitle = "test"
//                    //var testAuthor = "name"
//                   // var testTrack = "track"
//
//                   // myDB.addSong(testTitle, testAuthor, "-2" )
//                   // val dbHelper = MyHelper(context)
//                    //val database = dbHelper.writableDatabase
//                    //dbHelper.addSong("Song Title", "Author", "Track")
//                    val audioData = audioFile?.readBytes()
//
//                    // Save the song details and audio data to the database
//                    val dbHelper = MyHelper(context)
//                    dbHelper.addSongWithAudioData(recordingTitle, authorName, "Track", audioData ?: byteArrayOf())
//
//                }
//            ) {
//                Text("Save")
//            }
//        }
//    )
//}

