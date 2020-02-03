package com.attafakkur.myfilmstorage.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.attafakkur.myfilmstorage.BuildConfig;
import com.attafakkur.myfilmstorage.entity.MyMovies;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * @author geek-dev
 */
public class RecomendationViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<MyMovies>> listMovies = new MutableLiveData<>();

    public LiveData<ArrayList<MyMovies>> getRecomend() {
        return listMovies;
    }

    public void setRecomend() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MyMovies> listItem = new ArrayList<>();
        String API_KEY = BuildConfig.MovieApi;
        String url = "https://api.themoviedb.org/3/movie/3/recommendations?api_key=" + API_KEY + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject movie = array.getJSONObject(i);
                        MyMovies moviesitem = new MyMovies();
                        moviesitem.setId(movie.getInt("id"));
                        moviesitem.setImage(movie.getString("poster_path"));
                        moviesitem.setTitle(movie.getString("title"));
                        moviesitem.setDate(movie.getString("release_date"));
                        moviesitem.setLanguage(movie.getString("original_language"));
                        moviesitem.setPopularity(movie.getString("popularity"));
                        moviesitem.setDescription(movie.getString("overview"));
                        moviesitem.setVoteCount(movie.getString("vote_count"));


                        listItem.add(moviesitem);
                    }
                    listMovies.postValue(listItem);

                } catch (JSONException e) {
                    Log.d("Exception...", "Load failed");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", "onFailure");
            }
        });
    }
}
