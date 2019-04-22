package com.mbs.sdk.net.listener;

import com.mbs.sdk.net.msg.WebMsg;

/**
 * @author:zlcai
 * @new date:2016-12-5
 * @last date:2016-12-5
 * @remark:
 **/

public interface OnResultListener  {
	abstract void onWebUiResult(WebMsg webMsg);
}