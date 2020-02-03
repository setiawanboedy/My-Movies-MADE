package com.attafakkur.myfilmfinal.adapter;

import android.content.Context;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.attafakkur.myfilmfinal.R;
import com.attafakkur.myfilmfinal.fragment.MoviesFragment;
import com.attafakkur.myfilmfinal.fragment.TvShowFragment;

import org.jetbrains.annotations.NotNull;

/**
 * @author geek-dev
 */
public class AdapterTabLayout extends FragmentPagerAdapter {
    private final Context context;
    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2
    };

    public AdapterTabLayout(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }


    @NotNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new MoviesFragment();
            case 1:
                return new TvShowFragment();
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

