package com.vivwe.main.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.main.adapter.ReplyMessageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 18:12
 * remark: 消息通知
 */
public class ReplyMessageActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ReplyMessageAdapter adapter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_message);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);//从底部开始显示
        linearLayoutManager.setReverseLayout(true);//列表翻转
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ReplyMessageAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration());
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setCount(adapter.getCount()+20);
                                refreshLayout.finishRefresh();
                            }
                        });
                    }
                }).start();
            }
        });
        linearLayoutManager.scrollToPositionWithOffset(0, Integer.MIN_VALUE);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
            int space = getResources().getDimensionPixelOffset(R.dimen.x30);
            if(parent.getChildAdapterPosition(view) == 0){
                outRect.bottom = space;
            }
        }
    }
}
