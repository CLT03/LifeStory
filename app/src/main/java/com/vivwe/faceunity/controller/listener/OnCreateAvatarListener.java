package com.vivwe.faceunity.controller.listener;

import android.graphics.Bitmap;
import com.faceunity.p2a_art.entity.AvatarP2A;
import com.vivwe.faceunity.constant.CreateAvatarTypeEnum;

/**
 * ahtor: super_link
 * date: 2019/2/27 13:41
 * remark:
 */
public interface OnCreateAvatarListener {

    public void onFinished();
    /***
     * 选择了性别
     * @param sex
     */
    public void onSexResult(int sex);

    /***
     * 选择了文件
     * @param bitmap 根据图片封装的bitmap对象
     * @param filePath 文件路径
     */
    public void onFileResult(final Bitmap bitmap, String dir);

    /***
     * 准备
     * @param hasReady
     */
    public void onTakePhotoReadyListener(CreateAvatarTypeEnum type);

    /***
     * 完成创建
     * @param avatarP2A
     */
    public void onFinished(AvatarP2A avatarP2A);

}
