package com.vivwe.main.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

import com.faceunity.p2a_art.constant.AvatarConstant;
import com.faceunity.p2a_art.core.AvatarHandle;
import com.faceunity.p2a_art.core.FUP2ARenderer;
import com.faceunity.p2a_art.core.P2ACore;
import com.faceunity.p2a_art.entity.AvatarP2A;
import com.faceunity.p2a_art.renderer.CameraRenderer;
import com.mbs.sdk.utils.PermissionsUtil;
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.base.activity.BaseFragmentActivity;
import com.vivwe.main.R;
import com.vivwe.main.fragment.MainFragment;
import com.vivwe.main.fragment.UcenterFragment;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ahtor: super_link
 * date: 2019/4/23 13:49
 * remark: 主Activity
 */
public class MainActivity extends BaseFragmentActivity implements CameraRenderer.OnCameraRendererStatusListener {

    @BindView(R.id.fl_content)
    FrameLayout contentFl; // 内容
    @BindView(R.id.glsv_avatar)
    GLSurfaceView mGLSurfaceView;

    private AvatarHandle mAvatarHandle;
    private CameraRenderer mCameraRenderer;
    private FUP2ARenderer mFUP2ARenderer;
    private P2ACore mP2ACore;

    private GestureDetectorCompat mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;

    // 用户化身
    private AvatarP2A avatarP2A;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        init();
    }

    private int touchMode = 0;
    private void init(){

        PermissionsUtil.checkAndRequestPermissions(this);

        mGLSurfaceView.setEGLContextClientVersion(3);
        mCameraRenderer = new CameraRenderer(this, mGLSurfaceView);
        mCameraRenderer.setOnCameraRendererStatusListener(this);
        mGLSurfaceView.setRenderer(mCameraRenderer);
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        mFUP2ARenderer = new FUP2ARenderer(this);
        mP2ACore = new P2ACore(this, mFUP2ARenderer);
        mFUP2ARenderer.setFUCore(mP2ACore);
        mAvatarHandle = mP2ACore.createAvatarHandle();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        final int screenWidth = metrics.widthPixels;
        final int screenHeight = metrics.heightPixels;
        mGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
//                if (showIndex == 0) {
//                    return fragments.get(0).onSingleTapUp(e);
//                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (touchMode != 1) {
                    touchMode = 1;
                    return false;
                }
                float rotDelta = -distanceX / screenWidth;
                float translateDelta = distanceY / screenHeight;
                mAvatarHandle.setRotDelta(rotDelta);
                mAvatarHandle.setTranslateDelta(translateDelta);
                return distanceX != 0 || translateDelta != 0;
            }
        });

        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if (touchMode != 2) {
                    touchMode = 2;
                    return false;
                }
                float scale = detector.getScaleFactor() - 1;
                mAvatarHandle.setScaleDelta(scale);
                return scale != 0;
            }
        });
        showFragment(MainFragment.class);
        loadAvatarP2A();
    }

    private void loadAvatarP2A(){
        setMShowAvatarP2A();
    }

    /***
     * 设置并显示化身数据
     */
    private void setMShowAvatarP2A(){

        mAvatarHandle.setAvatar(getShowAvatarP2A(), new Runnable() {
            @Override
            public void run() {
//                if (loadingV.getVisibility() == View.VISIBLE) {
//                    loadingV.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            loadingV.setVisibility(View.GONE);
//                        }
//                    });
//                }
            }
        });
    }



    public AvatarP2A getShowAvatarP2A() {

        // 如果转换失败的话创建一个默认的
        if(avatarP2A == null){
            avatarP2A = new AvatarP2A(AvatarP2A.style_art, R.drawable.head_1_male, AvatarP2A.gender_boy, "head_1/head.bundle",
                    AvatarConstant.hairBundle("head_1", AvatarP2A.gender_boy), 2, 0);
        }

        return avatarP2A;
    }



    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mFUP2ARenderer.onSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public int onDrawFrame(byte[] cameraNV21Byte, int cameraTextureId, int cameraWidth, int cameraHeight) {
        mCameraRenderer.refreshLandmarks(mP2ACore.getLandmarksData());
        return mFUP2ARenderer.onDrawFrame(cameraNV21Byte, cameraTextureId, cameraWidth, cameraHeight);
    }

    @Override
    public void onSurfaceDestroy() {
        mFUP2ARenderer.onSurfaceDestroyed();
    }

    @Override
    public void onCameraChange(int currentCameraType, int cameraOrientation) {
        mFUP2ARenderer.onCameraChange(currentCameraType, cameraOrientation);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (baseFragment == null) {
            if (event.getPointerCount() == 2) {
                mScaleGestureDetector.onTouchEvent(event);
            } else if (event.getPointerCount() == 1)
                mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraRenderer.openCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCameraRenderer.releaseCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraRenderer.onDestroy();
    }

    MainFragment homeFragment = null;
    BaseFragment baseFragment = null;

    public void showFragment(Class cls){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(MainFragment.class == cls){

            Log.v("----", "A");

            if(baseFragment != null){
                transaction.remove(baseFragment);
                baseFragment = null;
            }

            if(homeFragment == null){
                homeFragment = new MainFragment();
                transaction.add(contentFl.getId(), homeFragment);
            } else {
                transaction.show(homeFragment);
            }
        } else {
            Log.v("----", "B");

            if(homeFragment != null){
                transaction.hide(homeFragment);
            }

            baseFragment = new UcenterFragment();
            transaction.add(contentFl.getId(), baseFragment);

            // transaction.show(baseFragment);
        }

        transaction.commitAllowingStateLoss();
    }
}
