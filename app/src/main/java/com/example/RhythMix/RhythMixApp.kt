package com.example.RhythMix

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.RhythMix.screens.EditScreen
import com.example.RhythMix.screens.HomeScreen
import com.example.RhythMix.screens.RecordScreen


enum class Screens(@StringRes val title: Int) {
    Home(R.string.home),
    Edit(R.string.home),
    Record(R.string.record)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhythMixApp(modifier: Modifier = Modifier) {
    val vm = RhythMixViewModel()
    val navController = rememberNavController()
    Scaffold(
        topBar = { RhythMixTopBar() },
        bottomBar = { RhythMixBottomBar() },
        modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = Screens.Home.name,
            modifier = modifier.padding(it)) {
            composable(route = Screens.Home.name) {
                HomeScreen(vm = vm)
            }

            composable(route = Screens.Edit.name) {
                EditScreen(vm = vm)
            }
            composable(route = Screens.Record.name) {
                RecordScreen(vm = vm)
            }
        }
    }
}

@Composable
fun RhythMixTopBar() {

}

@Composable
fun RhythMixBottomBar() {
    BottomAppBar {

    }
}