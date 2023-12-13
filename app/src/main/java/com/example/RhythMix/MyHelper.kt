package com.example.RhythMix

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.RhythMix.classes.Track

//Track database

class MyHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val context: Context = context

    companion object {
        private const val DATABASE_NAME = "TrackLibrary.db"
        private const val DATABASE_VERSION = 8
        private const val TABLE_NAME = "my_tracks"
        private const val COLUMN_ID = "song_id"
        private const val COLUMN_TITLE = "song_title"
        private const val COLUMN_START = "song_start"
        private const val COLUMN_LOOPS = "song_loops"
        private const val COLUMN_VOLUME = "song_volume"
        private const val COLUMN_AUDIO = "song_audio"
    }

    init {
        Log.d("MyHelper", "Database Created/Opened")
    }
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("calling onCreate", "starting to created table")

        val query = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT NOT NULL, " +
                "$COLUMN_START INTEGER NOT NULL, " +
                "$COLUMN_LOOPS INTEGER NOT NULL, " +
                "$COLUMN_VOLUME INTEGER NOT NULL, " +
                "$COLUMN_AUDIO BLOB NOT NULL);"
        db.execSQL(query)
        Log.d(" onCreate", "successfully made track table")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d("calling onUpgrade", "onUpgrade()")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addTrack(title: String, start: Int, loops: Int, volume: Float, audio: ByteArray) {
        Log.d("MyHelper", "Adding track to db")
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_START, start)
            put(COLUMN_LOOPS, loops)
            put(COLUMN_VOLUME, volume)
            put(COLUMN_AUDIO, audio)
        }


        val result = db.insert(TABLE_NAME, null, cv)
        Log.d("MyHelper", "added track to db")
        if (result == -1L) {
            Log.d("addTrack()","failed to add track")
        } else {
            Log.d("addTrack()","added track successfully")
        }
    }

    fun deleteTrack(trackId : Int){
        val db = this.writableDatabase
        val clause = "$COLUMN_ID = ?"
        val args = arrayOf(trackId.toString())

        val result = db.delete(TABLE_NAME, clause, args)
        if (result == -1) {

            Log.d("deleteTrack()","failed to delete track")

        } else {
            Log.d("deleteTrack()","deleted track successfully")
        }
    }

    @SuppressLint("Range")
    fun getAllTracks(): List<Track> {
        val trackList = mutableListOf<Track>()
        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val trackId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID)).toString()
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)).toInt()
                val start = cursor.getString(cursor.getColumnIndex(COLUMN_START)).toInt()
                val loops = cursor.getString(cursor.getColumnIndex(COLUMN_LOOPS)).toInt()
                val volume = cursor.getString(cursor.getColumnIndex(COLUMN_VOLUME)).toFloat()
                val audio = cursor.getBlob(cursor.getColumnIndex(COLUMN_AUDIO))
                val trackObject = Track(trackId, title, start, loops, volume, audio)
                trackList.add(trackObject)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return trackList
    }
}

