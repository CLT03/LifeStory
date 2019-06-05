package com.vivwe.video.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

    public VideoToShowCommendToCommentAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().circleCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setCommentEntities(ArrayList<CommentCommentEntity> commentEntities,int position) {
        this.commentEntities = commentEntities;
        this.position=position;
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
        View v = LayoutInflater.from(activity).inflate(R.layout.item_video_to_show_commend_to_commend, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity).load(commentEntities.get(i).getFromAvatar()).apply(requestOptions).into(holder.ivHead);
        holder.tvName.setText(commentEntities.get(i).getFromNickname());
        holder.tvContent.setText(commentEntities.get(i).getContent());
        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VideoToShowActivity)activity).setCommentData(1,
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
