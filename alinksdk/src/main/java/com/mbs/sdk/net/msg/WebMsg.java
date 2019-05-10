package com.mbs.sdk.net.msg;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


/**
 * @author:zlcai
 * @createrDate:2017/7/27
 * @lastTime:2019/4/9
 * @detail: 返回网络实体
 **/

public class WebMsg {

	// 普通变量
    private Object data;
	private int webCode = 200; // 网络错误代码
	private String code;
	private String desc;


//	public String getData() {
//		return data != null ? new Gson().toJson(data) : null;
//	}

	public String getData(){
		return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(data);
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setWebCode(int webCode) {
		this.webCode = webCode;
	}

	public static WebMsg getSuccessed(){
		WebMsg webMsg = new WebMsg();
		webMsg.webCode = 200;
		webMsg.code = "success";
		return webMsg;
	}


	public static WebMsg getFailed(Throwable e){

		WebMsg webMsg = new WebMsg();
		if(e != null){
			if (e instanceof HttpException){             //HTTP错误
				HttpException httpException = (HttpException) e;
				webMsg.setWebCode(httpException.code());
			} else {
				webMsg.setWebCode(-1);
			}
		}

		return webMsg;
	}

	public int getWebCode() {
		return webCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 是否成功
	 * @return
	 */
	public boolean isSuccessed(){
		return code.equals("success") && webCode == 200;
	}
}
