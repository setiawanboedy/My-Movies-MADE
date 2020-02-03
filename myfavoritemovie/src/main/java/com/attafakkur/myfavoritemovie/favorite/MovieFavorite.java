package com.attafakkur.myfavoritemovie.favorite;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attafakkur.myfavoritemovie.R;
import com.attafakkur.myfavoritemovie.adapter.AdapterMyMovies;
import com.attafakkur.myfavoritemovie.model.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 * @author geek-dev
 */
public class MovieFavorite extends Fragment {
    private AdapterMyMovies adapter;
    private RecyclerView recyclerView;

    public MovieFavorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_moviefavorite);
        recyclerView.setHasFixedSize(true);
        showRecyclerView();

        MovieViewModel moviesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        moviesViewModel.getContext(getContext());
        moviesViewModel.setMovie();
        moviesViewModel.getMovie().observe(getViewLifecycleOwner(), myMovies -> {
            if (myMovies != null){
                adapter.setData(myMovies);
            }
        });
        moviesViewModel.setMovie();
    }
    private void showRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterMyMovies();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }
}
