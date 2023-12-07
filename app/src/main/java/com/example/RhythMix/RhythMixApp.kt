package com.example.RhythMix

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.RhythMix.classes.Song
import com.example.RhythMix.classes.Sound
import com.example.RhythMix.classes.Track
import com.example.RhythMix.screens.EditScreen
import com.example.RhythMix.screens.HomeScreen
import com.example.RhythMix.screens.RecordScreen

enum class Screens { Home, Edit, Record }
object Singleton {
    val vm: RhythMixViewModel = RhythMixViewModel()
    @SuppressLint("StaticFieldLeak")
    var controller: NavHostController? = null
}
@RequiresApi(34)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhythMixApp(modifier: Modifier = Modifier) {
    Singleton.controller = rememberNavController()
    val prevVisits by Singleton.controller!!.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(prevVisits?.destination?.route ?: Screens.Home.name)

    Scaffold(
        topBar = { RhythMixTopBar(currentScreen = currentScreen)},
        bottomBar = { RhythMixBottomBar(
            modifier = modifier,
            homeClick = {
                Singleton.vm.mp.reset()
                Singleton.controller!!.navigate(Screens.Home.name)
            },
            editClick = {
                Singleton.vm.mp.reset()
                Singleton.controller!!.navigate(Screens.Edit.name)
            },
            recordClick = {
                Singleton.vm.mp.reset()
                Singleton.controller!!.navigate(Screens.Record.name)
            }) },
        modifier = modifier) {
        NavHost(
            navController = Singleton.controller!!,
            startDestination = Screens.Home.name,
            modifier = modifier.padding(it)) {
            composable(route = Screens.Home.name) {
                HomeScreen(modifier = modifier)
            }
            composable(route = Screens.Edit.name) {
                EditScreen(modifier = modifier)
            }
            composable(route = Screens.Record.name) {
                RecordScreen(context = LocalContext.current)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhythMixTopBar(currentScreen: Screens) {
    var showDialog by remember { mutableStateOf(false) }
    var showTimerDialog by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text("RhythMix") },
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
                    Singleton.vm.mp.reset()
                    Singleton.controller!!.navigate(Screens.Record.name)
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
                when (Singleton.vm.state.collectAsState().value.editing) {
                    null -> {}
                    else -> IconButton(onClick = { Singleton.vm.editSong() }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Expand"
                        )
                    }
                }
            }
            Screens.Record -> {

                val custom1ImagePainter: Painter = painterResource(R.drawable.timer)

                IconButton(onClick = {

                    showTimerDialog= true
                }) {

                    Image(
                        painter = custom1ImagePainter,
                        contentDescription = "timer image",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }


                val custom2ImagePainter: Painter = painterResource(R.drawable.triangle)

                IconButton(onClick = {

                    showDialog= true
                }) {

                    Image(
                        painter = custom2ImagePainter,
                        contentDescription = "metronome image",
                        modifier = Modifier
                            .size(27.dp)
                            .padding(0.dp,5.dp,0.dp,0.dp)
                    )
                }


            }
        }
        }

    )
    if (showDialog) {
        MetronomeDialog(
            onDismiss = {
                showDialog = false
            },
            onConfirm = {
                    userInput ->

               // vm.playSoundMultipleTimes(userInput)

            },

        )
    }
    if (showTimerDialog) {
        TimerDialog(
            onDismiss = {
                showTimerDialog = false
            },


            )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetronomeDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var updateMetronomeText by remember { mutableStateOf("") }
    val mTimer: CountUpTimer = object : CountUpTimer(30000) {
        private val mediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.metronomemp3)
        override fun onTick(second: Int) {
            updateMetronomeText = second.toString()
            mediaPlayer.start()
        }
        override fun onFinish() {

            mediaPlayer.release()
        }
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Metronome")
        },
        text = {
            Text(updateMetronomeText )
            Text("4/4 time")
        },

        confirmButton = {
            Button(
                onClick = {
                    mTimer.start()
                }
            ) {
                Text("Start")
            }

            Button(
                onClick = {
                    mTimer.cancel()
                    mTimer.onFinish()
                }
            ) {
                Text("Stop")
            }

            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Close")
            }

        }
    )
}

abstract class CountUpTimer protected constructor(private val duration: Long) :
    CountDownTimer(duration, INTERVAL_MS) {
    abstract fun onTick(second: Int)
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
@Composable
fun TimerDialog(
    onDismiss: () -> Unit
) {
    var updateTimerText by remember { mutableStateOf("") }
    val timer: CountUpTimer = object : CountUpTimer(30000) {
        override fun onTick(second: Int) {
            updateTimerText = second.toString()
        }
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Timer in Seconds")
        },
        text = {
            Text(updateTimerText )
        },

        confirmButton = {
            Button(
                onClick = {
                    timer.start()
                }
            ) {
                Text("Start")
            }

            Button(
                onClick = {
                    timer.cancel()
                }
            ) {
                Text("Stop")
            }

            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Close")
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
    ctx: Context = LocalContext.current,
    editSong: (Song) -> Unit = {}) {
    Card(modifier = Modifiers.cardModifier) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(20.dp)
        ) {
            Text(sound.display)
            if (sound is Track) {
                Row(
                    modifier = modifier.fillMaxWidth(0.2F),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { Singleton.vm.play(sound, ctx) },
                        modifier = modifier) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Play"
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.pausebutton),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clickable {
                                if (Singleton.vm.mp.isPlaying) {
                                    Singleton.vm.mp.pause()
                                }
                            })
                }
            } else if (sound is Song) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = modifier.fillMaxWidth(0.2F),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = { Singleton.vm.play(sound, ctx) },
                            modifier = modifier) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Play"
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.pausebutton),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    if (Singleton.vm.mp.isPlaying) {
                                        Singleton.vm.mp.pause()
                                    }
                                })
                    }
                    IconButton(
                        onClick = { editSong(sound) },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}


