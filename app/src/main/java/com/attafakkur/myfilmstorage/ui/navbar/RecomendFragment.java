package com.attafakkur.myfilmstorage.ui.navbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.attafakkur.myfilmstorage.R;
import com.attafakkur.myfilmstorage.adapter.AdapterMyMovies;
import com.attafakkur.myfilmstorage.model.RecomendationViewModel;

/**
 * @author geek-dev
 */
public class RecomendFragment extends Fragment {
    private ProgressBar progressBar;

    private AdapterMyMovies adapter;
    private RecyclerView recyclerRecomend;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recomend, container, false);
        recyclerRecomend = root.findViewById(R.id.recyclerreco);

        showMyRecyclerView();

        progressBar = root.findViewById(R.id.progressBar);
        RecomendationViewModel moviesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(RecomendationViewModel.class);
        moviesViewModel.getRecomend().observe(getViewLifecycleOwner(), myMovies -> {

            if (myMovies != null) {
                adapter.setData(myMovies);
            }
            showLoading(false);
        });

        moviesViewModel.setRecomend();

        showLoading(true);
        return root;
    }

    private void showLoading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showMyRecyclerView() {
        recyclerRecomend.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterMyMovies();
        recyclerRecomend.setAdapter(adapter);

    }

}