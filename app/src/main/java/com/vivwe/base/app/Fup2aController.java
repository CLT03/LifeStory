package com.vivwe.base.app;

import android.content.Context;

import com.faceunity.p2a_art.constant.ColorConstant;
import com.faceunity.p2a_art.core.FUP2ARenderer;
import com.faceunity.p2a_art.core.P2AClientWrapper;
import com.faceunity.p2a_art.core.authpack;
import com.faceunity.p2a_art.web.OkHttpUtils;
import com.faceunity.p2a_helper.FUAuthCheck;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ahtor: super_link
 * date: 2019/2/27 09:39
 * remark:
 */
public class Fup2aController {

    private static Fup2aController instance;
    private Context context;

    private Fup2aController(Context context){
        this.context = context;
        OkHttpUtils.initOkHttpUtils(OkHttpUtils.initOkHttpClient(context));
        ColorConstant.init(context);
        closeAndroidPDialog();

        //TODO 初始化部分
        //初始化nama
        FUP2ARenderer.initFURenderer(context);
        //初始化P2A Client
        P2AClientWrapper.setupData(context);
        //初始化P2A Helper
        FUAuthCheck.fuP2ASetAuth(authpack.A());
    }

    /**
     * 解决部分机型 使用反射后 会弹出系统提示弹窗的问题
     */
    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init(final Context context) {
        if (instance == null) instance = new Fup2aController(context);
    }
}
