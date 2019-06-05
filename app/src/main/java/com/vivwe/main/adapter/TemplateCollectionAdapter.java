package com.vivwe.main.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vivwe.main.R;
import com.vivwe.video.activity.AllTemplateActivity;
import com.vivwe.video.entity.CommentCommentEntity;
import com.vivwe.video.entity.TemplateTypeEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemplateCollectionAdapter extends RecyclerView.Adapter<TemplateCollectionAdapter.ViewHolder> {



    private Activity activity;
    private RequestOptions requestOptions;
    private ArrayList<TemplateTypeEntity> templateTypeEntities;


    public TemplateCollectionAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().circleCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setTemplateTypeEntities(ArrayList<TemplateTypeEntity> templateTypeEntities) {
        this.templateTypeEntities = templateTypeEntities;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_template_collection, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity).load(templateTypeEntities.get(i).getImageUrl()).apply(requestOptions).into(holder.ivCover);
        holder.tvTitle.setText(templateTypeEntities.get(i).getName());
        holder.ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity,AllTemplateActivity.class).putExtra("tag",holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return templateTypeEntities==null?0:templateTypeEntities.size();
    }


}
