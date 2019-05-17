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
    public final static String EXIT_APP = "exit_app";

    public static final String DIR_CACHE_BASE = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LifeStory" + File.separator;
    public static final String DIR_CACHE_BUNDLE =  DIR_CACHE_BASE + "bundle" + File.separator;
}
