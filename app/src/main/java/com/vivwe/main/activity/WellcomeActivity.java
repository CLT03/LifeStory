package com.vivwe.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;


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

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                // 初始化人偶核心组件
//
//
//                emitter.onComplete();
//            }
//        }).observeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
                        Intent intent = new Intent();
                        intent.setClass(WellcomeActivity.this, MainActivity.class);
                        WellcomeActivity.this.startActivity(intent);
                        WellcomeActivity.this.finish();
//                    }
//                });
    }
}
