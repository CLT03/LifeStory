package com.vivwe.base.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.faceunity.p2a_art.core.AvatarHandle;
import com.faceunity.p2a_art.core.FUP2ARenderer;
import com.faceunity.p2a_art.core.P2ACore;
import com.faceunity.p2a_art.entity.AvatarP2A;
import com.faceunity.p2a_art.renderer.CameraRenderer;
import com.mbs.sdk.utils.ScreenUtils;
import com.vivwe.faceunity.listener.OnFragmentListener;
import com.vivwe.main.activity.MainActivity;
import com.vivwe.main.fragment.MainFragment;

import java.util.List;

/**
 * ahtor: super_link
 * date: 2019/4/25 10:46
 * remark: 该类用于执行fragment执行后的一些意图。
 */
public class BaseFragment extends Fragment {

    protected MainActivity mainActivity;
    protected FUP2ARenderer mFUP2ARenderer;
    protected P2ACore mP2ACore;
    protected AvatarHandle mAvatarHandle;
    protected CameraRenderer mCameraRenderer;
    protected AvatarP2A avatarP2A;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
        mFUP2ARenderer = mainActivity.getmFUP2ARenderer();
        mP2ACore = mainActivity.getmP2ACore();
        mAvatarHandle = mainActivity.getmAvatarHandle();
        mCameraRenderer = mainActivity.getmCameraRenderer();
        avatarP2A = mainActivity.getShowAvatarP2A();
    }

    /**
     * 返回到主界面
     */
    public void onBackPressed(){
        mainActivity.showFragment(MainFragment.class);
    }

    /**
     * Fragment监听
     */
    private OnFragmentListener onFragmentListener;


    public OnFragmentListener getOnFragmentListener() {
        return onFragmentListener;
    }

    public void setOnFragmentListener(OnFragmentListener onFragmentListener) {
        this.onFragmentListener = onFragmentListener;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

    }

    /**
     * 触摸是否可以到达父级
     * @return
     */
    public boolean touchIsCanToParent(){
        return false;
    }

    public void setViewToStatusHeight(View view){
        // 设置状态栏高度
        int stateusHeight = ScreenUtils.getStatusHeight(this.getContext());
        ConstraintLayout.LayoutParams linearParams =(ConstraintLayout.LayoutParams) view.getLayoutParams();
        linearParams.height = stateusHeight;
        view.setLayoutParams(linearParams);
    }
}
