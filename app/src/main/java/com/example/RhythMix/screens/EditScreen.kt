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
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.RhythMix.Modifiers
import com.example.RhythMix.R
import com.example.RhythMix.Singleton
import com.example.RhythMix.TrackCard
import com.example.RhythMix.classes.Song
import com.example.RhythMix.classes.Sound
import com.example.RhythMix.classes.Track

@SuppressLint("UnrememberedMutableState")
@Composable
fun EditScreen(modifier: Modifier) {
    val state = Singleton.vm.state.collectAsState().value
    var popup by rememberSaveable { mutableStateOf(false) }

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
    } else {
        val song = state.editing!!
        Column(modifier = modifier.fillMaxSize()) {
            Row(
                modifier = modifier.fillMaxWidth().padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (song.title.length > 15) song.title.take(15) + "..." else song.title,
                    fontSize = TextUnit(5F, TextUnitType.Em))
                Button(onClick = { popup = true }) {
                    Text(text = "Import New Song")
                }
            }
        }
    }
    if (popup) {
        Dialog(onDismissRequest = { popup = false }) {
            LazyColumn(content = {
                items(Singleton.vm.getSongs()) {
                    Card(modifier = modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(5.dp)
                        .clickable {
                            popup = false
                            Singleton.vm.setSong(it)
                        }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = modifier
                                .fillMaxSize()
                                .padding(10.dp)) {
                            Text(it.title)
                        }
                    }
                }
            })
        }
    }
}

@Composable
fun EditableTrackCard(sound: Sound, modifier: Modifier, ctx: Context = LocalContext.current) {

}