package com.vivwe.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.base.cache.UserCache;
import com.vivwe.faceunity.fragment.CreateAvatarFragment;
import com.vivwe.faceunity.fragment.EditDecorationFragment;
import com.vivwe.faceunity.fragment.FaceToAssetsFragment;
import com.vivwe.main.R;
import com.vivwe.main.entity.UserInfoEntity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/28 16:54
 * remark:
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_username)
    TextView userNameTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){

        // 获取用户信息
        UserInfoEntity userInfo = UserCache.Companion.getUserInfo();
        userNameTv.setText(userInfo.getNickname() == null ? "Your Name" : userInfo.getNickname());

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
