package com.attafakkur.myfilmuiux;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailMoviesActivity extends AppCompatActivity {
    public static final String extra_movies = "extra_movies";

    TextView title, desc,date,language,runtime,genre,season;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);

        title = findViewById(R.id.tv_title);
        date = findViewById(R.id.tv_date);
        desc = findViewById(R.id.tv_description);
        language = findViewById(R.id.tv_language);
        runtime = findViewById(R.id.tv_duration);
        genre = findViewById(R.id.tv_genre);
        season = findViewById(R.id.textView);
        image = findViewById(R.id.img_item_photo);

        MyMovies movies = getIntent().getParcelableExtra(extra_movies);

        title.setText(movies.getTitle());
        desc.setText(movies.getDescription());
        date.setText(movies.getDate());
        language.setText(movies.getLanguage());
        runtime.setText(movies.getRuntime());
        season.setText(movies.getSeason());
        genre.setText(movies.getGenre());

        Glide.with(this)
                .load(movies.getImage())
                .apply(new RequestOptions()).override(136,186)
                .into(image);
    }
}
