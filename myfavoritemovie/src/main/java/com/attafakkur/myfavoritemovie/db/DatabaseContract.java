package com.attafakkur.myfavoritemovie.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author geek-dev
 */
public class DatabaseContract {
    private static final String AUTHORITY = "com.attafakkur.myfilmfinal.movie";
    private static final String AUTHORITY_TV = "com.attafakkur.myfilmfinal.tvshow";
    private static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {

        static final String TABLE_NAME = "favorite";


        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static final class TvShowColumns implements BaseColumns {

        static final String TABLE_NAME_TV = "favoritetv";

        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_TV)
                .appendPath(TABLE_NAME_TV)
                .build();
    }
}
