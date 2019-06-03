package com.vivwe.author.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vivwe.author.fragment.TemplateApprovedFragment;
import com.vivwe.personal.fragment.MycollectedFragment;

import java.util.ArrayList;

public class TemplateApprovedPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<TemplateApprovedFragment> fragments;

    public TemplateApprovedPagerAdapter(FragmentManager fm, ArrayList<TemplateApprovedFragment> fragments) {
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