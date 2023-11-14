package com.example.RhythMix.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.RhythMix.RhythMixViewModel
import com.example.RhythMix.getSongs
import androidx.compose.foundation.lazy.items

@Composable
fun HomeScreen(
    vm: RhythMixViewModel
) {
//lazy column use items
    //List songTitles = vm.getSongs()
    LazyColumn(
        content = { items(getSongs()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                       //TODO
                    }
            ) {
                Text(it.title)
            }


        }

        }
    )

//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(10.dp)
//                    .clickable {
//                       //TODO
//                    }
//            ) {
//                Text("Song")
//            }
//        }
    }


@Composable
fun SongRow(songTitle: String) {
    Row(
        modifier = Modifier
    ){
        Text(songTitle)
    }

}