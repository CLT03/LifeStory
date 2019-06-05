package com.vivwe.video.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.base.util.MiscUtil;
import com.vivwe.main.R;
import com.vivwe.personal.entity.UserEntity;
import com.vivwe.video.activity.VideoToShowActivity;
import com.vivwe.video.api.VideoApi;
import com.vivwe.video.entity.CommentCommentEntity;
import com.vivwe.video.entity.VideoComment;
import com.vivwe.video.entity.VideoCommentEntity;
import com.vivwe.video.ui.MyRecyclerViewInner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoToShowCommendAdapter extends RecyclerView.Adapter<VideoToShowCommendAdapter.ViewHolder> {


    private Activity activity;
    private RequestOptions requestOptions;
    private ArrayList<VideoComment> commentEntities;

    public VideoToShowCommendAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().circleCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setCommentEntities(ArrayList<VideoComment> commentEntities) {
        this.commentEntities = commentEntities;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.iv_like)
        ImageView ivLike;
        @BindView(R.id.tv_like)
        TextView tvLike;
        @BindView(R.id.recyclerView)
        MyRecyclerViewInner recyclerView;
        @BindView(R.id.tv_open)
        TextView tvOpen;
        @BindView(R.id.group_open)
        Group groupOpen;
        @BindView(R.id.cl)
        ConstraintLayout cl;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_video_to_show_comment, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity).load(commentEntities.get(i).getAvatar()).apply(requestOptions).into(holder.ivHead);
        holder.tvName.setText(commentEntities.get(i).getNickName());
        //设置评论内容加时间
        SpannableString sStr=new SpannableString(commentEntities.get(i).getContent()+"  "+MiscUtil.getDistanceFromDate(commentEntities.get(i).getGmtCreate()));
        sStr.setSpan(new AbsoluteSizeSpan(activity.getResources().getDimensionPixelSize(R.dimen.x24)), commentEntities.get(i).getContent().length(), sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), commentEntities.get(i).getContent().length(), sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvComment.setText(sStr);

        if (commentEntities.get(i).getIsLiked() == 1) {//是否点赞
            holder.ivLike.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_like_my));
        } else holder.ivLike.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_like_video));
        holder.tvLike.setText(String.valueOf(commentEntities.get(i).getLrCount()));

        //没展开且不为空
        if (commentEntities.get(i).getVdrList() != null && commentEntities.get(i).getVdrList().size() > 0) {//评论的回复
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.recyclerView.setLayoutManager(linearLayoutManager);
            final VideoToShowCommendToCommentAdapter adapter = new VideoToShowCommendToCommentAdapter(activity);
            holder.recyclerView.setAdapter(adapter);
            adapter.setCommentEntities(commentEntities.get(i).getVdrList(),holder.getAdapterPosition());
            holder.recyclerView.setVisibility(View.VISIBLE);
            if(!commentEntities.get(i).isOpen()) {
                //Log.e("ououou","sdf"+commentEntities.get(i).getVdrCount());
                if (commentEntities.get(i).getVdrCount() > 0) {
                    holder.tvOpen.setText("展开" + commentEntities.get(i).getVdrCount() + "条回复");
                    holder.groupOpen.setVisibility(View.VISIBLE);
                    //展开更多
                    holder.tvOpen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(commentEntities.get(holder.getAdapterPosition()).isOpen()){//已展开 那就是收起
                                //commentEntities.get(holder.getAdapterPosition()).getVdrList()
                            }else {//未展开 那就是展开
                                getMoreReply(commentEntities.get(holder.getAdapterPosition()).getVdrList().get(0).getVdrId(),
                                        commentEntities.get(holder.getAdapterPosition()).getVdId(),
                                        adapter, commentEntities.get(holder.getAdapterPosition()), holder.groupOpen);
                            }
                        }
                    });
                } else holder.groupOpen.setVisibility(View.GONE);
            }else {
                holder.tvOpen.setText("收起");
            }
        } else {
            holder.groupOpen.setVisibility(View.GONE);
            holder.recyclerView.setVisibility(View.GONE);
        }

        //点击回复视频评论
        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VideoToShowActivity)activity).setCommentData(1,
                        commentEntities.get(holder.getAdapterPosition()).getVdId(),
                        commentEntities.get(holder.getAdapterPosition()).getUserId(),
                        commentEntities.get(holder.getAdapterPosition()).getNickName(),
                        holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return commentEntities != null ? commentEntities.size() : 0;
    }


    /**
     *
     * @param vdrId 第一条回复的id
     * @param videoDiscussId 改视频评论的id
     * @param adapter
     * @param videoComment
     * @param group 展开更多的组
     */
    private void getMoreReply(int vdrId, int videoDiscussId, final VideoToShowCommendToCommentAdapter adapter,
                              final VideoComment videoComment, final Group group ){
        HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).getMoreReply(UserCache.Companion.getUserInfo().getId(),
                vdrId ,videoDiscussId), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    videoComment.setOpen(true);
                    ArrayList<CommentCommentEntity> commentCommentEntities=new GsonBuilder().create().fromJson(webMsg.getData(),new TypeToken<ArrayList<CommentCommentEntity>>(){}.getType());
                    for(int i=0;i<videoComment.getNewAddReplyNumber();i++){//去掉新加的回复
                        commentCommentEntities.remove(commentCommentEntities.size()-1);
                    }
                    videoComment.getVdrList().addAll(commentCommentEntities);
                    adapter.setCommentEntities(videoComment.getVdrList());
                    group.setVisibility(View.GONE);
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(activity, webMsg.getDesc(), 2000);
                }
            }
        });
    }

}
