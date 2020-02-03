package com.attafakkur.myfilmstorage.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.attafakkur.myfilmstorage.db.TvHelper;

import java.util.Objects;

import static com.attafakkur.myfilmstorage.db.DatabaseContract.AUTHORITY_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.CONTENT_URI_TV;
import static com.attafakkur.myfilmstorage.db.DatabaseContract.TvShowColumns.TABLE_NAME_TV;

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
    public boolean onCreate() {
        tvHelper = new TvHelper(getContext());
        tvHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
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

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long addeed;
        if (S_URI_MATCHER.match(uri) == FAVORITE_TV_SHOW) {
            addeed = tvHelper.insertProvider(values);
        } else {
            addeed = 0;
        }
        if (addeed > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI_TV + "/" + addeed);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
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
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
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
