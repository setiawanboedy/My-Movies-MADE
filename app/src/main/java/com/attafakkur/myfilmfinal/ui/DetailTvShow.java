package com.attafakkur.myfilmfinal.ui;

import android.content.ContentValues;
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
import com.attafakkur.myfilmfinal.db.TvHelper;
import com.attafakkur.myfilmfinal.entity.MyTvShow;
import com.attafakkur.myfilmfinal.model.RecommendationViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import static com.attafakkur.myfilmfinal.db.DatabaseContract.TvShowColumns.CONTENT_URI_TV;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.TvShowColumns.IMAGE_TV;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.TvShowColumns.KEY_ID_TV;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.TvShowColumns.LANGUAGE_TV;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.TvShowColumns.POPULARITY_TV;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.TvShowColumns.RELEASE_DATE_TV;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.TvShowColumns.SYNOPSIS_TV;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.TvShowColumns.TITLE_TV;
import static com.attafakkur.myfilmfinal.db.DatabaseContract.TvShowColumns.VOTE_TV;

/**
 * @author geek-dev
 */
public class DetailTvShow extends AppCompatActivity implements Runnable {

    public static final String EXTRA_MOVIES = "EXTRA_TVSHOW";

    private TextView title, date, desc, language, popular, voteCount;
    private ImageView image;
    private ProgressBar progressBar;

    private AdapterRecommendation adapter;
    private RecyclerView recyclerRecomend;
    private Handler handler;
    private Boolean isFavorite = false;
    private FloatingActionButton btnLike;
    private TvHelper tvHelper;

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
        recyclerRecomend = findViewById(R.id.recyclerrecomend);
        recyclerRecomend.setHasFixedSize(true);
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
        recyclerRecomend.setLayoutManager(linearLayoutManager);
        recyclerRecomend.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tvHelper != null) {
            tvHelper.close();
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
        MyTvShow tvShow = getIntent().getParcelableExtra(EXTRA_MOVIES);
        tvHelper = new TvHelper(this);
        tvHelper.open();

        Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI_TV + "/" + Objects.requireNonNull(tvShow).getId()), null,
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
        MyTvShow tvShow = getIntent().getParcelableExtra(EXTRA_MOVIES);
        getContentResolver().delete(Uri.parse(CONTENT_URI_TV + "/" + Objects.requireNonNull(tvShow).getId()), null,
                null);

    }

    private void favoriteSave() {
        MyTvShow tvShow = getIntent().getParcelableExtra(EXTRA_MOVIES);
        ContentValues values = new ContentValues();
        values.put(KEY_ID_TV, Objects.requireNonNull(tvShow).getId());
        values.put(TITLE_TV, tvShow.getTitle());
        values.put(IMAGE_TV, tvShow.getImage());
        values.put(SYNOPSIS_TV, tvShow.getDescription());
        values.put(POPULARITY_TV, tvShow.getPopularity());
        values.put(VOTE_TV, tvShow.getVoteCount());
        values.put(LANGUAGE_TV, tvShow.getLanguage());
        values.put(RELEASE_DATE_TV, tvShow.getDate());
        getContentResolver().insert(CONTENT_URI_TV, values);

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
            MyTvShow tvShow = getIntent().getParcelableExtra(EXTRA_MOVIES);
            String imageUrl = BuildConfig.IMAGE;
            String urlImage = imageUrl + Objects.requireNonNull(tvShow).getImage();

            title.setText(tvShow.getTitle());
            desc.setText(tvShow.getDescription());
            date.setText(tvShow.getDate());
            language.setText(tvShow.getLanguage());
            popular.setText(tvShow.getPopularity());
            voteCount.setText(tvShow.getVoteCount());

            Glide.with(DetailTvShow.this)
                    .load(urlImage)
                    .apply(new RequestOptions()).override(136, 186)
                    .placeholder(R.drawable.loading)
                    .error(R.mipmap.ic_launcher_error)
                    .into(image);

            dataSQLite();
            btnLike.setOnClickListener((v) -> {
                if (isFavorite) {
                    favoriteRemove();
                } else {
                    favoriteSave();
                }
                isFavorite = !isFavorite;
                setFavorite();

            });

            progressBar.setVisibility(View.GONE);
        });
    }
}
