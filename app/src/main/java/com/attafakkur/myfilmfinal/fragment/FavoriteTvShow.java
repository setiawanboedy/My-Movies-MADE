package com.attafakkur.myfilmfinal.fragment;


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

import com.attafakkur.myfilmfinal.R;
import com.attafakkur.myfilmfinal.adapter.AdapterTvShowFavorite;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import static com.attafakkur.myfilmfinal.db.DatabaseContract.TvShowColumns.CONTENT_URI_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShow extends Fragment {
    private RecyclerView rvTvFavorite;
    private ProgressBar progressBar;
    private AdapterTvShowFavorite tvShowFavoriteAdapter;
    private Cursor cursor;

    public FavoriteTvShow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBarTvShow);

        tvShowFavoriteAdapter = new AdapterTvShowFavorite();
        tvShowFavoriteAdapter.setCursor(cursor);
        tvShowFavoriteAdapter.notifyDataSetChanged();

        rvTvFavorite = view.findViewById(R.id.rv_fav_tv);
        rvTvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvFavorite.setAdapter(tvShowFavoriteAdapter);
        rvTvFavorite.setHasFixedSize(true);

        new LoadNoteAsync().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadNoteAsync().execute();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvTvFavorite, message, Snackbar.LENGTH_SHORT).show();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getContext()).getContentResolver().query(CONTENT_URI_TV, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favorite) {
            super.onPostExecute(favorite);
            progressBar.setVisibility(View.GONE);

            cursor = favorite;

            tvShowFavoriteAdapter.setCursor(cursor);
            tvShowFavoriteAdapter.notifyDataSetChanged();

            if (cursor.getCount() == 0) {
                showSnackbarMessage(getString(R.string.message_tv));
            }
        }

    }

}
