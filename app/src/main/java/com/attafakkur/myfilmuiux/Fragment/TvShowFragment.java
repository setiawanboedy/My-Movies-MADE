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
public class TvShowFragment extends Fragment {


    public TvShowFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private ArrayList<MyMovies> listFilm = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, parent, false);

        recyclerView = view.findViewById(R.id.recyclertvshow);
        recyclerView.setHasFixedSize(true);

        listFilm.addAll(getListtvShow());
        showMyRecyclerView();

        return view;
    }

    public ArrayList<MyMovies> getListtvShow() {
        String[] dataTitle = getResources().getStringArray(R.array.data_title);
        String[] dataDesc = getResources().getStringArray(R.array.data_desc);
        String dataLanguage = getResources().getString(R.string.data_language);
        String[] dataRuntime = getResources().getStringArray(R.array.data_durationtv);
        String[] dataGenre = getResources().getStringArray(R.array.data_genretv);
        String[] dataSeason = getResources().getStringArray(R.array.data_seasontv);
        String[] dataImage = getResources().getStringArray(R.array.data_image);
        String string = getResources().getString(R.string.season);

        ArrayList<MyMovies> list = new ArrayList<>();
        for (int i = 0; i < dataTitle.length; i++) {
            MyMovies film = new MyMovies();
            film.setTitle(dataTitle[i]);
            film.setDescription(dataDesc[i]);
            film.setLanguage(dataLanguage);
            film.setRuntime(dataRuntime[i]);
            film.setGenre(dataGenre[i]);
            film.setDate(dataSeason[i]);
            film.setImage(dataImage[i]);
            film.setSeason(string);
            list.add(film);
        }
        return list;
    }

    private void showMyRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        AdapterMyMovies listtvShowAdapter = new AdapterMyMovies(this.getContext());
        listtvShowAdapter.setListFilm(listFilm);
        recyclerView.setAdapter(listtvShowAdapter);
    }
}
