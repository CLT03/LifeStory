package com.vivwe.main.fragment;

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
 * date: 2019/4/25 10:58
 * remark: 个人中心
 */
public class UcenterFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ucenter, null);
    }
}
