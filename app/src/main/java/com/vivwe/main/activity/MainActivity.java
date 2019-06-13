package com.vivwe.main.activity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.faceunity.p2a_art.constant.AvatarConstant;
import com.faceunity.p2a_art.core.AvatarHandle;
import com.faceunity.p2a_art.core.FUP2ARenderer;
import com.faceunity.p2a_art.core.P2ACore;
import com.faceunity.p2a_art.entity.AvatarP2A;
import com.faceunity.p2a_art.renderer.CameraRenderer;
import com.google.gson.GsonBuilder;
import com.mbs.sdk.utils.PermissionsUtil;
import com.mbs.sdk.utils.ScreenUtils;
import com.shixing.sxvideoengine.License;
import com.shixing.sxvideoengine.SXVideo;
import com.vivwe.base.activity.BaseFragmentActivity;
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.constant.Globals;
import com.vivwe.faceunity.fragment.CreateAvatarFragment;
import com.vivwe.faceunity.fragment.EditDecorationFragment;
import com.vivwe.faceunity.fragment.EditFaceFragment;
import com.vivwe.faceunity.fragment.FaceToAssetsFragment;
import com.vivwe.faceunity.listener.OnFragmentListener;
import com.vivwe.main.R;
import com.vivwe.main.fragment.HomeFragment;
import com.vivwe.main.fragment.MainFragment;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import butterknife.BindView;
import butterknife.BindViews;
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

    @BindViews({R.id.v_left, R.id.v_top, R.id.v_right, R.id.v_bottom})
    View[] boundaryV;

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    private int touchMode = 0;
    private void init(){

        // 注册视频合成license
        String license = "FenLdZXrnW+f40bJcj2ivsQGAQfXKrzBUGybmzjmIDcwpj0rUs8aoIbRItJ4tGknq32D9f1b66nuFCiFraEoxftIgvu2DAenEs8pzzMXPtJg60xJ5dF2iWlzJWe2AYTKbkRkUdzLi3PmAFrxycOpwoS6qImrSwlrj39BsdoL5lAc2+Wr2KWyUOEkKZ1KuwvtDpl7Z394v85+4RLpAhh1Vem9rWIxr5qNAxmOjZTSdL2Z4K0NfCg0vdy6UseKtQya4nSk93KfZFjMLa2nqdbMN0drFn++u6cSblVIzo3a8ya1nqhhwrONfyQ+iGgKkqxA4Gewc6PedRShIpeYorBklQiExEi2yY7yqF2WtcAiFI9XXjAuu+dSnDfW1loNqCIaclD32oPkiXClK3ulvt2UT5ngrQ18KDS93LpSx4q1DJridKT3cp9kWMwtraep1sw3zhoeA7T8tg7QspjriD2grkkilnGME7Yq8yuYG6dYQiYnIkadb4FQX+aRvGmlV7dw9T9tz0273tN8q62EZdbsx4e1qOHPp6Oi00tXx83BZaHfsiMfbRvL8r29EU/PcxtmLvWrYmrejcflZiYtujUMlZ6+9uu7+kphK8kSXSfR0W1PuKK8hyvrpTro6mtRsYiOg3iI33M6Xv/xemLnfBzV59srUKMlgUuJqR0Vfd/3RMHJ3Mw8L3oxUU/flFdAeQ4ah7Wo4c+no6LTS1fHzcFloYWjXDy9iUfCUKU9taxYuxsjzbfHTQI/OinfMXCsD8oeycv9cLiDgcz62vHGhBAFLLJj6LUFBTJp8ce7cTQ0A0fG6iisGu02ML0taKjqk5w+CArbtkIMY8jpKE98WOM2HxV2D3XQNzr31Phm6EgP8ay1nqhhwrONfyQ+iGgKkqxA367o7342NYgFWzTgX/2FFAiExEi2yY7yqF2WtcAiFI/86kO3MqpKhsfSBfqGAFeEclD32oPkiXClK3ulvt2UT8bqKKwa7TYwvS1oqOqTnD4ICtu2QgxjyOkoT3xY4zYf/28pY7VfiPpEHgRRrbwDGEkilnGME7Yq8yuYG6dYQiYnIkadb4FQX+aRvGmlV7dwSYdeRYvABO2afLfzyzux4CsCbVB/gFLriPqiA2kbZq+0J8RUp9K5KWWFUFd2nTyPLvWrYmrejcflZiYtujUMlTERmtWIDj/XMWdRlcO2/jwXml4KBkSGeevwpBZDs2cefxKgkn7lGIt8nlIn6++BDdsrUKMlgUuJqR0Vfd/3RMGiMqLGsORC0r14xTIJAWC8KwJtUH+AUuuI+qIDaRtmrwoYGiYpb9jzKpkFYYyFea3mAeRF2Whcsd2LUhZzMfo4ycv9cLiDgcz62vHGhBAFLFhldX4EWhjl7KxQkrTJWLI=";
        License l = License.init(license);
        boolean b = l.isValid();

        // 设置状态栏文字颜色
        setStatusBarColor(Color.BLACK);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ISEXIT = false;
            }
        };


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
        if(Globals.isDebug) {
            Log.v(">>>showAvatarP2A", new GsonBuilder().create().toJson(getShowAvatarP2A()));
        }
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
//        if(avatarP2A == null){
//            avatarP2A = new AvatarP2A(AvatarP2A.style_art, R.drawable.head_1_male, AvatarP2A.gender_boy, "head_1/head.bundle",
//                    AvatarConstant.hairBundle("head_1", AvatarP2A.gender_boy), 2, 0);
            avatarP2A = UserCache.Companion.getAvatarP2A();
//        }

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
        if (baseFragment == null || baseFragment.touchIsCanToParent()) {
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

        if(baseFragment != null){
            transaction.remove(baseFragment);
            baseFragment = null;
        }

        if(MainFragment.class == cls){

            Log.v("----", "A");

            // 显示主页
            showHomeFragment(transaction);
            loadAvatarP2A();
        } else {
            Log.v("----", "B");

            if(homeFragment != null){
                transaction.hide(homeFragment);
            }

            if(CreateAvatarFragment.class == cls){
                baseFragment = new CreateAvatarFragment();
            } else if(FaceToAssetsFragment.class == cls){
                baseFragment = new FaceToAssetsFragment();
            } else if(EditDecorationFragment.class == cls){
                baseFragment = new EditDecorationFragment();
            } else if(EditFaceFragment.class == cls){
                baseFragment = new EditFaceFragment();
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


        setGLSurfaceViewSize(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.x98));
        mAvatarHandle.resetAllMin();
    }

    /**
     * 设置GlSurfaceView的位置
     * @param l 左间距
     * @param t 上间距
     * @param r 右间距
     * @param b 下间距
     */
    public void setGLSurfaceViewSize(final int l, final int t, final int r, final int b) {

//        params.width = getResources().getDimensionPixelSize(R.dimen.x750) - l - r;
//        params.height = ScreenUtils.getScreenHeight(this) - t - b;
//        int height = (int)(this.getResources().getDimensionPixelSize(R.dimen.x750) * 1.2);
//        params.height = isMin ? height : RelativeLayout.LayoutParams.MATCH_PARENT;

//        params.topMargin = isMin ? getResources().getDimensionPixelSize(R.dimen.x158) : 0;
//        params.bottomMargin =  isMin ? getResources().getDimensionPixelSize(R.dimen.x380) : 0;

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mGLSurfaceView.getLayoutParams();
                // 设置左间距宽度
                RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) boundaryV[0].getLayoutParams();
                linearParams.width = l;
                boundaryV[0].setLayoutParams(linearParams);

                // 设置上间距高度
                linearParams =(RelativeLayout.LayoutParams) boundaryV[1].getLayoutParams();
                linearParams.height = t;
                boundaryV[1].setLayoutParams(linearParams);

                // 设置右间距宽度
                linearParams =(RelativeLayout.LayoutParams) boundaryV[2].getLayoutParams();
                linearParams.width = r;
                boundaryV[2].setLayoutParams(linearParams);

                // 设置下间距高度
                linearParams =(RelativeLayout.LayoutParams) boundaryV[3].getLayoutParams();
                linearParams.height = b;
                boundaryV[3].setLayoutParams(linearParams);

                mGLSurfaceView.setLayoutParams(params);
            }
        });

//        params.leftMargin = l;
//        params.topMargin = t;
//        params.rightMargin = r;
//        params.bottomMargin = b;

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
