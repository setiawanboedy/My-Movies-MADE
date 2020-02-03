package com.attafakkur.myfilmstorage.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.attafakkur.myfilmstorage.movie";
    public static final String AUTHORITY_TV = "com.attafakkur.myfilmstorage.tvshow";
    private static final String SCHEME = "content";

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static final class MovieColumns implements BaseColumns {

        public static final String TABLE_NAME = "favorite";

        public static final String KEY_ID = "_id";
        public static final String IMAGE = "image";
        public static final String TITLE = "title";
        public static final String RELEASE_DATE = "date";
        public static final String VOTE = "voteaverage";
        public static final String POPULARITY = "popularity";
        public static final String LANGUAGE = "language";
        public static final String SYNOPSIS = "synopsis";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static final class TvShowColumns implements BaseColumns {

        public static final String TABLE_NAME_TV = "favoritetv";

        public static final String KEY_ID_TV = "_id";
        public static final String IMAGE_TV = "image";
        public static final String TITLE_TV = "title";
        public static final String RELEASE_DATE_TV = "date";
        public static final String VOTE_TV = "voteaverage";
        public static final String POPULARITY_TV = "popularity";
        public static final String LANGUAGE_TV = "language";
        public static final String SYNOPSIS_TV = "synopsis";

        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_TV)
                .appendPath(TABLE_NAME_TV)
                .build();
    }
}
