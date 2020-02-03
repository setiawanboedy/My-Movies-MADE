package com.attafakkur.myfilmfinal.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.attafakkur.myfilmfinal.R;
import com.attafakkur.myfilmfinal.adapter.AdapterMyTvShow;
import com.attafakkur.myfilmfinal.model.SearchTvShowViewModel;
import com.attafakkur.myfilmfinal.model.TvShowViewModel;

import java.util.Objects;


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
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;


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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_bar,menu);
        MenuItem menuItem = menu.findItem(R.id.search_movie);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if (menuItem != null){
            searchView = (SearchView) menuItem.getActionView();
        }
        if (searchView != null){
            searchView.setSearchableInfo(Objects.requireNonNull(searchManager).getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchTvShowRecyclerView(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchTvShowRecyclerView(String query) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SearchTvShowViewModel searchTvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchTvShowViewModel.class);
        searchTvShowViewModel.getSearchTvShow().observe(getViewLifecycleOwner(), myMovies -> {
            if (myMovies != null) {
                adapter.setData(myMovies);
            }
            showLoading(false);
        });
        searchTvShowViewModel.setSearchTvShow();
        showLoading(true);

        searchTvShowViewModel.setTvShowName(query);
        searchTvShowViewModel.setSearchTvShow();
        adapter = new AdapterMyTvShow();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_movie){
            return false;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
}
