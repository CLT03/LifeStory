package com.vivwe.video.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;




import com.vivwe.video.fragment.MusicLibraryFragment;


import java.util.ArrayList;

public class MusicLibraryFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<MusicLibraryFragment> fragments;

    public MusicLibraryFragmentPagerAdapter(FragmentManager fm, ArrayList<MusicLibraryFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }
}