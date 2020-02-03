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
import com.attafakkur.myfilmfinal.adapter.AdapterMyMovies;
import com.attafakkur.myfilmfinal.model.MoviesViewModel;
import com.attafakkur.myfilmfinal.model.SearchMovieViewModel;

import java.util.Objects;

/**
 * @author geek-dev
 */
public class MoviesFragment extends Fragment {

    private ProgressBar progressBar;
    private AdapterMyMovies adapter;
    private RecyclerView recyclerView;

    public MoviesFragment() {
        // Required empty public constructor
    }
    private SearchView.OnQueryTextListener queryTextListener;
    private SearchView searchView = null;


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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_bar, menu);
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
                    showSearchRecyclerView(query);
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

    private void showSearchRecyclerView(String query) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SearchMovieViewModel searchMovieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchMovieViewModel.class);
        searchMovieViewModel.getSearchMovies().observe(getViewLifecycleOwner(), myMovies -> {

            if (myMovies != null) {
                adapter.setData(myMovies);
            }
            showLoading(false);
        });

        showLoading(true);

        searchMovieViewModel.setMovieName(query);
        searchMovieViewModel.setSearchMovies();
        adapter = new AdapterMyMovies();
        adapter.notifyDataSetChanged();
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
