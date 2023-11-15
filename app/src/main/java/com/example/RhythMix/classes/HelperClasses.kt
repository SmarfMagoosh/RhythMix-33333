package com.example.RhythMix.classes

import java.io.File
open class Sound(open var title: String, open var file: Int) {
    // TODO
}
class Mix(var track: Track, var start: Float)
class Song(
    override var title: String,
    var sounds: MutableList<Mix> = mutableListOf(),
    override var file: Int
): Sound(title, file)
class Track(
    override var title: String,
    override var file: Int
): Sound(title, file)