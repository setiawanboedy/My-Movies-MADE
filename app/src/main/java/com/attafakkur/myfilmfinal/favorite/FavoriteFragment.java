package com.attafakkur.myfilmfinal.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.attafakkur.myfilmfinal.R;
import com.google.android.material.tabs.TabLayout;

public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        FavoriteTab adapterTabLayout = new FavoriteTab(getContext(), getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapterTabLayout);
        TabLayout tab = view.findViewById(R.id.tabs);
        tab.setupWithViewPager(viewPager);

        getChildFragmentManager().beginTransaction();
        return view;
    }

}
