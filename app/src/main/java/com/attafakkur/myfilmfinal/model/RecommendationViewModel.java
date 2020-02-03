package com.attafakkur.myfilmfinal.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.attafakkur.myfilmfinal.BuildConfig;
import com.attafakkur.myfilmfinal.entity.MyMovies;
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
public class RecommendationViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<MyMovies>> listMovies = new MutableLiveData<>();

    public LiveData<ArrayList<MyMovies>> getRecommend() {
        return listMovies;
    }

    public void setRecommend() {
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
                        MyMovies moviesItem = new MyMovies();
                        moviesItem.setId(movie.getInt("id"));
                        moviesItem.setImage(movie.getString("poster_path"));
                        moviesItem.setTitle(movie.getString("title"));
                        moviesItem.setDate(movie.getString("release_date"));
                        moviesItem.setLanguage(movie.getString("original_language"));
                        moviesItem.setPopularity(movie.getString("popularity"));
                        moviesItem.setDescription(movie.getString("overview"));
                        moviesItem.setVoteCount(movie.getString("vote_count"));


                        listItem.add(moviesItem);
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
