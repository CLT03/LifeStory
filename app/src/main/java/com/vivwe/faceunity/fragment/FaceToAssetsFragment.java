package com.vivwe.faceunity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.faceunity.p2a_art.constant.AvatarConstant;
import com.faceunity.p2a_art.entity.Scenes;
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.faceunity.adapter.TestAdapter;
import com.vivwe.main.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/5 17:17
 * remark:
 */
public class FaceToAssetsFragment extends BaseFragment {

    @BindView(R.id.rlv_face)
    RecyclerView faceRlv;

    @BindView(R.id.v_face_line)
    View faceLineV;
    @BindView(R.id.v_gif_line)
    View gifLineV;
    @BindView(R.id.btn_face_toassets)
    TextView faceToAssetsBtn;
    @BindView(R.id.btn_gif_toassets)
    TextView gifToAssetsBtn;

    // adapter
    TestAdapter testAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_faceunity_tosssets, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    /**
     * 初始化
     */
    private void init(){

        // 初始化表情控件
        testAdapter = new TestAdapter(AvatarConstant.SCENES_ART_SINGLE);

        faceRlv.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        faceRlv.setAdapter(testAdapter);
        ((SimpleItemAnimator) faceRlv.getItemAnimator()).setSupportsChangeAnimations(false);

        testAdapter.setScenesSelectListener(new TestAdapter.ScenesSelectListener() {
            @Override
            public void onScenesSelectListener(boolean isAnim, Scenes scenes) {

            }
        });

        // 显示缩小
        mainActivity.setGLSurfaceViewSize(true);
    }

    @OnClick({R.id.btn_face_toassets, R.id.btn_gif_toassets})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.btn_face_toassets: // 表情导出
//                underLineV.setCon
                showFunView(1);
                break;
            case R.id.btn_gif_toassets: // gif导出
                showFunView(2);
                break;
        }
    }

    private int showIndex = 1;
    private void showFunView(int index){
        if(showIndex != index){

            showIndex = index;

            faceLineV.setVisibility(index == 1 ? View.VISIBLE : View.INVISIBLE);
            gifLineV.setVisibility(index == 2 ? View.VISIBLE : View.INVISIBLE);
            faceToAssetsBtn.setTextColor(index == 1 ? 0xff191919 : 0xFFABABAB);
            gifToAssetsBtn.setTextColor(index == 2 ? 0xff191919 : 0xFFABABAB);

            // 切换选项卡
            testAdapter.setDatas(index == 1 ? AvatarConstant.SCENES_ART_SINGLE : AvatarConstant.SCENES_ART_ANIMATION);

        }
    }

}
