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
import com.attafakkur.myfilmstorage.adapter.AdapterMyTvShow;
import com.attafakkur.myfilmstorage.model.TvShowViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdapterMyTvShow adapter;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, parent, false);

        recyclerView = view.findViewById(R.id.recyclertvshow);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        showMyRecyclerView();

        TvShowViewModel tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel.class);
        tvShowViewModel.getTvShow().observe(getViewLifecycleOwner(), myMovies -> {
            if (myMovies != null) {
                adapter.setData(myMovies);
            }
            showLoading(false);
        });
        tvShowViewModel.setTvShow();
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
        adapter = new AdapterMyTvShow();
        recyclerView.setAdapter(adapter);
    }
}
