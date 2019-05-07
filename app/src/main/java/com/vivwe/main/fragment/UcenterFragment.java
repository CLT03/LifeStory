package com.vivwe.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vivwe.base.activity.BaseFragment;
import com.vivwe.main.R;
import com.vivwe.author.activity.ApplyActivity;
import com.vivwe.personal.activity.MyAttentionActivity;
import com.vivwe.personal.activity.MyCollectedActivity;
import com.vivwe.personal.activity.MyDraftActivity;
import com.vivwe.personal.activity.MyFansActivity;
import com.vivwe.personal.activity.MyPurchasedActivity;
import com.vivwe.personal.activity.UpdateUserInfoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * ahtor: super_link
 * date: 2019/4/25 10:58
 * remark: 个人中心
 */
public class UcenterFragment extends BaseFragment {
    Unbinder unbind;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ucenter, null);
        unbind=ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    @OnClick({R.id.iv_setting, R.id.iv_notice, R.id.iv_attention, R.id.tv_attention, R.id.iv_fans, R.id.tv_fans, R.id.tv_purchased, R.id.tv_collected, R.id.tv_draft, R.id.tv_source, R.id.iv_more, R.id.tv_more, R.id.tv_movement, R.id.tv_video, R.id.tv_recommend, R.id.tv_author_ucenter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_setting:
                startActivity(new Intent(getActivity(),UpdateUserInfoActivity.class));
                break;
            case R.id.iv_notice:
                break;
            case R.id.iv_attention:
            case R.id.tv_attention:
                startActivity(new Intent(getActivity(),MyAttentionActivity.class));
                break;
            case R.id.iv_fans:
            case R.id.tv_fans:
                startActivity(new Intent(getActivity(),MyFansActivity.class));
                break;
            case R.id.tv_purchased:
                startActivity(new Intent(getActivity(),MyPurchasedActivity.class));
                break;
            case R.id.tv_collected:
                startActivity(new Intent(getActivity(),MyCollectedActivity.class));
                break;
            case R.id.tv_draft:
                startActivity(new Intent(getActivity(),MyDraftActivity.class));
                break;
            case R.id.tv_source:
                break;
            case R.id.iv_more:
                break;
            case R.id.tv_more:
                break;
            case R.id.tv_movement:
                break;
            case R.id.tv_video:
                break;
            case R.id.tv_recommend:
                break;
            case R.id.tv_author_ucenter:
                startActivity(new Intent(getActivity(),ApplyActivity.class));
                break;
        }
    }
}
