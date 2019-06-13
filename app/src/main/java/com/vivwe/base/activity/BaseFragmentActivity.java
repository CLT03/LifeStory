package com.vivwe.base.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.vivwe.base.constant.Globals;

/**
 * ahtor: super_link
 * date: 2019/4/25 10:42
 * remark:
 */
public class BaseFragmentActivity extends FragmentActivity {

    private BaseFragmentActivity.BaseReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // 注册广播接收者
        receiver = new BaseFragmentActivity.BaseReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Globals.EXIT_APP);
        this.registerReceiver(receiver, filter);
    }



    public void setStatusBarColor(@ColorInt int color){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private class BaseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Globals.EXIT_APP)) {
                finish();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }
    }
}
