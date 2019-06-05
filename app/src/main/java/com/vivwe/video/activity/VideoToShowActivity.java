package com.vivwe.video.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.video.adapter.VideoToShowCommendAdapter;
import com.vivwe.video.api.VideoApi;
import com.vivwe.video.entity.CommentCommentEntity;
import com.vivwe.video.entity.VideoComment;
import com.vivwe.video.entity.VideoCommentEntity;
import com.vivwe.video.entity.VideoDetailEntity;
import com.vivwe.video.ui.MyRecyclerView;
import com.vivwe.video.ui.SoftKeyBoardListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 15:38
 * remark: 视频展示页
 */
public class VideoToShowActivity extends BaseActivity implements TextView.OnEditorActionListener {

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
    @BindView(R.id.group_comment)
    Group groupComment;
    @BindView(R.id.cl_all)
    ConstraintLayout clAll;
    @BindView(R.id.view_comment)
    View viewComment;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.iv_attention)
    ImageView ivAttention;
    private float y = 0;//起始y坐标
    private TranslateAnimation mHiddenAction, mShowAction;//评论隐藏、显示的动作
    private VideoToShowCommendAdapter adapterComment;
    private boolean once = true;//输入框显示的位置只需要计算一次
    private RequestOptions requestOptions;
    private int isLike;//是否点赞
    private VideoDetailEntity videoDetailEntity;
    private VideoCommentEntity videoCommentEntity;
    private int mCommentType;//0 是评论视频 1回复视频评论
    private int mVideoCommentId;//视频评论id
    private int mToUserId;//被回复的人的id
   // private String mEditHint;//输入框的提示
    private String mToUserNickName;//被回复的人的名字
    private int mVideoCommentIndex;//被回复视频评论的下标

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_show);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        requestOptions = new RequestOptions().circleCrop()
                .placeholder(getResources().getDrawable(R.drawable.ic_launcher_background));
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


        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                if (once) {
                    once = false;
                    ViewGroup.LayoutParams layoutParams = viewComment.getLayoutParams();
                    layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.x108) + height;
                    viewComment.setLayoutParams(layoutParams);
                }

                edtComment.setFocusable(true);
                edtComment.setFocusableInTouchMode(true);
                edtComment.requestFocus();
                //  Log.e("ououou", "显示" + height);
            }

            @Override
            public void keyBoardHide(int height) {
                //  Log.e("ououou", "隐藏 " + height);
                groupComment.setVisibility(View.GONE);
            }
        });
        edtComment.setOnEditorActionListener(this);
        getVideoDetail();
        getComment();
    }

    //获得详情
    private void getVideoDetail() {
        HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).getVideoDetail(UserCache.Companion.getUserInfo().getId(), getIntent().getIntExtra("videoId", 0)), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    videoDetailEntity = webMsg.getData(VideoDetailEntity.class);
                    Glide.with(VideoToShowActivity.this).load(videoDetailEntity.getAvatar()).apply(requestOptions).into(ivHead);
                    tvName.setText("#" + videoDetailEntity.getNickName());
                    tvTitle.setText(videoDetailEntity.getVideoTitle());
                    tvLike.setText(String.valueOf(videoDetailEntity.getLrcount()));
                    tvComment.setText(String.valueOf(videoDetailEntity.getVdcount()));
                    tvShare.setText(String.valueOf(videoDetailEntity.getShareCount()));
                    isLike = videoDetailEntity.getIsLiked();
                    if (isLike == 1) {//已点赞
                        ivLike.setImageDrawable(getResources().getDrawable(R.mipmap.icon_like_my));
                    }
                    if(videoDetailEntity.getIsSub()==1){//已关注
                        ivAttention.setVisibility(View.GONE);
                    }
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(VideoToShowActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_attention, R.id.iv_share, R.id.iv_like, R.id.iv_comment, R.id.view, R.id.tv_comment_guide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_attention:
                attention();
                break;
            case R.id.iv_share:
                startActivity(new Intent(this,MusicLibraryActivity.class));
                break;
            case R.id.iv_like:
                newLike();
                break;
            case R.id.iv_comment:
                cl.setVisibility(View.VISIBLE);
                cl.scrollTo(0, 0);
                cl.startAnimation(mShowAction);
                cl.setVisibility(View.VISIBLE);
                recyclerView.setIntercept(true);
                view1.setVisibility(View.VISIBLE);
                //getComment();
                break;
            case R.id.view:
                cl.startAnimation(mHiddenAction);
                cl.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                if (groupComment.getVisibility() == View.VISIBLE) {
                    InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm1.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                break;
            case R.id.tv_comment_guide:
                groupComment.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
    }

    //关注取消关注
    private void attention() {
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).attentionOrCancel(videoDetailEntity.getUserId()), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    ivAttention.setVisibility(View.GONE);
                    Toast.show(VideoToShowActivity.this, webMsg.getDesc(), 2000);
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(VideoToShowActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    //点赞取消点赞
    private void newLike() {
        HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).newLike(1, UserCache.Companion.getUserInfo().getId(),
                null, null, getIntent().getIntExtra("videoId", 0)), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    if (isLike == 1) {
                        isLike = 0;
                        ivLike.setImageDrawable(getResources().getDrawable(R.mipmap.icon_video_like));
                        videoDetailEntity.setLrcount(videoDetailEntity.getLrcount()-1);
                        tvLike.setText(String.valueOf(videoDetailEntity.getLrcount()));
                    } else {
                        isLike = 1;
                        ivLike.setImageDrawable(getResources().getDrawable(R.mipmap.icon_like_my));
                        videoDetailEntity.setLrcount(videoDetailEntity.getLrcount()+1);
                        tvLike.setText(String.valueOf(videoDetailEntity.getLrcount()));
                    }
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(VideoToShowActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    //获取评论
    private void getComment() {
        Log.e("ououou",getIntent().getIntExtra("videoId", 0)+" ");
        HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).getVideoCommentList(1, Integer.MAX_VALUE, UserCache.Companion.getUserInfo().getId(), getIntent().getIntExtra("videoId", 0)), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    videoCommentEntity= webMsg.getData(VideoCommentEntity.class);
                    adapterComment.setCommentEntities(videoCommentEntity.getVdList());
                    tvCommentNumber.setText(videoCommentEntity.getPageItem().getTotal()+"条评论");
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(VideoToShowActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.e("ououou", "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (cl.getVisibility() == View.VISIBLE) {
                    if (event.getRawY() - y > 0)
                        cl.scrollTo(0, -(int) (event.getY() - y));
                }
                break;
            case MotionEvent.ACTION_UP:
                if (cl.getVisibility() == View.VISIBLE) {
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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //判断是否是“发送”键
        if(actionId == EditorInfo.IME_ACTION_SEND){
            if(mCommentType==0)
              newComment();
            else newReplyComment();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }

    private void newComment(){
        final String content = edtComment.getText().toString();
        if(edtComment.getText().toString().length()>0) {
            HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).newComment(content,
                    UserCache.Companion.getUserInfo().getId(), getIntent().getIntExtra("videoId", 0)),
                    new OnResultListener() {
                        @Override
                        public void onWebUiResult(WebMsg webMsg) {
                            if (webMsg.dataIsSuccessed()) {
                                VideoComment videoComment=new VideoComment();
                                videoComment.setVdId(getIntent().getIntExtra("videoId", 0));
                                videoComment.setUserId(UserCache.Companion.getUserInfo().getId());
                                videoComment.setContent(content);
                                videoComment.setNickName(UserCache.Companion.getUserInfo().getNickname());
                                videoComment.setAvatar(UserCache.Companion.getUserInfo().getAvatar());
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                                Date date = new Date(System.currentTimeMillis());
                                videoComment.setGmtCreate(simpleDateFormat.format(date));
                                videoCommentEntity.getVdList().add(videoComment);
                                adapterComment.setCommentEntities(videoCommentEntity.getVdList());
                                videoCommentEntity.getPageItem().setTotal(videoCommentEntity.getPageItem().getTotal()+1);
                                tvCommentNumber.setText(videoCommentEntity.getPageItem().getTotal()+"条评论");
                                tvComment.setText(String.valueOf(videoCommentEntity.getPageItem().getTotal()));
                            } else if (webMsg.netIsSuccessed()) {
                                Toast.show(VideoToShowActivity.this, webMsg.getDesc(), 2000);
                            }
                        }
                    });
        }else Toast.show(this,"说点啥",2000);
    }

    public void newReplyComment(){
        final String content = edtComment.getText().toString();
        if(edtComment.getText().toString().length()>0) {
            HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).newReplyComment(content,
                    UserCache.Companion.getUserInfo().getId(),mToUserId, mVideoCommentId),
                    new OnResultListener() {
                        @Override
                        public void onWebUiResult(WebMsg webMsg) {
                            if (webMsg.dataIsSuccessed()) {
                                CommentCommentEntity commentCommentEntity=new CommentCommentEntity();
                                commentCommentEntity.setVideoDiscussId(mVideoCommentId);
                                commentCommentEntity.setFromUId(UserCache.Companion.getUserInfo().getId());
                                commentCommentEntity.setFromNickname(UserCache.Companion.getUserInfo().getNickname());
                                commentCommentEntity.setFromAvatar(UserCache.Companion.getUserInfo().getAvatar());
                                commentCommentEntity.setToUId(mToUserId);
                                commentCommentEntity.setToNickname(mToUserNickName);
                                commentCommentEntity.setContent(content);
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                                Date date = new Date(System.currentTimeMillis());
                                commentCommentEntity.setGmtReplyTime(simpleDateFormat.format(date));
                                //videoCommentEntity.getVdList().get(mVideoCommentIndex).setVdrCount(videoCommentEntity.getVdList().get(mVideoCommentIndex).getVdrCount()+1);//刷新展开还有多少条
                                videoCommentEntity.getVdList().get(mVideoCommentIndex).getVdrList().add(commentCommentEntity);
                                adapterComment.setCommentEntities(videoCommentEntity.getVdList());
                                //videoCommentEntity.getPageItem().setTotal(videoCommentEntity.getPageItem().getTotal()+1);
                                //tvCommentNumber.setText(videoCommentEntity.getPageItem().getTotal()+"条评论");
                                //tvComment.setText(String.valueOf(videoCommentEntity.getPageItem().getTotal()));
                            } else if (webMsg.netIsSuccessed()) {
                                Toast.show(VideoToShowActivity.this, webMsg.getDesc(), 2000);
                            }
                        }
                    });
        }else Toast.show(this,"说点啥",2000);
    }

    //在列表里面点击回复时需要先设置好相关信息
    public void setCommentData(int mCommentType,int mVideoCommentId,int mToUserId,String mToUserNickName,int mVideoCommentIndex){
        this.mCommentType=mCommentType;
        this.mVideoCommentId=mVideoCommentId;
        this.mToUserId=mToUserId;
        this.mToUserNickName=mToUserNickName;
        this.mVideoCommentIndex=mVideoCommentIndex;
        Log.e("ououo"," "+mToUserId+" "+mVideoCommentId);
        edtComment.setHint("回复"+mToUserNickName);
        edtComment.setText(null);
        if(groupComment.getVisibility()==View.GONE) {
            groupComment.setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
