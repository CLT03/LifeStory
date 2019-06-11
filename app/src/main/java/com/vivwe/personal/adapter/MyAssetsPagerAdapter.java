package com.vivwe.personal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vivwe.personal.fragment.MyAssetsFragment;
import com.vivwe.personal.fragment.MyCollectedFragment;

import java.util.ArrayList;

public class MyAssetsPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<MyAssetsFragment> fragments;

    public MyAssetsPagerAdapter(FragmentManager fm, ArrayList<MyAssetsFragment> fragments) {
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