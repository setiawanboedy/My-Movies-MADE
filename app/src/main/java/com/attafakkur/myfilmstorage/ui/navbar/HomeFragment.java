package com.attafakkur.myfilmstorage.ui.navbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.attafakkur.myfilmstorage.R;
import com.attafakkur.myfilmstorage.adapter.AdapterTabLayout;
import com.google.android.material.tabs.TabLayout;

/**
 * @author geek-dev
 */
public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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