package com.example.rhythmix

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.RhythMix.R
import androidx.navigation.compose.rememberNavController


enum class Screens(@StringRes val title: Int) {
    Home(R.string.home),
    Edit(R.string.home),
    Record(R.string.record)
}

@Composable
fun RhythMixApp(modifier: Modifier = Modifier) {
    val vm = RhythMixViewModel()
    val navController = rememberNavController()
    NavHost(
        navController = rememberNavController(),
        startDestination = Screens.Home.name,
        modifier = modifier) {
        // TODO :(
    }
}