package com.vivwe.main.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.main.adapter.ActivityMessageAdapter;
import com.vivwe.main.adapter.SystemMessageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 18:12
 * remark: 系统消息
 */
public class SystemMessageActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    SystemMessageAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration());
        adapter=new SystemMessageAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
            int space = getResources().getDimensionPixelOffset(R.dimen.x28);
            if(parent.getChildAdapterPosition(view) == state.getItemCount() - 1){
                outRect.bottom = space;
            }
        }
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
