package com.attafakkur.myfilmfinal.ui.navbar;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.attafakkur.myfilmfinal.R;
import com.attafakkur.myfilmfinal.adapter.AdapterTabLayout;
import com.google.android.material.tabs.TabLayout;

/**
 * @author geek-dev
 */
public class HomeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        AdapterTabLayout adapterTabLayout = new AdapterTabLayout(getContext(), getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapterTabLayout);
        TabLayout tab = root.findViewById(R.id.tabs);
        tab.setupWithViewPager(viewPager);

        getChildFragmentManager().beginTransaction();

        return root;
    }
}