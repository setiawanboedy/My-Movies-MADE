package com.attafakkur.myfilmapi.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.attafakkur.myfilmapi.adapter.AdapterMyMovies;
import com.attafakkur.myfilmapi.model.MyMovies;
import com.attafakkur.myfilmapi.model.TvShowViewModel;
import com.attafakkur.myfilmapi.R;

import java.util.ArrayList;

import static com.attafakkur.myfilmapi.ui.DetailMoviesActivity.extra_movies;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {


    public TvShowFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private ArrayList<MyMovies> listFilm = new ArrayList<>();
    private ProgressBar progressBar;
    private TvShowViewModel tvShowViewModel;
    private AdapterMyMovies adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, parent, false);

        recyclerView = view.findViewById(R.id.recyclertvshow);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        showMyRecyclerView();

        tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel.class);
        tvShowViewModel.getTvShow().observe(this, new Observer<ArrayList<MyMovies>>() {
            @Override
            public void onChanged(ArrayList<MyMovies> myMovies) {
                if (myMovies != null){
                    adapter.setData(myMovies);
                }
                showLoading(false);
            }
        });
        tvShowViewModel.setTvShow(extra_movies);
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
