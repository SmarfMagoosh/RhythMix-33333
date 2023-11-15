package com.example.RhythMix

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.RhythMix.screens.EditScreen
import com.example.RhythMix.screens.HomeScreen
import com.example.RhythMix.screens.RecordScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.RhythMix.classes.Sound
import com.example.RhythMix.screens.AudioManager

enum class Screens(@StringRes val title: Int) {
    Home(R.string.home),
    Edit(R.string.home),
    Record(R.string.record)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhythMixApp(modifier: Modifier = Modifier,audioManager: AudioManager) {
    val vm = RhythMixViewModel()
    val navController = rememberNavController()
    val prevVisits by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(prevVisits?.destination?.route?: Screens.Home.name)
    Scaffold(
        topBar = { RhythMixTopBar() },
        bottomBar = { RhythMixBottomBar(
            modifier = modifier,
            homeClick = {
                navController.navigate(Screens.Home.name)
                // TODO: Change icon color
            },
            editClick = {
                navController.navigate(Screens.Edit.name)
                // TODO: Change icon color
            },
            recordClick = {
                navController.navigate(Screens.Record.name)
                // TODO: Change icon color
            }) },
        modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = Screens.Home.name,
            modifier = modifier.padding(it)) {
            composable(route = Screens.Home.name) {
                HomeScreen(vm = vm, modifier = modifier)
            }
            composable(route = Screens.Edit.name) {
                EditScreen(vm = vm, modifier = modifier)
            }
            composable(route = Screens.Record.name) {
                RecordScreen(vm = vm, audioManager)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhythMixTopBar() {
    TopAppBar(
        title = {
            Text("RythMix")
                
        },
        actions = {
            IconButton(
                onClick = { /* Handle icon click */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create"
                )
            }
            IconButton(
                onClick = { /* Handle icon click */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }

    )
}

@Composable
fun RhythMixBottomBar(modifier: Modifier, homeClick: () -> Unit, editClick: () -> Unit, recordClick: () -> Unit) {
    BottomAppBar{
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        ) {
            IconButton(
                modifier = modifier,
                onClick = homeClick) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home")
            }
            IconButton(
                modifier = modifier,
                onClick = editClick) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit")
            }
            IconButton(
                modifier = modifier,
                onClick = recordClick) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Record")
            }
        }
    }
}

@Composable
fun TrackCard(sound: Sound, modifier: Modifier, vm: RhythMixViewModel) {
    Card(
        modifier = Modifiers.cardModifier
    ) {
        Column(
            modifier = modifier.fillMaxHeight().padding(20.dp)
        ) {
            Text(
                text = sound.title
            )
            // TODO: Play button and things?
        }
    }
}