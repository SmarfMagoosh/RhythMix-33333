package com.example.RhythMix

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

enum class Screens(@StringRes val title: Int) {
    Home(R.string.home),
    Edit(R.string.home),
    Record(R.string.record)
}

object singleton {
    val vm: RhythMixViewModel = RhythMixViewModel()
    @SuppressLint("StaticFieldLeak")
    var controller: NavHostController? = null
        get() = field
        set(input) { field = input }
}

@RequiresApi(34)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhythMixApp(modifier: Modifier = Modifier) {
    val vm = RhythMixViewModel()
    singleton.controller = rememberNavController()

    val prevVisits by singleton.controller!!.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(prevVisits?.destination?.route ?: Screens.Home.name)


    Scaffold(
        topBar = { RhythMixTopBar(currentScreen = currentScreen)},
        bottomBar = { RhythMixBottomBar(
            modifier = modifier,
            homeClick = {
                vm.mp.reset()
                singleton.controller!!.navigate(Screens.Home.name)
            },
            editClick = {
                vm.mp.reset()
                singleton.controller!!.navigate(Screens.Edit.name)
            },
            recordClick = {
                vm.mp.reset()
                singleton.controller!!.navigate(Screens.Record.name)
            }) },
        modifier = modifier) {
        NavHost(
            navController = singleton.controller!!,
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
fun RhythMixTopBar(currentScreen: Screens, vm: RhythMixViewModel, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    var showTimerDialog by remember { mutableStateOf(false) }
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
                    singleton.vm.mp.reset()
                    singleton.controller!!.navigate(Screens.Record.name)
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

                IconButton(onClick = {
                    showTimerDialog = true
                }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Timer"
                    )
                }
                IconButton(onClick = {
                    showDialog = true
                }) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Metronome"
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
         //   onConfirm = {
                //    userInput ->

                // vm.playSoundMultipleTimes(userInput)

          //  },

            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetronomeDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit

) {
    var userInput by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Metronome")
        },

        text = {

            TextField(
                value = userInput,
                onValueChange = {
                    userInput = it
                },
                label = {
                    Text("BPM")
                }
            )
        },

        confirmButton = {
            Button(
                onClick = {

                    val timesToPlay = userInput.toIntOrNull() ?: 0
                    onConfirm(timesToPlay.toString())
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },

        dismissButton = {

            Button(
                onClick = {

                    onDismiss()
                }
            ) {
                Text("Cancel")
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
}@OptIn(ExperimentalMaterial3Api::class)
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
    //val state by vm.state.collectAsState()
    Card(modifier = Modifiers.cardModifier) {

        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(20.dp)
        ) {
            Text(sound.title)
            if (sound is Track) {
                Row(
                    modifier = modifier.fillMaxWidth(0.2F),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { singleton.vm.play(sound, ctx) },
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
                                if (singleton.vm.mp.isPlaying) {
                                    singleton.vm.mp.pause()
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
                            onClick = { singleton.vm.play(sound, ctx) },
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
                                    if (singleton.vm.mp.isPlaying) {
                                        singleton.vm.mp.pause()
                                    }
                                })
                    }
                    IconButton(
                        onClick = {
                            if (sound is Song) {
                                editSong(sound)
                                Log.d("onclick edit",sound.title)
                                //vm.addSongToEditing(sound.title)


                            }
                        },
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


