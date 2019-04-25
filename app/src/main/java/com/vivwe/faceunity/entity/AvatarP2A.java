package com.vivwe.faceunity.entity;

import android.text.TextUtils;
import com.vivwe.faceunity.constant.AvatarConstant;
import com.vivwe.faceunity.constant.Constant;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by tujh on 2018/6/20.
 */
public class AvatarP2A implements Serializable {
    private static final long serialVersionUID = -2062781401904016738L;
   // public static final String TAG = AvatarP2A.class.getSimpleName();

    /** 该文件在服务器中保存的位置 */
    private String server_url;
    /** 一种风格，默认为1，不会有什么变动 */
    private int style = Constant.style_art;
    /** bundle 资源目录 */
    private String bundleDir = "";
    /** 图片文件资源路径 */
    private int originPhotoRes = -1;
    /** 图片文件路径 */
    private String originPhoto = "";
    /** 图片文件缩略图 */
    private String originPhotoThumbNail = "";
    /** 头像文件路径 */
    private String headFile = "";
    /** 身体文件路径 */
    private String bodyFile = "";
    /** 表情文件路径 */
    private String expressionFile = "";
    /** 头发文件路径（多种头发，数组形式） */
    private String[] hairFileList = new String[0];
    /** 性别（0：男， 1：女） */
    private int gender = Constant.gender_boy;

    /** 头发位置(在本地的资源下标) */
    private int hairIndex = 0;
    /** 眼镜位置(在本地的资源下标) */
    private int glassesIndex = 0;
    /** 衣服位置(在本地的资源下标) */
    private int clothesIndex = 0;
    /** 胡子位置(在本地的资源下标) */
    private int beardIndex = 0;
    /** 睫毛位置(在本地的资源下标) */
    private int eyelashIndex = 0;
    /** 眉毛位置(在本地的资源下标) */
    private int eyebrowIndex = 0;
    /** 帽子位置(在本地的资源下标) */
    private int hatIndex = 0;

    private double[] skinColorServerValues = new double[]{0, 0, 0};
    /** 皮肤颜色值 */
    private double skinColorValue = -1;
    private double[] lipColorServerValues = new double[]{0, 0, 0};
    /** 唇色颜色值 */
    private double lipColorValue = -1;
    /** 虹膜颜色值 */
    private double irisColorValue = 0;
    /** 头发颜色值 */
    private double hairColorValue = 0;
    /** 眼镜片颜色值 */
    private double glassesColorValue = 0;
    /** 眼镜框颜色值 */
    private double glassesFrameColorValue = 0;
    /** 胡子颜色值 */
    private double beardColorValue = 0;
    /** 帽子颜色值 */
    private double hatColorValue = 0;

    public AvatarP2A() {
        hairIndex = -1;
        glassesIndex = -1;
        clothesIndex = -1;
        beardIndex = -1;
        eyelashIndex = -1;
        eyebrowIndex = -1;
        hatIndex = -1;
    }

    public AvatarP2A(int style, int originPhotoRes, int gender, String headFile, String[] hairFileList, int hairIndex, int beardIndex) {
        this.style = style;
        this.originPhotoRes = originPhotoRes;
        this.gender = gender;
        this.headFile = headFile;
        this.bodyFile = AvatarConstant.bodyBundle(gender);
        this.hairFileList = hairFileList;
        this.hairIndex = hairIndex;
        this.beardIndex = beardIndex;
    }

    public AvatarP2A(int style, String bundleDir, int gender, String headFile, String[] hairFileList) {
        this.style = style;
        setBundleDir(bundleDir);

        this.headFile = headFile;
        this.bodyFile = AvatarConstant.bodyBundle(gender);
        this.gender = gender;
        this.hairFileList = hairFileList;
    }

    public AvatarP2A(int style, String bundleDir, int gender) {
        this.style = style;
        setBundleDir(bundleDir);

        this.bodyFile = AvatarConstant.bodyBundle(gender);
        this.gender = gender;
    }

    public String getServer_url() {
        return server_url;
    }

    public void setServer_url(String server_url) {
        this.server_url = server_url;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public String getBundleDir() {
        return bundleDir;
    }

    public void setBundleDir(String bundleDir) {
        this.bundleDir = bundleDir;
        this.originPhoto = bundleDir + Constant.FILE_NAME_CLIENT_DATA_ORIGIN_PHOTO;
        this.originPhotoThumbNail = bundleDir + Constant.FILE_NAME_CLIENT_DATA_THUMB_NAIL;
        this.headFile = bundleDir + Constant.FILE_NAME_HEAD_BUNDLE;
    }

    public int getOriginPhotoRes() {
        return originPhotoRes;
    }

    public String getOriginPhoto() {
        return originPhoto;
    }

    public void setOriginPhoto(String originPhoto) {
        this.originPhoto = originPhoto;
    }

    public String getOriginPhotoThumbNail() {
        return originPhotoThumbNail;
    }

    public void setOriginPhotoThumbNail(String originPhotoThumbNail) {
        this.originPhotoThumbNail = originPhotoThumbNail;
    }

    public String getHeadFile() {
        return headFile;
    }

    public String getBodyFile() {
        return bodyFile;
    }

    public String[] getHairFileList() {
        return hairFileList;
    }

    public void setHairFileList(String[] hairFileList) {
        this.hairFileList = hairFileList;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getHairIndex() {
        return hairIndex;
    }

    public void setHairIndex(int hairIndex) {
        this.hairIndex = hairIndex;
    }

    public int getGlassesIndex() {
        return glassesIndex;
    }

    public void setGlassesIndex(int glassesIndex) {
        this.glassesIndex = glassesIndex;
    }

    public int getClothesIndex() {
        return clothesIndex;
    }

    public void setClothesIndex(int clothesIndex) {
        this.clothesIndex = clothesIndex;
    }

    public int getEyelashIndex() {
        return eyelashIndex;
    }

    public void setEyelashIndex(int eyelashIndex) {
        this.eyelashIndex = eyelashIndex;
    }

    public int getEyebrowIndex() {
        return eyebrowIndex;
    }

    public void setEyebrowIndex(int eyebrowIndex) {
        this.eyebrowIndex = eyebrowIndex;
    }

    public int getBeardIndex() {
        return beardIndex;
    }

    public void setBeardIndex(int beardIndex) {
        this.beardIndex = beardIndex;
    }

    public int getHatIndex() {
        return hatIndex;
    }

    public void setHatIndex(int hatIndex) {
        this.hatIndex = hatIndex;
    }

    public String getExpressionFile() {
        return expressionFile;
    }

    public void setExpressionFile(String expressionFile) {
        this.expressionFile = expressionFile;
    }

    public String getHairFile() {
        return hairFileList == null || hairFileList.length == 0 || hairIndex < 0 ? "" : hairFileList[hairIndex];
    }

    public String getGlassesFile() {
        return glassesIndex < 0 ? "" : AvatarConstant.glassesBundleRes(gender)[glassesIndex].path;
    }

    public String getClothesFile() {
        return clothesIndex < 0 ? "" : AvatarConstant.clothesBundleRes(gender)[clothesIndex].path;
    }

    public String getEyelashFile() {
        return eyelashIndex < 0 ? "" : AvatarConstant.eyelashBundleRes()[eyelashIndex].path;
    }

    public String getEyebrowFile() {
        return eyebrowIndex < 0 ? "" : AvatarConstant.eyebrowBundleRes(gender)[eyebrowIndex].path;
    }

    public String getBeardFile() {
        return beardIndex < 0 ? "" : AvatarConstant.beardBundleRes()[beardIndex].path;
    }

    public String getHatFile() {
        return hatIndex < 0 ? "" : AvatarConstant.hatBundleRes(gender)[hatIndex].path;
    }

    public double[] getSkinColorServerValues() {
        return skinColorServerValues;
    }

    public void setSkinColorServerValues(double[] skinColorServerValues) {
        this.skinColorServerValues = skinColorServerValues;
    }

    public double getSkinColorValue() {
        return skinColorValue;
    }

    public void setSkinColorValue(double skinColorValue) {
        this.skinColorValue = skinColorValue;
    }

    public double[] getLipColorServerValues() {
        return lipColorServerValues;
    }

    public void setLipColorServerValues(double[] lipColorServerValues) {
        this.lipColorServerValues = lipColorServerValues;
    }

    public double getLipColorValue() {
        return lipColorValue;
    }

    public void setLipColorValue(double lipColorValue) {
        this.lipColorValue = lipColorValue;
    }

    public double getIrisColorValue() {
        return irisColorValue;
    }

    public void setIrisColorValue(double irisColorValue) {
        this.irisColorValue = irisColorValue;
    }

    public double getHairColorValue() {
        return hairColorValue;
    }

    public void setHairColorValue(double hairColorValue) {
        this.hairColorValue = hairColorValue;
    }

    public double getGlassesColorValue() {
        return glassesColorValue;
    }

    public void setGlassesColorValue(double glassesColorValue) {
        this.glassesColorValue = glassesColorValue;
    }

    public double getGlassesFrameColorValue() {
        return glassesFrameColorValue;
    }

    public void setGlassesFrameColorValue(double glassesFrameColorValue) {
        this.glassesFrameColorValue = glassesFrameColorValue;
    }

    public double getBeardColorValue() {
        return beardColorValue;
    }

    public void setBeardColorValue(double beardColorValue) {
        this.beardColorValue = beardColorValue;
    }

    public double getHatColorValue() {
        return hatColorValue;
    }

    public void setHatColorValue(double hatColorValue) {
        this.hatColorValue = hatColorValue;
    }

    @Override
    public String toString() {
        return " bundleDir " + bundleDir + "\n"
                + " originPhotoRes " + originPhotoRes + " originPhoto " + originPhoto + " originPhotoThumbNail " + originPhotoThumbNail + "\n"
                + " style " + style + " gender " + gender + "\n"
                + " mHeadFile " + headFile + " lipColorValue " + lipColorValue + " lipColorServerValues " + Arrays.toString(lipColorServerValues) + " irisColorValue " + irisColorValue + " skinColorValue " + skinColorValue + " skinColorServerValues " + Arrays.toString(skinColorServerValues) + "\n"
                + " bodyFile " + bodyFile + "\n"
                + " hairIndex " + hairIndex + " hair " + getHairFile() + " hairColorValue " + hairColorValue + " hairFileList " + Arrays.toString(hairFileList) + "\n"
                + " glassesIndex " + glassesIndex + " glasses " + getGlassesFile() + " glassesColorValue " + glassesColorValue + " glassesFrameColorValue " + glassesFrameColorValue + "\n"
                + " clothesIndex " + clothesIndex + " clothes " + getClothesFile() + " hatIndex " + hatIndex + " hat " + getHatFile() + " hatColorValue " + hatColorValue + "\n"
                + " expressionFile " + expressionFile + "\n"
                + " eyelashIndex " + eyelashIndex + " eyebrowIndex " + eyebrowIndex + "\n"
                + " beardIndex " + beardIndex + " beard " + getBeardFile() + " beardColorValue " + beardColorValue + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvatarP2A avatarP2A = (AvatarP2A) o;
        return !TextUtils.isEmpty(headFile) && headFile.equals(avatarP2A.getHeadFile());
    }


    @Override
    public AvatarP2A clone() {
        AvatarP2A avatarP2A = new AvatarP2A();
        avatarP2A.style = this.style;
        avatarP2A.bundleDir = this.bundleDir;
        avatarP2A.originPhotoRes = this.originPhotoRes;
        avatarP2A.originPhoto = this.originPhoto;
        avatarP2A.originPhotoThumbNail = this.originPhotoThumbNail;
        avatarP2A.headFile = this.headFile;
        avatarP2A.bodyFile = this.bodyFile;
        avatarP2A.gender = this.gender;
        avatarP2A.hairIndex = this.hairIndex;
        avatarP2A.hairFileList = Arrays.copyOf(this.hairFileList, this.hairFileList.length);
        avatarP2A.glassesIndex = this.glassesIndex;
        avatarP2A.clothesIndex = this.clothesIndex;
        avatarP2A.expressionFile = this.expressionFile;
        avatarP2A.beardIndex = this.beardIndex;
        avatarP2A.eyelashIndex = this.eyelashIndex;
        avatarP2A.eyebrowIndex = this.eyebrowIndex;
        avatarP2A.hatIndex = this.hatIndex;

        avatarP2A.skinColorValue = this.skinColorValue;
        avatarP2A.skinColorServerValues = Arrays.copyOf(this.skinColorServerValues, this.skinColorServerValues.length);
        avatarP2A.lipColorValue = this.lipColorValue;
        avatarP2A.lipColorServerValues = Arrays.copyOf(this.lipColorServerValues, this.lipColorServerValues.length);
        avatarP2A.irisColorValue = this.irisColorValue;
        avatarP2A.hairColorValue = this.hairColorValue;
        avatarP2A.glassesColorValue = this.glassesColorValue;
        avatarP2A.glassesFrameColorValue = this.glassesFrameColorValue;
        avatarP2A.beardColorValue = this.beardColorValue;
        avatarP2A.hatColorValue = this.hatColorValue;
        return avatarP2A;
    }

    public boolean compare(AvatarP2A avatarP2A) {
        return avatarP2A.hairIndex != this.hairIndex ||
                avatarP2A.glassesIndex != this.glassesIndex ||
                avatarP2A.clothesIndex != this.clothesIndex ||
                avatarP2A.beardIndex != this.beardIndex ||
                avatarP2A.eyelashIndex != this.eyelashIndex ||
                avatarP2A.eyebrowIndex != this.eyebrowIndex ||
                avatarP2A.hatIndex != this.hatIndex ||
                avatarP2A.skinColorValue != this.skinColorValue ||
                avatarP2A.lipColorValue != this.lipColorValue ||
                avatarP2A.irisColorValue != this.irisColorValue ||
                avatarP2A.hairColorValue != this.hairColorValue ||
                avatarP2A.glassesColorValue != this.glassesColorValue ||
                avatarP2A.glassesFrameColorValue != this.glassesFrameColorValue ||
                avatarP2A.beardColorValue != this.beardColorValue ||
                avatarP2A.hatColorValue != this.hatColorValue;
    }
}

