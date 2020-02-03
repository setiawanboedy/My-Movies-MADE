package com.attafakkur.myfilmstorage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.attafakkur.myfilmstorage.R;
import com.attafakkur.myfilmstorage.adapter.AdapterMyMovies;
import com.attafakkur.myfilmstorage.model.MoviesViewModel;

public class MoviesFragment extends Fragment {

    private ProgressBar progressBar;
    private AdapterMyMovies adapter;
    private RecyclerView recyclerView;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        recyclerView = view.findViewById(R.id.recyclermovies);
        recyclerView.setHasFixedSize(true);
        showMyRecyclerView();

        progressBar = view.findViewById(R.id.progressBar);
        MoviesViewModel moviesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MoviesViewModel.class);
        moviesViewModel.getMovies().observe(getViewLifecycleOwner(), myMovies -> {

            if (myMovies != null) {
                adapter.setData(myMovies);
            }
            showLoading(false);
        });

        moviesViewModel.setMovies();

        showLoading(true);
        return view;
    }

    private void showLoading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showMyRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterMyMovies();
        recyclerView.setAdapter(adapter);

    }

}
