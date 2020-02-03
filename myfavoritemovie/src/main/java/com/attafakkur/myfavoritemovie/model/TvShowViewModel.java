package com.attafakkur.myfavoritemovie.model;

import android.annotation.SuppressLint;
import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.attafakkur.myfavoritemovie.entity.MyMovies;

import java.util.ArrayList;

import static com.attafakkur.myfavoritemovie.db.DatabaseContract.TvShowColumns.CONTENT_URI_TV;

/**
 * @author geek-dev
 * @date 1/28/20
 */
public class TvShowViewModel extends ViewModel {

    private Context context;
    public void getContext(Context context){
        this.context = context;
    }

    private final MutableLiveData<ArrayList<MyMovies>> listMovie = new MutableLiveData<>();
    public void setMovie() {
        Uri uri = Uri.parse("content://com.attafakkur.myfilmfinal.tvshow/favorite");
        @SuppressLint("Recycle")
        ContentProviderClient client = context.getContentResolver().acquireContentProviderClient(uri);

        try {
            ArrayList<MyMovies> arrayList = new ArrayList<>();
            assert client != null;
            Cursor cursor = client.query(CONTENT_URI_TV, null, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                do {
                    MyMovies movies = new MyMovies();
                    movies.setId(Integer.valueOf(cursor.getString(0)));
                    movies.setTitle(cursor.getString(1));
                    movies.setDescription(cursor.getString(2));
                    movies.setImage(cursor.getString(3));
                    movies.setPopularity(cursor.getString(4));
                    movies.setLanguage(cursor.getString(5));
                    movies.setDate(cursor.getString(6));
                    movies.setVoteCount(cursor.getString(7));
                    arrayList.add(movies);
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            }
            cursor.close();
            listMovie.postValue(arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveData<ArrayList<MyMovies>> getMovie() {
        return listMovie;
    }
}
