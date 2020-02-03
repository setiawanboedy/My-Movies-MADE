package com.attafakkur.myfilmstorage.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.attafakkur.myfilmstorage.BuildConfig;
import com.attafakkur.myfilmstorage.entity.MyTvShow;
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
public class TvShowViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<MyTvShow>> listtvShow = new MutableLiveData<>();

    public LiveData<ArrayList<MyTvShow>> getTvShow() {
        return listtvShow;
    }

    public void setTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MyTvShow> listItem = new ArrayList<>();
        String API_KEY = BuildConfig.MovieApi;
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject movie = array.getJSONObject(i);
                        MyTvShow tvShowitem = new MyTvShow();
                        tvShowitem.setId(movie.getInt("id"));
                        tvShowitem.setImage(movie.getString("poster_path"));
                        tvShowitem.setTitle(movie.getString("name"));
                        tvShowitem.setDate(movie.getString("first_air_date"));
                        tvShowitem.setLanguage(movie.getString("original_language"));
                        tvShowitem.setPopularity(movie.getString("popularity"));
                        tvShowitem.setDescription(movie.getString("overview"));
                        tvShowitem.setVoteCount(movie.getString("vote_count"));

                        listItem.add(tvShowitem);
                    }
                    listtvShow.postValue(listItem);

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
