package com.vivwe.personal.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.main.R;

/**
 * ahtor: super_link
 * date: 2019/5/7 09:57
 * remark: 我的动态
 */
public class MyMomentsFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal_mymoments, container);
        return v;
    }


}
