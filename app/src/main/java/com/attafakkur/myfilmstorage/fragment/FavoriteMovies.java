package com.attafakkur.myfilmstorage.fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.attafakkur.myfilmstorage.R;
import com.attafakkur.myfilmstorage.adapter.AdapterMovieFavorite;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import static com.attafakkur.myfilmstorage.db.DatabaseContract.MovieColumns.CONTENT_URI;

/**
 * @author geek-dev
 */
public class FavoriteMovies extends Fragment {

    private ProgressBar progressBar;
    private AdapterMovieFavorite adapterMovieFavorite;
    private Cursor cursor;
    private RecyclerView rvMovieFavorite;

    public FavoriteMovies() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBarMovie);

        adapterMovieFavorite = new AdapterMovieFavorite();
        adapterMovieFavorite.setCursor(cursor);
        adapterMovieFavorite.notifyDataSetChanged();

        rvMovieFavorite = view.findViewById(R.id.rv_fav_movie);
        rvMovieFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovieFavorite.setAdapter(adapterMovieFavorite);
        rvMovieFavorite.setHasFixedSize(true);

        new LoadNoteAsync().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadNoteAsync().execute();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvMovieFavorite, message, Snackbar.LENGTH_SHORT).show();
    }

    private void showLoading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading(true);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getContext()).getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favorite) {
            super.onPostExecute(favorite);

            showLoading(false);
            cursor = favorite;

            adapterMovieFavorite.setCursor(cursor);
            adapterMovieFavorite.notifyDataSetChanged();

            if (cursor.getCount() == 0) {
                showSnackbarMessage(getString(R.string.message));
            }

        }
    }

}
