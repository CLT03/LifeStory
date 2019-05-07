package com.vivwe.main.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.faceunity.p2a_art.constant.AvatarConstant;
import com.faceunity.p2a_art.core.AvatarHandle;
import com.faceunity.p2a_art.core.FUP2ARenderer;
import com.faceunity.p2a_art.core.P2ACore;
import com.faceunity.p2a_art.entity.AvatarP2A;
import com.faceunity.p2a_art.renderer.CameraRenderer;
import com.mbs.sdk.utils.PermissionsUtil;
import com.mbs.sdk.utils.ScreenUtils;
import com.vivwe.base.activity.BaseFragmentActivity;
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.faceunity.fragment.CreateAvatarFragment;
import com.vivwe.faceunity.fragment.FaceToAssetsFragment;
import com.vivwe.faceunity.listener.OnFragmentListener;
import com.vivwe.main.R;
import com.vivwe.main.fragment.HomeFragment;
import com.vivwe.main.fragment.MainFragment;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ahtor: super_link
 * date: 2019/4/23 13:49
 * remark: 主Activity
 */
public class MainActivity extends BaseFragmentActivity implements CameraRenderer.OnCameraRendererStatusListener, OnFragmentListener {

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

        int width = ScreenUtils.getScreenWidth(this);
        int height = ScreenUtils.getScreenHeight(this);
        Log.v("---", "width:"+width + "height:"+ height);

        ButterKnife.bind(this);

        init();
    }

    private int touchMode = 0;
    private void init(){

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ISEXIT = false;
            }
        };

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

        setGLSurfaceViewSize(false);
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
    public void onBackPressed() {

        if (!ISEXIT) {
            if(baseFragment != null){
                Log.v("----","showFragment(MainFragment.class)");
                //showFragment(MainFragment.class);
                baseFragment.onBackPressed();
            } else {
                ISEXIT = true;
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                // 利用handler延迟发送更改状态信息,2秒之内再按一次退出程序
                handler.sendEmptyMessageDelayed(0, 2000);
            }
        } else {
            super.onBackPressed();
        }
    }

    //重写返回键
    private Handler handler;
    private boolean ISEXIT = false;
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && !ISEXIT) {
//            if(baseFragment != null){
//                showFragment(HomeFragment.class);
//            } else {
//                ISEXIT = true;
//                Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                        Toast.LENGTH_SHORT).show();
//                // 利用handler延迟发送更改状态信息,2秒之内再按一次退出程序
//                handler.sendEmptyMessageDelayed(0, 2000);
//            }
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

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

    //选文件响应函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(baseFragment != null){
            baseFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    MainFragment homeFragment = null;
    BaseFragment baseFragment = null;

    @Override
    public void showFragment(Class cls){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(MainFragment.class == cls){

            Log.v("----", "A");

            if(baseFragment != null){
                transaction.remove(baseFragment);
                baseFragment = null;
            }

            // 显示主页
            showHomeFragment(transaction);
        } else {
            Log.v("----", "B");

            if(homeFragment != null){
                transaction.hide(homeFragment);
            }

            if(CreateAvatarFragment.class == cls){
                baseFragment = new CreateAvatarFragment();
            } else if(FaceToAssetsFragment.class == cls){
                baseFragment = new FaceToAssetsFragment();
            }

            transaction.add(contentFl.getId(), baseFragment);

            // transaction.show(baseFragment);
        }

        transaction.commitAllowingStateLoss();
    }

    /**
     * 展示主页
     * @param transaction
     */
    public void showHomeFragment(FragmentTransaction transaction) {
        if (mCameraRenderer.getCurrentCameraType() == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mCameraRenderer.updateMTX();
            mCameraRenderer.changeCamera();
        }

        if(homeFragment == null){
            homeFragment = new MainFragment();
            homeFragment.setOnFragmentListener(this);
            transaction.add(contentFl.getId(), homeFragment);
        } else {
            transaction.show(homeFragment);
        }


        mAvatarHandle.resetAllMin();
    }

    public void setGLSurfaceViewSize(boolean isMin) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mGLSurfaceView.getLayoutParams();
        params.width = isMin ? getResources().getDimensionPixelSize(R.dimen.x750) : RelativeLayout.LayoutParams.MATCH_PARENT;
//        int height = (int)(this.getResources().getDimensionPixelSize(R.dimen.x750) * 1.2);
//        params.height = isMin ? height : RelativeLayout.LayoutParams.MATCH_PARENT;

//        params.topMargin = isMin ? getResources().getDimensionPixelSize(R.dimen.x158) : 0;
        params.bottomMargin =  isMin ? getResources().getDimensionPixelSize(R.dimen.x380) : 0;
        mGLSurfaceView.setLayoutParams(params);
        //mGroupPhotoRound.setVisibility(isMin ? View.VISIBLE : View.GONE);
    }

    public GLSurfaceView getmGLSurfaceView() {
        return mGLSurfaceView;
    }

    public AvatarHandle getmAvatarHandle() {
        return mAvatarHandle;
    }

    public CameraRenderer getmCameraRenderer() {
        return mCameraRenderer;
    }

    public FUP2ARenderer getmFUP2ARenderer() {
        return mFUP2ARenderer;
    }

    public P2ACore getmP2ACore() {
        return mP2ACore;
    }
}
