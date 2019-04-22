package com.mbs.sdk.core;


/**
 * @author:zlcai
 * @createrDate:2017/7/27 14:24
 * @lastTime:2019/47/9 14:40
 * @detail: sdk全局类
 **/
public class Globals {

	// 是否为调试模式
	public static boolean isDebug = true;
	// 基础网址
	//public static String BASE_API = "http://119.23.189.158:8080/LifeStroy/";

	// 一些网络状态常量
	public final static int STATE_OK = 200; // 正常
	public static final int STATE_UNAUTHORIZED = 401;
	public static final int STATE_FORBIDDEN = 403;
	public static final int STATE_NOT_FOUND = 404;
	public static final int STATE_REQUEST_TIMEOUT = 408;
	public static final int STATE_INTERNAL_SERVER_ERROR = 500; // 服务器错误
	public static final int STATE_BAD_GATEWAY = 502;
	public static final int STATE_SERVICE_UNAVAILABLE = 503;
	public final static int STATE_IO_ERROR = -1; // 读取服务器数据出错
	public final static int STATE_GATEWAY_TIMEOUT = 504; // 网络超时
	public final static int STATE_NO_CONTENT = 204; // 无网络连接
	public final static int STATE_ENCODING_UNSUPPORT = 998;
	public final static int STATE_ERROR = 999; // 未知错误
}
