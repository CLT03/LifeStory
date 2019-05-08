package com.vivwe.faceunity.controller.create;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.faceunity.p2a_art.core.FUP2ARenderer;
import com.faceunity.p2a_art.core.NamaCore;
import com.faceunity.p2a_art.core.P2ACore;
import com.faceunity.p2a_art.renderer.CameraRenderer;
import com.faceunity.p2a_art.utils.BitmapUtil;
import com.faceunity.p2a_art.utils.FaceCheckUtil;
import com.faceunity.p2a_art.utils.LightSensorUtil;
import com.faceunity.p2a_art.utils.ToastUtil;
import com.vivwe.faceunity.controller.listener.OnCreateAvatarListener;
import com.vivwe.main.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/30 14:54
 * remark:
 */
public class TakePhotoController  {

    private Context context;
    private OnCreateAvatarListener listener;
    protected FUP2ARenderer mFUP2ARenderer;
    protected CameraRenderer mCameraRenderer;

    @BindView(R.id.tv_tip)
    TextView tipTv;

    private NamaCore mNamaCore;
    private P2ACore mP2ACore;

    public TakePhotoController(Context context, OnCreateAvatarListener listener,
                               FUP2ARenderer mFUP2ARenderer, P2ACore mP2ACore, CameraRenderer mCameraRenderer) {
        this.context = context;
        this.listener = listener;
        this.mCameraRenderer = mCameraRenderer;
        this.mFUP2ARenderer = mFUP2ARenderer;
        this.mP2ACore = mP2ACore;

        mSensorManager = LightSensorUtil.getSensorManager(context);
        LightSensorUtil.registerLightSensor(mSensorManager, mSensorEventListener);
    }

    /***
     * 展示界面
     * @param view
     */
    public void show(RelativeLayout view){

        view.removeAllViews();

        View v = LayoutInflater.from(context).inflate(R.layout.item_faceunity_newmodel_takephoto, view);
        ButterKnife.bind(this, v);

        init();
    }

    private void init(){
        mNamaCore = new NamaCore(context, mFUP2ARenderer) {
            @Override
            public int onDrawFrame(byte[] img, int tex, int w, int h) {
                int fu = super.onDrawFrame(img, tex, w, h);
                checkPic();
                return fu;
            }
        };

        mFUP2ARenderer.setFUCore(mNamaCore);
    }

    private SensorManager mSensorManager;
    private float mLight;
    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                //光线强度
                mLight = event.values[0];
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


    private int isTracking;
    private float[] faceRect;
    private int mFrameId = 0;

    private void checkPic() {
        isTracking = mNamaCore.isTracking();
        faceRect = mNamaCore.getFaceRectData();
        if (mFrameId++ % 15 > 0)
            return;
        if (isTracking != 1) {
            showCheckPic("请保持1个人输入");
        } else if (FaceCheckUtil.checkRotation(mNamaCore.getRotationData())) {
            showCheckPic("请保持正面");
        } else if (FaceCheckUtil.checkFaceRect(faceRect, mCameraRenderer.getCameraWidth(), mCameraRenderer.getCameraHeight())) {
            showCheckPic("请将人脸对准虚线框");
        } else if (FaceCheckUtil.checkExpression(mNamaCore.getExpressionData())) {
            showCheckPic("请保持面部无夸张表情");
        } else if (mLight < 5) {
            showCheckPic("光线不充足");
        } else {
            showCheckPic("完美");
        }
    }

    private void showCheckPic(final String message) {
        tipTv.post(new Runnable() {
            @Override
            public void run() {
                tipTv.setText(message);
            }
        });
    }

    /**
     * 资源释放
     */
    public void onDestroy(){
        mNamaCore.release();
        LightSensorUtil.unregisterLightSensor(mSensorManager, mSensorEventListener);
    }

    @OnClick({R.id.btn_take_photo})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.btn_take_photo:
                if (isTracking > 0) {
                    mCameraRenderer.takePic(new CameraRenderer.TakePhotoCallBack() {
                        @Override
                        public void takePhotoCallBack(final Bitmap bmp) {
                            mCameraRenderer.setNeedStopDrawFrame(false);
                            String dir = BitmapUtil.saveBitmap(bmp, isTracking == 1 ? faceRect : null);
                            listener.onFileResult(bmp, dir);
                        }
                    });
                } else {
                    ToastUtil.showCenterToast(context, "面部识别失败，请重新尝试");
                }
                break;
        }

    }

    @OnClick(R.id.ibtn_cancel)
    public void onBack(){
        listener.onFinished();
    }
}
