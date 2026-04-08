package com.priyansh.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String dbname="MoviesDB";
    public DBHelper(Context context){
        super(context,dbname,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table movies(" +
                "id integer primary key autoincrement,"+
                "movie_name text," +
                "score integer, " +
                "review text, " +
                "year integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists movies");
        onCreate(db);
    }

    public Cursor getAllMovies() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM movies", null);
    }

    public void deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("movies", "id=?", new String[]{String.valueOf(id)});
        // Removed db.close() to keep connection alive for Inspector
    }
}
