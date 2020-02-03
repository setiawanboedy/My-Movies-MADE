package com.attafakkur.myfilmstorage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.attafakkur.myfilmstorage.db.DatabaseContract.MovieColumns.IMAGE;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.MovieColumns.KEY_ID;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.MovieColumns.LANGUAGE;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.MovieColumns.POPULARITY;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.MovieColumns.SYNOPSIS;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.MovieColumns.TABLE_NAME;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.MovieColumns.TITLE;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.MovieColumns.VOTE;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.IMAGE_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.KEY_ID_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.LANGUAGE_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.POPULARITY_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.RELEASE_DATE_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.SYNOPSIS_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.TABLE_NAME_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.TITLE_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.VOTE_TV;

/**
 * @author geek-dev
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db.moviefavorite";

    private static final int DATABASE_VERSION = 1;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cREATETABLEMOVIE = "create table " + TABLE_NAME + " ( " +
                KEY_ID + " integer primary key autoincrement, " +
                TITLE + " text not null, " +
                SYNOPSIS + " text not null, " +
                IMAGE + " text not null, " +
                POPULARITY + " text not null, " +
                LANGUAGE + " text not null, " +
                RELEASE_DATE + " text not null, " +
                VOTE + " text not null " +
                "); ";
        db.execSQL(cREATETABLEMOVIE);

        String cREATETABLETV = "create table " + TABLE_NAME_TV + " ( " +
                KEY_ID_TV + " integer primary key autoincrement, " +
                TITLE_TV + " text not null, " +
                SYNOPSIS_TV + " text not null, " +
                IMAGE_TV + " text not null, " +
                POPULARITY_TV + " text not null, " +
                LANGUAGE_TV + " text not null, " +
                RELEASE_DATE_TV + " text not null, " +
                VOTE_TV + " text not null " +
                "); ";
        db.execSQL(cREATETABLETV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TV);
        onCreate(db);
    }
}
