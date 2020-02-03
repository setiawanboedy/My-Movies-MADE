package com.attafakkur.myfavoritemovie;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.attafakkur.myfavoritemovie.favorite.TabFavorite;
import com.google.android.material.tabs.TabLayout;

/**
 * @author geek-dev
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabFavorite tabFavorite = new TabFavorite(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(tabFavorite);
        TabLayout tab = findViewById(R.id.tabs);
        tab.setupWithViewPager(viewPager);

        getSupportFragmentManager().beginTransaction();

    }
}
