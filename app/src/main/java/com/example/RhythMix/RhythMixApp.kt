package com.example.RhythMix

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

import com.example.RhythMix.classes.Sound

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
    val mp = MediaPlayer()
    mp.setAudioAttributes(
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build())
    val navController = rememberNavController()
    val prevVisits by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(prevVisits?.destination?.route ?: Screens.Home.name)
    Scaffold(
        //mp.reset()
        topBar = { RhythMixTopBar(currentScreen = currentScreen, vm =vm, navController = navController)},
        bottomBar = { RhythMixBottomBar(
            modifier = modifier,
            homeClick = {
                //mp.reset()
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
                HomeScreen(vm = vm, modifier = modifier)
            }
            composable(route = Screens.Edit.name) {
                EditScreen(vm = vm, modifier = modifier, mp = mp)
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
    ctx: Context = LocalContext.current) {
    Card(modifier = Modifiers.cardModifier) {
        Column(modifier = modifier
            .fillMaxHeight()
            .padding(20.dp)) {
            Text(sound.title)
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
               // horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        vm.mp.reset()
                       vm.mp.setDataSource(ctx.resources.openRawResourceFd(sound.file))
                        vm.mp.prepare()
                        vm.mp.start()
                    }) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play")
                }

                ClickableImage(vm.mp)

            }
        }
    }
}
@Composable
fun ClickableImage(mp: MediaPlayer) {
    val context = LocalContext.current

    // Replace R.drawable.pausebutton with your actual image resource ID
    val imageResId = R.drawable.pausebutton

    // Add a clickable modifier to the Image composable
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        modifier = Modifier
            .clickable {
                       if (mp.isPlaying) {
                           mp.pause()
                        }
            }
            .size(80.dp)
            .padding(10.dp)

    )
}

