package com.vivwe.base.constant;

import android.os.Environment;

import java.io.File;

/**
 * ahtor: super_link
 * date: 2019/4/10 11:14
 * remark:
 */
public class Globals extends com.mbs.sdk.core.Globals {

    public static String APP_KEY = "7DLF3E218C10F53CB6A439D388E1B115";
    public static String URL_WEB = "http://112.74.164.53:8083/";
    public static String URL_QINIU = "http://assets.vivhist.com/";

    public final static String EXIT_APP = "exit_app";
    public static String APPLICTION_ID = "com.vivwe.main";

    public static final String DIR_CACHE_BASE = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LifeStory" + File.separator;
    public static final String DIR_CACHE_BUNDLE =  DIR_CACHE_BASE + "bundle" + File.separator;
}
