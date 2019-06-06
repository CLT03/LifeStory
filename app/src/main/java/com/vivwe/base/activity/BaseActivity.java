package com.vivwe.base.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vivwe.base.constant.Globals;
import com.vivwe.main.R;

/**
 * ahtor: super_link
 * date: 2019/4/23 13:22
 * remark:
 */
public class BaseActivity extends AppCompatActivity {

    private BaseReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        setTheme(R.style.AppImmersiveTheme);

        // 注册广播接收者
        receiver = new BaseReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Globals.EXIT_APP);
        this.registerReceiver(receiver, filter);
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
