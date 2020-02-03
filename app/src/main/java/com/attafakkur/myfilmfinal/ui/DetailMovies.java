package com.attafakkur.myfilmfinal.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.attafakkur.myfilmfinal.BuildConfig;
import com.attafakkur.myfilmfinal.R;
import com.attafakkur.myfilmfinal.adapter.AdapterRecommendation;
import com.attafakkur.myfilmfinal.db.MovieHelper;
import com.attafakkur.myfilmfinal.entity.MyMovies;
import com.attafakkur.myfilmfinal.model.RecommendationViewModel;
import com.attafakkur.myfilmfinal.widget.ImageWidget;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import static com.attafakkur.myfilmfinal.db.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.MovieColumns.IMAGE;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.MovieColumns.KEY_ID;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.MovieColumns.LANGUAGE;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.MovieColumns.POPULARITY;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.MovieColumns.SYNOPSIS;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.MovieColumns.TITLE;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.MovieColumns.VOTE;

/**
 * @author geek-dev
 */
public class DetailMovies extends AppCompatActivity implements Runnable {
    public static final String EXTRA_MOVIES = "EXTRA_MOVIES";

    private TextView title, date, desc, language, popular, voteCount;
    private ImageView image;
    private ProgressBar progressBar;

    private AdapterRecommendation adapter;
    private RecyclerView recyclerRecommend;
    private Handler handler;
    private Boolean isFavorite = false;
    private FloatingActionButton btnLike;
    private MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        title = findViewById(R.id.tv_title);
        date = findViewById(R.id.tv_date);
        desc = findViewById(R.id.tv_description);
        language = findViewById(R.id.tv_language);
        popular = findViewById(R.id.tv_duration);
        voteCount = findViewById(R.id.tv_genre);
        image = findViewById(R.id.img_item_photo);
        progressBar = findViewById(R.id.progressBar);

        btnLike = findViewById(R.id.favorite);
        progressBar.setVisibility(View.VISIBLE);
        handler = new Handler();

        run();
        recyclerRecommendations();
    }

    private void recyclerRecommendations() {
        recyclerRecommend = findViewById(R.id.recyclerrecomend);
        recyclerRecommend.setHasFixedSize(true);
        showMyRecyclerView();

        progressBar = findViewById(R.id.progressBar);
        RecommendationViewModel recommendationViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(RecommendationViewModel.class);
        recommendationViewModel.getRecommend().observe(this, myMovies -> {

            if (myMovies != null) {
                adapter.setMovieRecommend(myMovies);
            }
            showLoading(false);

        });

        recommendationViewModel.setRecommend();

        showLoading(true);
    }

    private void showLoading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showMyRecyclerView() {

        adapter = new AdapterRecommendation();
        adapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerRecommend.setLayoutManager(linearLayoutManager);
        recyclerRecommend.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movieHelper != null) {
            movieHelper.close();
        }
    }

    private void setFavorite() {
        if (isFavorite) {
            btnLike.setImageResource(R.drawable.ic_color);
        } else {
            btnLike.setImageResource(R.drawable.ic_border);
        }
    }

    private void dataSQLite() {
        MyMovies myMovies = getIntent().getParcelableExtra(EXTRA_MOVIES);
        movieHelper = new MovieHelper(this);
        movieHelper.open();

        Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI + "/" + Objects.requireNonNull(myMovies).getId()), null,
                null,
                null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                isFavorite = true;
            }
            cursor.close();
        }
        setFavorite();
    }

    private void favoriteRemove() {
        MyMovies myMovies = getIntent().getParcelableExtra(EXTRA_MOVIES);
        getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + Objects.requireNonNull(myMovies).getId()), null,
                null);
        updateWidget(this);
    }

    private void favoriteSave() {
        MyMovies myMovies = getIntent().getParcelableExtra(EXTRA_MOVIES);
        ContentValues values = new ContentValues();
        values.put(KEY_ID, Objects.requireNonNull(myMovies).getId());
        values.put(TITLE, myMovies.getTitle());
        values.put(IMAGE, myMovies.getImage());
        values.put(SYNOPSIS, myMovies.getDescription());
        values.put(POPULARITY, myMovies.getPopularity());
        values.put(VOTE, myMovies.getVoteCount());
        values.put(LANGUAGE, myMovies.getLanguage());
        values.put(RELEASE_DATE, myMovies.getDate());
        getContentResolver().insert(CONTENT_URI, values);
        updateWidget(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.d("Interupted", "Interupted");
        }
        handler.post(() -> {
            MyMovies movies = getIntent().getParcelableExtra(EXTRA_MOVIES);
            String imageUrl = BuildConfig.IMAGE;
            String urlImage = imageUrl + Objects.requireNonNull(movies).getImage();

            title.setText(movies.getTitle());
            desc.setText(movies.getDescription());
            date.setText(movies.getDate());
            language.setText(movies.getLanguage());
            popular.setText(movies.getPopularity());
            voteCount.setText(movies.getVoteCount());

            Glide.with(DetailMovies.this)
                    .load(urlImage)
                    .apply(new RequestOptions()).override(136, 186)
                    .placeholder(R.drawable.loading)
                    .error(R.mipmap.ic_launcher_error)
                    .into(image);

            dataSQLite();
            btnLike.setOnClickListener((v) -> {
                if (isFavorite) {
                    favoriteRemove();
                    showSnackbarMessage(getResources().getString(R.string.remove));
                } else {
                    favoriteSave();
                    showSnackbarMessage(getResources().getString(R.string.add));
                }
                isFavorite = !isFavorite;
                setFavorite();
            });

            progressBar.setVisibility(View.GONE);
        });

    }
    private static void updateWidget(Context context){
        Intent intent = new Intent(context, ImageWidget.class);
        intent.setAction(ImageWidget.UPDATE);
        context.sendBroadcast(intent);
    }
    private void showSnackbarMessage(String message) {
        Snackbar.make(btnLike, message, Snackbar.LENGTH_SHORT).show();
    }
}

