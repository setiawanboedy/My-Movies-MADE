package com.attafakkur.myfavoritemovie.favorite;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.attafakkur.myfavoritemovie.R;

/**
 * @author geek-dev
 * @date 1/28/20
 */
public class TabFavorite extends FragmentPagerAdapter {

    private final Context context;
    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2
    };

    public TabFavorite(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new MovieFavorite();
            case 1:
                return new TvShowFavorite();
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
