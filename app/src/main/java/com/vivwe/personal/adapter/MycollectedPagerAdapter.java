package com.vivwe.personal.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.vivwe.personal.fragment.MycollectedFragment;
import com.vivwe.video.fragment.VideoSearchFragment;
import com.vivwe.video.ui.MusicPlayer;

import java.util.ArrayList;

public class MycollectedPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<MycollectedFragment> fragments;

    public MycollectedPagerAdapter(FragmentManager fm, ArrayList<MycollectedFragment> fragments) {
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