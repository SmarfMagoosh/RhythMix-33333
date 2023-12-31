package com.example.RhythMix.classes

/**
 * A trivial parent class that just allows us to reuse code when making cards
 */
open class Sound(open var title: String, open val type: String = "song") {
    val display: String
        get() = if (title.length < 20) title else "${title.take(20)}..."
}

/**
 * A track represents one part of a song such as the vocals, the bassline, etc.
 * @param Title the title of the track
 * @param id the id of the mp3 file in resources
 * @property start the time at which the track should start playing in the song
 * @property loops the number of times which the track should be played once it starts
 * @property volume the volume at which this track should be played
 * @param audioFile the sound made on record screen
 */
data class Track(
    override var title: String,
    var id: Int,
    var start: Int = 0,
    var loops: Int = 0,
    var volume: Float = 1F,
    var audioFile: ByteArray
): Sound(title, "track")

/**
 * A song is a mix of several tracks to form one greater piece of music
 * @param title the names of the song
 * @param tracks the list of tracks that make up the whole song
 */
data class Song(
    override var title: String,
    var tracks: MutableList<Track> = mutableListOf(),
    var tempo: Int = 60
): Sound(title, "song")
