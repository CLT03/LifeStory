package com.mbs.sdk.db;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.mbs.sdk.core.SdkContext;

/**
 * author:zlcai
 * new date:2016-10-18
 * last date:2019-4-10
 * remark: 共享引用存储
 **/

public abstract class SharedPreferences {
	
//	public static Context context;
	private static String NAME = "LifeStory";


	private static android.content.SharedPreferences getSharePerference(){
		return SdkContext.getSdkContext().getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
	}
	
	public static String getValueByString(String key){
		return getSharePerference().getString(key, null);
	}
	
	public static int getValueByInt(String key){
		return getSharePerference().getInt(key, -1);
	}
	
	public static float getValueByFloat(String key) {
		return getSharePerference().getFloat(key, -1);
	}
	
	public static boolean getValueByBoolean(String key){
		return getSharePerference().getBoolean(key, false);
	}
	
	public static long getValueByLong(String key){
		return getSharePerference().getLong(key, -1);
	}
	
	public static void saveValueToSharePerference(String key, boolean value){
		Editor editor = getSharePerference().edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static void saveValueToSharePerference(String key, int value){
		Editor editor = getSharePerference().edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static void saveValueToSharePerference(String key, String value){
		Editor editor = getSharePerference().edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static void saveValueToSharePerference(String key, long value){
		Editor editor = getSharePerference().edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public static void clear(){
		Editor editor = getSharePerference().edit();
		editor.clear();
		editor.commit();
	}
	
}
