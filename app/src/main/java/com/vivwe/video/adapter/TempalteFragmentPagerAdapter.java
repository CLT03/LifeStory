package com.vivwe.video.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vivwe.main.fragment.RecommendItemFragment;
import com.vivwe.video.fragment.TemplateItemFragment;

import java.util.ArrayList;

public class TempalteFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<TemplateItemFragment> fragments;

    public TempalteFragmentPagerAdapter(FragmentManager fm, ArrayList<TemplateItemFragment> fragments) {
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