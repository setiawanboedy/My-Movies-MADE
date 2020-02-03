package com.attafakkur.myfilmapi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.attafakkur.myfilmapi.R;
import com.attafakkur.myfilmapi.adapter.AdapterRecomendation;
import com.attafakkur.myfilmapi.model.MyMovies;
import com.attafakkur.myfilmapi.model.RecomendationViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class DetailMoviesActivity extends AppCompatActivity {
    public static final String extra_movies = "extra_movies";

    private TextView title, desc,date,language,popular,voteCount;
    private ImageView image;
    private ProgressBar progressBar;

    private AdapterRecomendation adapter;
    private RecyclerView recyclerRecomend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);

        title = findViewById(R.id.tv_title);
        date = findViewById(R.id.tv_date);
        desc = findViewById(R.id.tv_description);
        language = findViewById(R.id.tv_language);
        popular = findViewById(R.id.tv_duration);
        voteCount = findViewById(R.id.tv_genre);
        image = findViewById(R.id.img_item_photo);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.d("Interupted","Interupted");
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyMovies movies = getIntent().getParcelableExtra(extra_movies);
                        String popularity = Double.toString(movies.getPopularity());
                        String vote = Integer.toString(movies.getVoteCount());
                        String url_image = "https://image.tmdb.org/t/p/w185"+ movies.getImage();

                        title.setText(movies.getTitle());
                        desc.setText(movies.getDescription());
                        date.setText(movies.getDate());
                        language.setText(movies.getLanguage());
                        popular.setText(popularity);
                        voteCount.setText(vote);


                        Glide.with(DetailMoviesActivity.this)
                                .load(url_image)
                                .apply(new RequestOptions()).override(136,186)
                                .into(image);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
        RecyclerRecomendation();

    }
    private void RecyclerRecomendation(){
        recyclerRecomend = findViewById(R.id.recyclerrecomend);
        recyclerRecomend.setHasFixedSize(true);
        showMyRecyclerView();

        progressBar = findViewById(R.id.progressBar);
        RecomendationViewModel movieRecomendation = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(RecomendationViewModel.class);
        movieRecomendation.getRecomend().observe(this, new Observer<ArrayList<MyMovies>>() {
            @Override
            public void onChanged(ArrayList<MyMovies> myMovies) {

                if (myMovies != null){
                    adapter.setMovieRecomend(myMovies);
                }
                showLoading(false);

            }
        });

        movieRecomendation.setRecomend(extra_movies);

        showLoading(true);
    }

    private void showLoading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showMyRecyclerView(){

        adapter = new AdapterRecomendation();
        adapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerRecomend.setLayoutManager(linearLayoutManager);
        recyclerRecomend.setAdapter(adapter);

    }
}

