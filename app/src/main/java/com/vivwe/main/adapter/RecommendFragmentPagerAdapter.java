package com.vivwe.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vivwe.main.fragment.RecommendItemFragment;

import java.util.ArrayList;

public class RecommendFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<RecommendItemFragment> fragments;

    public RecommendFragmentPagerAdapter(FragmentManager fm, ArrayList<RecommendItemFragment> fragments) {
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