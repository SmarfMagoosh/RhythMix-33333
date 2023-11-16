package com.example.RhythMix.playback

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}