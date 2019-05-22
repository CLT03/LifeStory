package com.vivwe.video.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.video.adapter.VideoToShowCommendAdapter;
import com.vivwe.video.ui.MyRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 15:38
 * remark: 视频展示页
 */
public class VideoToShowActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_comment_number)
    TextView tvCommentNumber;
    @BindView(R.id.recyclerView)
    MyRecyclerView recyclerView;
    @BindView(R.id.edt_comment)
    EditText edtComment;
    @BindView(R.id.cl)
    ConstraintLayout cl;
    @BindView(R.id.view)
    View view1;
    private float y = 0;
    private TranslateAnimation mHiddenAction, mShowAction;
    private VideoToShowCommendAdapter adapterComment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_show);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        mHiddenAction.setDuration(500);
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mShowAction.setDuration(500);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterComment = new VideoToShowCommendAdapter(this);
        recyclerView.setAdapter(adapterComment);
        recyclerView.setStartY(new MyRecyclerView.StartY() {
            @Override
            public void setStartY(float startY) {
                y = startY;
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_attention, R.id.iv_share, R.id.iv_like, R.id.iv_comment, R.id.view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_attention:
                break;
            case R.id.iv_share:
                break;
            case R.id.iv_like:
                break;
            case R.id.iv_comment:
                cl.setVisibility(View.VISIBLE);
                cl.scrollTo(0, 0);
                cl.startAnimation(mShowAction);
                cl.setVisibility(View.VISIBLE);
                recyclerView.setIntercept(true);
                view1.setVisibility(View.VISIBLE);
                break;
            case R.id.view:
                Log.e("ououou", "sdf");
                cl.startAnimation(mHiddenAction);
                cl.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.e("ououou", "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(cl.getVisibility()==View.VISIBLE) {
                    if (event.getRawY() - y > 0)
                        cl.scrollTo(0, -(int) (event.getY() - y));
                    //else recyclerView.setCan(true);
                    //Log.e("ououou", "f" + event.getY() + " " + y);
                }
                break;
            case MotionEvent.ACTION_UP:
                //Log.e("ououou", "ACTION_UP");
                if(cl.getVisibility()==View.VISIBLE) {
                    if (event.getRawY() - y > getResources().getDimensionPixelOffset(R.dimen.x200)) {
                        cl.startAnimation(mHiddenAction);
                        cl.setVisibility(View.GONE);
                        view1.setVisibility(View.GONE);
                    } else if (event.getY() - y > 0)
                        cl.scrollTo(0, 0);
                    recyclerView.setIntercept(true);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
