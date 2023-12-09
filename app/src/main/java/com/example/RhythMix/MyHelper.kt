package com.example.RhythMix

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast


class MyHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val context: Context = context

    companion object {
        private const val DATABASE_NAME = "MusicLibrary.db"
        private const val DATABASE_VERSION = 4

        private const val TABLE_NAME = "my_library"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "song_title"
        private const val COLUMN_AUTHOR = "song_author"
        private const val COLUMN_TRACK = "song_track"
        private const val COLUMN_AUDIO = "song_audio"
    }

    init {
        Log.d("MyHelper", "Database Created/Opened")
    }
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("calling onCreate", "onCreate()")

       // val query = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
        //        "$COLUMN_TITLE TEXT, $COLUMN_AUTHOR TEXT, $COLUMN_TRACK TEXT);"
         val query = "CREATE TABLE my_library ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT NOT NULL, " +
                 "$COLUMN_AUTHOR TEXT NOT NULL, " +
                 "$COLUMN_TRACK TEXT NOT NULL, " +
                 "$COLUMN_AUDIO BLOB NOT NULL);"
        db.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d("calling onUpgrade", "onUpgrade()")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addSongWithAudioData(title: String, author: String, track: String, audioData: ByteArray) {
        Log.d("MyHelper", "Adding song with audio data to db")
        val db = this.writableDatabase
       //al cv = ContentValues()
        val cv = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_AUTHOR, author)
            put(COLUMN_TRACK, track)
            put(COLUMN_AUDIO, audioData)
        }


        val result = db.insert(TABLE_NAME, null, cv)
        Log.d("MyHelper", "added song to db")
        if (result == -1L) {
            Toast.makeText(context, "Failed to add song with audio data", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Song with audio data added successfully", Toast.LENGTH_SHORT).show()
        }
    }

}