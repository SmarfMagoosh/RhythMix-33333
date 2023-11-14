package com.example.RhythMix.classes

class Mix(val track: Track, val start: Float)
class Song(
    var title: String,
    var sounds: MutableList<Mix>
) {
    var length: Float = 0F
        get() = sounds.map {it.start + it.track.length}.max()
    
}