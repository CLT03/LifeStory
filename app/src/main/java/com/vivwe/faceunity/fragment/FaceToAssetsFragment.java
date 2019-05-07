package com.vivwe.faceunity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.faceunity.p2a_art.constant.AvatarConstant;
import com.faceunity.p2a_art.constant.Constant;
import com.faceunity.p2a_art.core.AvatarHandle;
import com.faceunity.p2a_art.core.P2AMultipleCore;
import com.faceunity.p2a_art.entity.AvatarP2A;
import com.faceunity.p2a_art.entity.Scenes;
import com.faceunity.p2a_art.utils.DateUtil;
import com.faceunity.p2a_art.utils.FileUtil;
import com.faceunity.p2a_helper.gif.GifHardEncoderWrapper;
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.faceunity.adapter.TestAdapter;
import com.vivwe.main.R;

import java.io.File;

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
    private AvatarP2A avatarP2A;
    private P2AMultipleCore mP2AMultipleCore;
    private GifHardEncoderWrapper mGifHardEncoder;
    private boolean isAnimationScenes;
    private int isLoadComplete;
    private String mGifPath = "";
    private SparseArray<AvatarHandle> mAvatarHandleSparse;
    private Scenes mScenes;
    int frameId = NONE_FRAME_ID;
    static final int NONE_FRAME_ID = -100;

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

        // data
        FileUtil.createFile(Constant.TmpPath);
        avatarP2A = mainActivity.getShowAvatarP2A();

        // 初始化表情控件
        testAdapter = new TestAdapter(avatarP2A.getGender() == 0 ? AvatarConstant.SCENES_ART_SINGLE_MALE : AvatarConstant.SCENES_ART_SINGLE_FEMALE);

        faceRlv.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        faceRlv.setAdapter(testAdapter);
        ((SimpleItemAnimator) faceRlv.getItemAnimator()).setSupportsChangeAnimations(false);

        // listener
        testAdapter.setScenesSelectListener(new TestAdapter.ScenesSelectListener() {
            @Override
            public void onScenesSelectListener(boolean isAnim, Scenes scenes) {
                isAnimationScenes = isAnim;
                mScenes = scenes;
                mP2AMultipleCore = new P2AMultipleCore(mainActivity, mFUP2ARenderer) {

                    @Override
                    public int onDrawFrame(byte[] img, int tex, int w, int h) {
                        int fuTex = super.onDrawFrame(img, tex, w, h);
                        AvatarHandle avatarHandle = mAvatarHandleSparse.get(0);
                        if (avatarHandle != null && mGifHardEncoder != null) {
                            int nowFrameId = avatarHandle.getNowFrameId();
                            if (frameId > nowFrameId) {
                                releaseGifEncoder();
                                frameId = NONE_FRAME_ID;
                            } else {
                                mGifHardEncoder.encodeFrame(fuTex);
                                frameId = nowFrameId;
                            }
                        }
                        return fuTex;
                    }
                };
                mP2ACore.unBind();
                mFUP2ARenderer.setFUCore(mP2AMultipleCore);
                mAvatarHandleSparse = mP2AMultipleCore.createAvatarMultiple(mScenes);
                isLoadComplete = 0;

                showAvatar();
            }
        });

        // 显示缩小
        mainActivity.setGLSurfaceViewSize(true);
    }

    /**
     * 展示形象
     * 暂时只是设计为单角色，因为mScenes.bundles[0]为第一个角色，所以单角色情况下这样获取。
     */
    private void showAvatar(){
        if (avatarP2A.getGender() == mScenes.bundles[0].gender) {
            avatarP2A.setExpressionFile(mScenes.bundles[0].path);
            final AvatarHandle avatarHandle = mAvatarHandleSparse.get(0);
            avatarHandle.setAvatar(avatarP2A, new Runnable() {
                @Override
                public void run() {
//                    mAvatarLayout.updateAvatarPoint();
                    if (++isLoadComplete == mAvatarHandleSparse.size()) {
                        if (isAnimationScenes) {
                            startGifEncoder();
                        }
                    }
                    avatarHandle.seekToAnimFrameId(1);
                    avatarHandle.setAnimState(2);
                }
            });
        }
    }

    private void startGifEncoder() {
        mP2AMultipleCore.queueEvent(new Runnable() {
            @Override
            public void run() {
                if (mGifHardEncoder != null) {
                    mGifHardEncoder.release();
                }

                Log.v("---", Constant.TmpPath + DateUtil.getCurrentDate() + "_tmp.gif");

                mGifHardEncoder = new GifHardEncoderWrapper(mGifPath = Constant.TmpPath + DateUtil.getCurrentDate() + "_tmp.gif",
                        mCameraRenderer.getCameraHeight() / 2, mCameraRenderer.getCameraWidth() / 2);
                if (mAvatarHandleSparse.get(0) != null) {
                    mAvatarHandleSparse.get(0).seekToAnimFrameId(1);
                    mAvatarHandleSparse.get(0).setAnimState(1);
                }
                frameId = NONE_FRAME_ID;
            }
        });
    }

    private void releaseGifEncoder() {
        if (mGifHardEncoder != null) {
            mGifHardEncoder.release();
            mGifHardEncoder = null;
//            mAvatarLayout.updateNextBtn(true);
        }
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

    /** 当前展示功能索引（1: 表情、2：gif） */
    private int showIndex = 1;

    /**
     * 切换功能视图
     * @param index 1: 表情、2：gif
     */
    private void showFunView(int index){
        if(showIndex != index){

            showIndex = index;

            faceLineV.setVisibility(index == 1 ? View.VISIBLE : View.INVISIBLE);
            gifLineV.setVisibility(index == 2 ? View.VISIBLE : View.INVISIBLE);
            faceToAssetsBtn.setTextColor(index == 1 ? 0xff191919 : 0xFFABABAB);
            gifToAssetsBtn.setTextColor(index == 2 ? 0xff191919 : 0xFFABABAB);

            // 切换选项卡
            testAdapter.setDatas(index == 1 ? (avatarP2A.getGender() == 0 ? AvatarConstant.SCENES_ART_SINGLE_MALE : AvatarConstant.SCENES_ART_SINGLE_FEMALE) :
                    (avatarP2A.getGender() == 0 ? AvatarConstant.SCENES_ART_ANIMATION_MALE :AvatarConstant.SCENES_ART_ANIMATION_FEMALE));

        }
    }

    @Override
    public void onBackPressed() {

        mP2ACore.bind();
        mFUP2ARenderer.setFUCore(mP2ACore);

        mainActivity.setGLSurfaceViewSize(false);

        if (mP2AMultipleCore != null) {
            mP2AMultipleCore.release();
            mP2AMultipleCore = null;
        }
        FileUtil.deleteDirAndFile(new File(Constant.TmpPath));

        super.onBackPressed();
    }
}
