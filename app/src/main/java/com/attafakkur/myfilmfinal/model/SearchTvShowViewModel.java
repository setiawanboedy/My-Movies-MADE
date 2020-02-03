package com.attafakkur.myfilmfinal.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.attafakkur.myfilmfinal.BuildConfig;
import com.attafakkur.myfilmfinal.entity.MyTvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * @author geek-dev
 * @date 1/23/20
 */
public class SearchTvShowViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<MyTvShow>> listtvShow = new MutableLiveData<>();
    public LiveData<ArrayList<MyTvShow>> getSearchTvShow() {
        return listtvShow;
    }

    private String tvShowName;
    public void setTvShowName(String tvShowName){
        this.tvShowName = tvShowName;
    }

    public void setSearchTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MyTvShow> listItem = new ArrayList<>();
        String API_KEY = BuildConfig.MovieApi;
        String url = "https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language=en-US&query="+tvShowName;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject movie = array.getJSONObject(i);
                        MyTvShow tvShowItem = new MyTvShow();
                        tvShowItem.setId(movie.getInt("id"));
                        tvShowItem.setImage(movie.getString("poster_path"));
                        tvShowItem.setTitle(movie.getString("name"));
                        tvShowItem.setDate(movie.getString("first_air_date"));
                        tvShowItem.setLanguage(movie.getString("original_language"));
                        tvShowItem.setPopularity(movie.getString("popularity"));
                        tvShowItem.setDescription(movie.getString("overview"));
                        tvShowItem.setVoteCount(movie.getString("vote_count"));

                        listItem.add(tvShowItem);
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
