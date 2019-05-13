package com.vivwe.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vivwe.base.activity.BaseFragment;
import com.vivwe.faceunity.fragment.CreateAvatarFragment;
import com.vivwe.faceunity.fragment.EditDecorationFragment;
import com.vivwe.faceunity.fragment.FaceToAssetsFragment;
import com.vivwe.main.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/28 16:54
 * remark:
 */
public class HomeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.iv_creater_avatar)
    public void toCreaterAvatarFragment(){
        mainActivity.showFragment(CreateAvatarFragment.class);
    }

    @OnClick(R.id.iv_toassets)
    public void toAssetsFragment(){
        mainActivity.showFragment(FaceToAssetsFragment.class);
    }

    @OnClick(R.id.iv_edit_face)
    public void toEditFaceFragment(){
        mainActivity.showFragment(EditDecorationFragment.class);
    }
}
