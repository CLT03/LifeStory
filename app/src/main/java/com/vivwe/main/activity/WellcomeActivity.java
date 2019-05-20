package com.vivwe.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.entity.UserToken;
import com.vivwe.main.R;
import com.vivwe.main.api.WebUserInfoApi;
import com.vivwe.main.entity.UserInfoEntity;


/**
 * ahtor: super_link
 * date: 2019/4/23 13:49
 * remark: 欢迎界面
 */
public class WellcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        UserToken userToken = UserCache.Companion.getUserToken();

        // 用户已经登录，获取用户信息
        if(userToken != null){
            HttpRequest.getInstance().excute(HttpRequest.create(WebUserInfoApi.class).getUserInfo(UserCache.Companion.getUserToken().getId()), new OnResultListener(){
                @Override
                public void onWebUiResult(WebMsg webMsg) {
                    if(webMsg.dataIsSuccessed()){
                        UserCache.Companion.setUserInfo(webMsg.getData(UserInfoEntity.class));
                    }

                    Intent intent = new Intent();
                    intent.setClass(WellcomeActivity.this, MainActivity.class);
                    WellcomeActivity.this.startActivity(intent);
                    WellcomeActivity.this.finish();
                }
            });
        } else { // 用户未登录，跳转到登录界面
            Intent intent = new Intent();
            intent.setClass(WellcomeActivity.this, LoginActivity.class);
            WellcomeActivity.this.startActivity(intent);
            WellcomeActivity.this.finish();
        }
    }
}
