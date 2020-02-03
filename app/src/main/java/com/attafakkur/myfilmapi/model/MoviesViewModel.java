package com.attafakkur.myfilmapi.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesViewModel extends ViewModel {

    private static final String API_KEY = "a5b7b6baa4f0fe2efccccd5e3b6dc2d4";
    private MutableLiveData<ArrayList<MyMovies>> listMovies = new MutableLiveData<>();

    public void setMovies(final String movies){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MyMovies> listItem = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("results");

                    for (int i=0;i<array.length();i++){
                        JSONObject movie = array.getJSONObject(i);
                        MyMovies moviesitem = new MyMovies();
                        moviesitem.setImage(movie.getString("poster_path"));
                        moviesitem.setTitle(movie.getString("title"));
                        moviesitem.setDate(movie.getString("release_date"));
                        moviesitem.setLanguage(movie.getString("original_language"));
                        moviesitem.setPopularity(movie.getDouble("popularity"));
                        moviesitem.setDescription(movie.getString("overview"));
                        moviesitem.setVoteCount(movie.getInt("vote_count"));


                        listItem.add(moviesitem);
                    }
                    listMovies.postValue(listItem);

                } catch (JSONException e) {
                    Log.d("Exception...","Loading failed");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", "onFailure");
            }
        });
    }
    public LiveData<ArrayList<MyMovies>> getMovies(){
        return listMovies;
    }
}
