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
import com.attafakkur.myfilmapi.model.MoviesViewModel;
import com.attafakkur.myfilmapi.model.MyMovies;
import com.attafakkur.myfilmapi.R;

import java.util.ArrayList;

import static com.attafakkur.myfilmapi.ui.DetailMoviesActivity.extra_movies;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private ProgressBar progressBar;
    private MoviesViewModel moviesViewModel;
    private AdapterMyMovies adapter;

    public MoviesFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        recyclerView = view.findViewById(R.id.recyclermovies);
        recyclerView.setHasFixedSize(true);
        showMyRecyclerView();

        progressBar = view.findViewById(R.id.progressBar);
        moviesViewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(MoviesViewModel.class);
        moviesViewModel.getMovies().observe(this, new Observer<ArrayList<MyMovies>>() {
            @Override
            public void onChanged(ArrayList<MyMovies> myMovies) {

                if (myMovies != null){
                    adapter.setData(myMovies);
                }
                showLoading(false);
            }
        });

        moviesViewModel.setMovies(extra_movies);

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


    private void showMyRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterMyMovies();
        recyclerView.setAdapter(adapter);

    }
}
