//package com.example.RhythMix;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//
//public class MyDatabaseHelper extends SQLiteOpenHelper {
//
//    private Context context;
//    private static final String DATABASE_NAME = "MusicLibrary.db";
//    private static final int DATABASE_VERSION = 1;
//
//    private static final String TABLE_NAME = "my_library";
//    private static final String COLUMN_ID= "_id";
//    private static final String COLUMN_TITLE = "song_title";
//    private static final String COLUMN_AUTHOR = "song_author";
//
//    private static final String COLUMN_TRACK = "track_author";
//
//
//    public MyDatabaseHelper(@Nullable Context context) {
//
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.context = context;
//
//        //companion object{
//
//
//
//    }
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        Log.d("calling onCreate","onCreate()");
//
//        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID +
//                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_TITLE + " TEXT, " +
//                COLUMN_AUTHOR + " TEXT, " +
//                COLUMN_TRACK + " TEXT);";
//        db.execSQL(query);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        Log.d("calling onUpgrade","onUpgrade()");
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
//    }
//
//    public void addSong(String title, String author, String track){
//        Log.d("calling addSong","addSong()");
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_TITLE,title);
//        cv.put(COLUMN_AUTHOR,author);
//        cv.put(COLUMN_TRACK,track);
//
//        long result = db.insert(TABLE_NAME, null, cv);
//        if(result == -1){
//            Toast.makeText(context, "failed",Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(context, "success",Toast.LENGTH_SHORT).show();
//        }
//    }
//}
