package com.attafakkur.myfilmstorage.favorite;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.attafakkur.myfilmstorage.R;
import com.attafakkur.myfilmstorage.fragment.FavoriteMovies;
import com.attafakkur.myfilmstorage.fragment.FavoriteTvShow;

public class FavoriteTab extends FragmentPagerAdapter {
    private final Context context;
    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2
    };

    FavoriteTab(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FavoriteMovies();
            case 1:
                return new FavoriteTvShow();
            default:
        }
        throw new IllegalStateException();
    }

    @Override
    public CharSequence getPageTitle(int i) {
        return context.getResources().getString(TAB_TITLES[i]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
