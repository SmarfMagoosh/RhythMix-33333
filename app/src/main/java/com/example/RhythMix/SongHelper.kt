package com.example.RhythMix

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

//Song database
class SongHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val context: Context = context

    companion object {
        private const val DATABASE_NAME = "SongLibrary.db"
        private const val DATABASE_VERSION = 6

        private const val TABLE_NAME = "my_songs"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "song_title"
        private const val COLUMN_TRACKS = "song_tracks"
        private const val COLUMN_TEMPO = "song_tempo"
    }

    init {
        Log.d("SongHelper", "Database Created/Opened")
    }
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("calling onCreate", "onCreate()")

        val query = "CREATE TABLE my_songs ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT NOT NULL, " +
                "$COLUMN_TRACKS TEXT NOT NULL, " +
                "$COLUMN_TEMPO TEXT NOT NULL);"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d("calling onUpgrade", "onUpgrade()")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addSong(title: String, track: List<String>, tempo: Int) {

        val db = this.writableDatabase
        //al cv = ContentValues()
        val cv = ContentValues().apply {
            put(COLUMN_TITLE, title)
          //  put(COLUMN_TRACKS, track)
            put(COLUMN_TEMPO, tempo)
        }


        val result = db.insert(TABLE_NAME, null, cv)
        Log.d("SongHelper", "added song to db")
        if (result == -1L) {
            Log.d("addSong()","failed to add song")
        } else {
            Log.d("addSong()","added song successfully")
        }
    }

    fun deleteSong(trackId : Int){
        val db = this.writableDatabase
        val clause = "$COLUMN_ID = ?"
        val args = arrayOf(trackId.toString())

        val result = db.delete(TABLE_NAME, clause, args)
        if (result == -1) {
            Log.d("deleteSong()","failed to delete song")

        } else {
            // Track deleted successfully
            Log.d("deleteSong()","deleted song successfully")
        }
    }


}