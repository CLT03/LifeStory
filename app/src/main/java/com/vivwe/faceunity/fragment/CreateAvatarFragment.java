package com.vivwe.faceunity.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.faceunity.p2a_art.entity.AvatarP2A;
import com.faceunity.p2a_art.utils.BitmapUtil;
import com.faceunity.p2a_art.utils.FileUtil;
import com.faceunity.p2a_art.utils.ToastUtil;
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.faceunity.constant.CreateAvatarTypeEnum;
import com.vivwe.faceunity.controller.create.CreateAvatarController;
import com.vivwe.faceunity.controller.create.GetPhotoWayController;
import com.vivwe.faceunity.controller.create.SelectSexController;
import com.vivwe.faceunity.controller.create.TakePhotoController;
import com.vivwe.faceunity.controller.listener.OnCreateAvatarListener;
import com.vivwe.main.R;
import java.io.File;
import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * 创建化身
 */
public class CreateAvatarFragment extends BaseFragment implements OnCreateAvatarListener {

    @BindView(R.id.rl_content)
    RelativeLayout contentRl;

    public String filepath;
    public int step;
    public static final String TAG = "CreateAvatarFragment";
    SelectSexController selectSexController;
    GetPhotoWayController readyController;
    TakePhotoController takePhotoController;
    CreateAvatarController scanFaceController;


    // 模型生成方式（1：摄像头， 2：文件）
    private CreateAvatarTypeEnum type;

    public int sex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_faceunity_create, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    /***
     * 初始化
     */
    private void init(){
        // 获取化身创建方式
        //type = getIntent().getIntExtra("type", CreateAvatarTypeEnum.FILE.getIndex());
//        type = (CreateAvatarTypeEnum) getIntent().getSerializableExtra("type");

        // 选择性别
        selectSexController = new SelectSexController(this.getContext(), this);
        selectSexController.show(contentRl);
    }


    /***
     * 设置创建化身的方式（1：拍照， 2：文件选择）
     * @param type
     */
    public void setType(CreateAvatarTypeEnum type) {
        this.type = type;
    }

    //选文件响应函数
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { // 拍照结果
            Uri uri;
            if (data != null) {
                uri = data.getData();
                String filePath = FileUtil.getFileAbsolutePath(this.getContext(), uri);
                File file = new File(filePath);
//                if (!Constant.is_debug || !createAvatarDebug(file)) {
                    if (file.exists()) {
                        Bitmap bitmap = BitmapUtil.loadBitmap(filePath, 720);
                        String dir = BitmapUtil.saveBitmap(bitmap, null);
                        onFileResult(bitmap, dir);
                        return;
                    } else {
                        ToastUtil.showCenterToast(this.getContext(), "所选图片文件不存在。");
                    }
//                }
            }
        } else if(requestCode == 2){ // 图库选择照片结果
            String filePath = data.getStringArrayListExtra("result").get(0);
            File file = new File(filePath);
            if (file.exists()) {
                Bitmap bitmap = BitmapUtil.loadBitmap(filePath, 720);
                String dir = BitmapUtil.saveBitmap(bitmap, null);
                onFileResult(bitmap, dir);
                return;
            } else {
                ToastUtil.showCenterToast(this.getContext(), "所选图片文件不存在。");
            }
        }
    }

    @Override
    public void onSexResult(int sex) {
        this.sex = sex;

//        if(type == CreateAvatarTypeEnum.FILE){ // 从相册里选择照片作为化身
//            Intent intent = new Intent();
//            intent.setClass(this, ImageLoadActivity.class);
//            this.startActivityForResult(intent, 2);
//        } else { // 拍照作为化身
            if(readyController == null){
                readyController = new GetPhotoWayController(this.getContext(),this);
            }
            // 选择获取图片方式
            readyController.show(contentRl);
//        }
    }

    @Override
    public void onTakePhotoReadyListener(CreateAvatarTypeEnum type) {

        if(type == null){
            selectSexController.show(contentRl);
        } else {
            this.type = type;
//            StatusBarUtil.setStatusBarColor(this, R.color.colorWhite);
            if(type == CreateAvatarTypeEnum.FILE){ // 从相册里选择照片作为化身
//                Intent intent = new Intent();
//                intent.setClass(this, ImageLoadActivity.class);
//                this.startActivityForResult(intent, 2);
            } else { // 拍照作为化身
                if(takePhotoController == null){
                    takePhotoController = new TakePhotoController(this.getContext(), this, mFUP2ARenderer, mP2ACore, mCameraRenderer);
                }
                // 准备拍照页面
                takePhotoController.show(contentRl);
            }
        }
    }

    @Override
    public void onFileResult(final Bitmap bitmap,final String dir) {

        // 拿到选择或拍照后的图片文件路径，进入扫脸步骤
        if(scanFaceController == null){
            scanFaceController = new CreateAvatarController(this.getActivity(), this);
        }
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scanFaceController.show(contentRl, sex, bitmap, dir);
            }
        });

    }

    @Override
    public void onFinished(final AvatarP2A avatarP2A) {

        // 保存最新化身到本地
//        CacheDataService.saveAvatarP2A(avatarP2A);

        // 启动服务更新最新化身到服务器上（异步的方式）
//        Intent startIntent = new Intent(this, SyncAvatarService.class);
//        startIntent.putExtra("dir", avatarP2A.getBundleDir());
//        startService(startIntent);

        // 退出
//        setResult(1);
//        finish();
        onBackPressed();
    }

    @Override
    public void onFinished() {
        // 退出
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(takePhotoController != null){
            mFUP2ARenderer.setFUCore(mP2ACore);
            takePhotoController.onBackPressed();

            mCameraRenderer.updateMTX();
            mFUP2ARenderer.queueNextEvent(new Runnable() {
                @Override
                public void run() {
                    CreateAvatarFragment.super.onBackPressed();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(takePhotoController != null){
            takePhotoController.onDestroy();
        }
    }
}
