package com.attafakkur.myfilmuiux.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attafakkur.myfilmuiux.Adapter.AdapterMyMovies;
import com.attafakkur.myfilmuiux.MyMovies;
import com.attafakkur.myfilmuiux.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    public MoviesFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private ArrayList<MyMovies> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        recyclerView = view.findViewById(R.id.recyclermovies);
        recyclerView.setHasFixedSize(true);

        list.addAll(getListMovies());
        showMyRecyclerView();

        return view;
    }
    public ArrayList<MyMovies> getListMovies() {
        String[] dataName = getResources().getStringArray(R.array.data_name);
        String[] dataDescription = getResources().getStringArray(R.array.data_description);
        String[] dataDate = getResources().getStringArray(R.array.data_date);
        String dataLanguage = getResources().getString(R.string.data_language);
        String[] dataRuntime = getResources().getStringArray(R.array.data_duration);
        String[] dataGenre = getResources().getStringArray(R.array.data_genre);
        String[] dataPhoto = getResources().getStringArray(R.array.data_photo);
        String string = getResources().getString(R.string.date_release);

        ArrayList<MyMovies> listFilm = new ArrayList<>();
        for (int i = 0; i < dataName.length; i++) {
            MyMovies film = new MyMovies();
            film.setTitle(dataName[i]);
            film.setDescription(dataDescription[i]);
            film.setDate(dataDate[i]);
            film.setLanguage(dataLanguage);
            film.setRuntime(dataRuntime[i]);
            film.setGenre(dataGenre[i]);
            film.setSeason(string);
            film.setImage(dataPhoto[i]);
            listFilm.add(film);

        }
        return listFilm;
    }

    private void showMyRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        AdapterMyMovies listMoviesAdapter = new AdapterMyMovies(this.getContext());
        listMoviesAdapter.setListFilm(list);
        recyclerView.setAdapter(listMoviesAdapter);

    }
}
