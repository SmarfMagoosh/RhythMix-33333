package com.example.RhythMix

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast


class SongHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val context: Context = context

    companion object {
        private const val DATABASE_NAME = "SongLibrary.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "my_songs"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "song_title"
        private const val COLUMN_AUTHOR = "song_author"
        private const val COLUMN_TRACKS = "song_tracks"
        private const val COLUMN_TEMPO = "song_tempo"
      //  private const val COLUMN_LOOPNUM = "song_loopnum"
       // private const val COLUMN_STARTTIME = "song_starttime"

        //times looped
        //list of tracks
        //start time
    }

    init {
        Log.d("SongHelper", "Database Created/Opened")
    }
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("calling onCreate", "onCreate()")

        val query = "CREATE TABLE my_songs ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT NOT NULL, " +
                "$COLUMN_AUTHOR TEXT NOT NULL, " +
                "$COLUMN_TEMPO TEXT NOT NULL, " +
                "$COLUMN_TRACKS TEXT NOT NULL);"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d("calling onUpgrade", "onUpgrade()")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addSong(title: String,tempo: Int, author: String, track: List<String>) {
        Log.d("SongHelper", "Adding song with audio data to db")
        val db = this.writableDatabase
        //al cv = ContentValues()
        val cv = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_AUTHOR, author)
            put(COLUMN_TEMPO, tempo)
            //put(COLUMN_TRACKS, track)
        }


        val result = db.insert(TABLE_NAME, null, cv)
        Log.d("SongHelper", "added song to db")
        if (result == -1L) {
            Toast.makeText(context, "Failed to add song", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Song added successfully", Toast.LENGTH_SHORT).show()
        }
    }

}