package com.example.RhythMix.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.RhythMix.Modifiers
import com.example.RhythMix.R
import com.example.RhythMix.Singleton
import com.example.RhythMix.classes.Song
import com.example.RhythMix.classes.Sound
import com.example.RhythMix.classes.Track
import java.lang.Integer.parseInt
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun EditScreen(modifier: Modifier) {
    val state = Singleton.vm.state.collectAsState().value
    var popup by rememberSaveable { mutableStateOf(false) }
    var currTrack by rememberSaveable { mutableStateOf<Track?>(null) }
    if (state.editing == null) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Card(modifier = modifier
                .width(150.dp)
                .height(150.dp)) {
                IconButton(onClick = { popup = true }, modifier = modifier.fillMaxSize()) {
                    Icon(
                        imageVector = Icons.Sharp.Add,
                        contentDescription = null,
                        modifier = modifier.fillMaxSize(0.8F))
                }
            }
        }
    }
    else {
        val song = state.editing!!
        if (state.editSongSettings) {
            SongSettingsMenu(modifier = modifier, song = song)
        } else {
            Column(modifier = modifier.fillMaxSize()) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = song.display,
                        fontSize = TextUnit(5F, TextUnitType.Em)
                    )
                    Button(onClick = { popup = true }) {
                        Text(text = "Switch Song")
                    }
                }
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    content = {
                        items(song.tracks) {
                            EditableTrackCard(sound = it, modifier = modifier, onEdit = { currTrack = it })
                        }
                    })
            }
        }
        if (currTrack != null) {
            val track = currTrack!!
            Dialog(onDismissRequest = {
                var remIdx: Int = -1
                for (i in 0 until song.tracks.size) {
                    if (song.tracks[i].title == track.title) remIdx = i
                }
                song.tracks.removeAt(remIdx)
                song.tracks.add(remIdx, track)
                currTrack = null
            }) {
                Card(modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6F)) {
                    Text(
                        text = track.display,
                        fontSize = TextUnit(6F, TextUnitType.Em),
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .padding(15.dp)
                            .fillMaxWidth())
                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        var start by rememberSaveable { mutableStateOf(track.start) }
                        var loops by rememberSaveable { mutableStateOf(track.loops) }
                        var volume by rememberSaveable { mutableFloatStateOf(track.volume) }
                        TextField(
                            modifier = modifier.fillMaxWidth(0.8F),
                            value = start.toString(),
                            onValueChange = {
                                start = if (it == "") 0 else parseInt(it)
                                track.start = start
                            },
                            label = { Text(text = "Start Time", fontSize = TextUnit(5F, TextUnitType.Em)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Next)
                        )
                        TextField(
                            modifier = modifier.fillMaxWidth(0.8F),
                            value = loops.toString(),
                            onValueChange = {
                                loops = if (it == "") 0 else parseInt(it)
                                track.loops = loops
                            },
                            label = { Text(text = "Loops", fontSize = TextUnit(5F, TextUnitType.Em)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Next)
                        )
                        Column(modifier = modifier.fillMaxWidth(0.8F)) {
                            Text(text = "Volume", fontSize = TextUnit(5F, TextUnitType.Em))
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxWidth()) {
                                Slider(value = volume, onValueChange = {
                                    volume = it
                                    track.volume = it
                                }, modifier = modifier.fillMaxWidth(0.7F))
                                Text("${floor(track.volume.toDouble() * 100).toInt()}%")
                            }
                        }
                    }
                }
            }
        }
    }
    if (popup) {
        Dialog(onDismissRequest = { popup = false }) {
            val options = listOf(Song("")) + Singleton.vm.getSongs()
            LazyColumn {
                items(options) {
                    Card(modifier = modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(5.dp)
                        .clickable {
                            popup = false
                            Singleton.vm.setSong(if (it.title == "") Song("New Song") else it)
                        }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = modifier
                                .fillMaxSize()
                                .padding(10.dp)) {
                            when (it.title) {
                                "" -> Icon(
                                        imageVector = Icons.Sharp.Add,
                                        contentDescription = null,
                                        modifier = modifier.fillMaxSize(0.8F))
                                else -> Text(it.title)
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun EditableTrackCard(sound: Sound, modifier: Modifier, ctx: Context = LocalContext.current, onEdit: () -> Unit) {
    Card(modifier = Modifiers.cardModifier) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(20.dp)) {
            Text(sound.display)
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(
                        modifier = modifier.fillMaxWidth(0.2F),
                        horizontalArrangement = Arrangement.End) {
                        IconButton(
                            onClick = { Singleton.vm.play(sound, ctx) },
                            modifier = modifier) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Play")}
                        Image(
                            painter = painterResource(id = R.drawable.pausebutton),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    if (Singleton.vm.mp.isPlaying) {
                                        Singleton.vm.mp.pause()
                                    }
                                }
                        )
                    }
                    IconButton(onClick = onEdit) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
                    }
                }
        }
    }
}
@Composable
fun SongSettingsMenu(modifier: Modifier, song: Song) {
    var tempo by rememberSaveable { mutableFloatStateOf(song.tempo / 512.0F) }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = modifier.fillMaxWidth(0.8F)) {
                Text("Tempo (bpm)")
                Row {
                    Slider(value = tempo, onValueChange = {
                        tempo = it
                        song.tempo = floor((tempo * 512).toDouble()).toInt()
                    }, modifier = modifier.fillMaxWidth(0.7F))
                    Text("${song.tempo} bpm")
                }
            }
        }
    }
}
