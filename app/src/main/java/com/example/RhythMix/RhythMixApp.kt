package com.example.RhythMix

import android.content.Context
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.RhythMix.classes.Song

import com.example.RhythMix.classes.Sound
import com.example.RhythMix.classes.Track

enum class Screens(@StringRes val title: Int) {
    Home(R.string.home),
    Edit(R.string.home),
    Record(R.string.record)
}

@RequiresApi(34)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhythMixApp(modifier: Modifier = Modifier) {
    val vm = RhythMixViewModel()
    val navController = rememberNavController()
    val prevVisits by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(prevVisits?.destination?.route ?: Screens.Home.name)
    Scaffold(
        topBar = { RhythMixTopBar(currentScreen = currentScreen, vm =vm, navController = navController)},
        bottomBar = { RhythMixBottomBar(
            modifier = modifier,
            homeClick = {
                vm.mp.reset()
                navController.navigate(Screens.Home.name)
            },
            editClick = {
                vm.mp.reset()
                navController.navigate(Screens.Edit.name)
            },
            recordClick = {
                vm.mp.reset()
                navController.navigate(Screens.Record.name)
            }) },
        modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = Screens.Home.name,
            modifier = modifier.padding(it)) {
            composable(route = Screens.Home.name) {
                HomeScreen(
                    vm = vm,
                    modifier = modifier,
                    editSong = {
                        navController.navigate(Screens.Edit.name)
                    }
                )
            }
            composable(route = Screens.Edit.name) {
                EditScreen(vm = vm, modifier = modifier)
            }
            composable(route = Screens.Record.name) {
                RecordScreen(vm = vm , context = LocalContext.current)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhythMixTopBar(currentScreen: Screens, vm: RhythMixViewModel, navController: NavController) {
    TopAppBar(
        title = {
            Text("RhythMix")
        },
        actions = {when (currentScreen) {
            Screens.Home -> {
                IconButton(onClick = {
                    // TODO
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create"
                    )
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
            Screens.Edit -> {
                IconButton(onClick = {
                    vm.mp.reset()
                    navController.navigate(Screens.Record.name)
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Delete"
                    )
                }
                IconButton(onClick = { /* Handle icon click */ }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Create"
                    )
                }
            }
            Screens.Record -> {

                IconButton(onClick = { /* Handle icon click */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Delete"
                    )
                }
                IconButton(onClick = { /* Handle icon click */ }) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Create"
                    )
                }
            }
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
fun TrackCard(
    sound: Sound,
    modifier: Modifier,
    vm: RhythMixViewModel,
    ctx: Context = LocalContext.current,
    editSong: () -> Unit = {}) {
    Card(modifier = Modifiers.cardModifier) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(20.dp)) {
            Text(sound.title)
            if (sound is Track) {
                Row(
                    modifier = modifier.fillMaxWidth(0.2F),
                    horizontalArrangement = Arrangement.End) {
                    IconButton(
                        onClick = { vm.play(sound, ctx) },
                        modifier = modifier) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Play")
                    }
                    Image(
                        painter = painterResource(id = R.drawable.pausebutton),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clickable {
                                if (vm.mp.isPlaying) {
                                    vm.mp.pause()
                                }
                            })
                }
            } else if (sound is Song) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(
                        modifier = modifier.fillMaxWidth(0.2F),
                        horizontalArrangement = Arrangement.End) {
                        IconButton(
                            onClick = { vm.play(sound, ctx) },
                            modifier = modifier) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Play")
                        }
                        Image(
                            painter = painterResource(id = R.drawable.pausebutton),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    if (vm.mp.isPlaying) {
                                        vm.mp.pause()
                                    }
                                })
                    }
                    IconButton(
                        onClick = editSong,
                        modifier = modifier) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null)
                    }
                }
            }
        }
    }
}

