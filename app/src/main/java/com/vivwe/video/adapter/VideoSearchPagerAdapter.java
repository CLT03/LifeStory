package com.vivwe.video.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.vivwe.video.fragment.MusicLibraryFragment;
import com.vivwe.video.fragment.VideoSearchFragment;
import com.vivwe.video.ui.MusicPlayer;

import java.util.ArrayList;

public class VideoSearchPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<VideoSearchFragment> fragments;

    public VideoSearchPagerAdapter(FragmentManager fm, ArrayList<VideoSearchFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        MusicPlayer.getInstance().release();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}