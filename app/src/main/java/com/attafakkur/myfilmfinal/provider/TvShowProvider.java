package com.attafakkur.myfilmfinal.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.attafakkur.myfilmfinal.db.TvHelper;

import java.util.Objects;

import static com.attafakkur.myfilmfinal.db.DatabaseContract.AUTHORITY_TV;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.TvShowColumns.CONTENT_URI_TV;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.TvShowColumns.TABLE_NAME_TV;

/**
 * @author geek-dev
 */
public class TvShowProvider extends ContentProvider {
    private static final int FAVORITE_TV_SHOW = 1;
    private static final int FAVORITE_TV_SHOW_ID = 2;
    private static final UriMatcher S_URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        S_URI_MATCHER.addURI(AUTHORITY_TV, TABLE_NAME_TV, FAVORITE_TV_SHOW);
        S_URI_MATCHER.addURI(AUTHORITY_TV,
                TABLE_NAME_TV + "/#",
                FAVORITE_TV_SHOW_ID);
    }
    private TvHelper tvHelper;

    public TvShowProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int deleted;
        if (S_URI_MATCHER.match(uri) == FAVORITE_TV_SHOW_ID) {
            deleted = tvHelper.deleteProvider(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }
        if (deleted > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long added;
        if (S_URI_MATCHER.match(uri) == FAVORITE_TV_SHOW) {
            added = tvHelper.insertProvider(values);
        } else {
            added = 0;
        }
        if (added > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI_TV + "/" + added);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        tvHelper = new TvHelper(getContext());
        tvHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor;
        switch (S_URI_MATCHER.match(uri)) {
            case FAVORITE_TV_SHOW:
                cursor = tvHelper.queryProvider();
                break;
            case FAVORITE_TV_SHOW_ID:
                cursor = tvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int update;
        if (S_URI_MATCHER.match(uri) == FAVORITE_TV_SHOW_ID) {
            update = tvHelper.updateProvider(uri.getLastPathSegment(), values);
        } else {
            update = 0;
        }
        if (update > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return update;
    }
}
