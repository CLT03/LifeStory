package com.vivwe.video.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vivwe.base.constant.Globals;
import com.vivwe.base.util.MiscUtil;
import com.vivwe.main.R;
import com.vivwe.video.activity.VideoToShowActivity;
import com.vivwe.video.entity.CommentCommentEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoToShowCommendToCommentAdapter extends RecyclerView.Adapter<VideoToShowCommendToCommentAdapter.ViewHolder> {



    private Activity activity;
    private RequestOptions requestOptions;
    private ArrayList<CommentCommentEntity> commentEntities;
    private int position;//记录上级的位置
    private int videoCommentUserId;//记录该视频评论用户的id 用来判断是不是回复他的


    public VideoToShowCommendToCommentAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().circleCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setCommentEntities(ArrayList<CommentCommentEntity> commentEntities, int position, int videoCommentUserId) {
        this.commentEntities = commentEntities;
        this.position = position;
        this.videoCommentUserId = videoCommentUserId;
        notifyDataSetChanged();
    }

    public void setCommentEntities(ArrayList<CommentCommentEntity> commentEntities) {
        this.commentEntities = commentEntities;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.view_click)
        View viewClick;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_video_to_show_commend_to_commend, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        CommentCommentEntity cce = commentEntities.get(i);
        Glide.with(activity).load(Globals.URL_QINIU+cce.getFromAvatar()).apply(requestOptions).into(holder.ivHead);
        holder.tvName.setText(cce.getFromNickname());
        if (/*cce.getToUId() == videoCommentUserId ||*/ cce.getToNickname() == null) {//回复的是上一级
            //设置评论内容加时间
            SpannableString sStr = new SpannableString(cce.getContent() + "  " + MiscUtil.getDistanceFromDate(cce.getGmtReplyTime()));
            sStr.setSpan(new AbsoluteSizeSpan(activity.getResources().getDimensionPixelSize(R.dimen.x24)), cce.getContent().length(), sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sStr.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), cce.getContent().length(), sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvContent.setText(sStr);
        } else {//回复的是同级的
            SpannableString sStr = new SpannableString("回复 " + cce.getToNickname() + ": " + cce.getContent() + "  " + MiscUtil.getDistanceFromDate(cce.getGmtReplyTime()));
            sStr.setSpan(new AbsoluteSizeSpan(activity.getResources().getDimensionPixelSize(R.dimen.x24)), 3, cce.getToNickname().length() + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sStr.setSpan(new ForegroundColorSpan(Color.parseColor("#555555")), 3, cce.getToNickname().length() + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sStr.setSpan(new AbsoluteSizeSpan(activity.getResources().getDimensionPixelSize(R.dimen.x24)), cce.getToNickname().length() + 5 + cce.getContent().length(), sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sStr.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), cce.getToNickname().length() + 5 + cce.getContent().length(), sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvContent.setText(sStr);
        }
        holder.viewClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VideoToShowActivity) activity).setCommentData(1,
                        commentEntities.get(holder.getAdapterPosition()).getVideoDiscussId(),
                        commentEntities.get(holder.getAdapterPosition()).getFromUId(),
                        commentEntities.get(holder.getAdapterPosition()).getFromNickname(),
                        position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentEntities == null ? 0 : commentEntities.size();
    }


}
