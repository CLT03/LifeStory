package com.vivwe.author.adapter;

import android.app.Activity;
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
import com.vivwe.personal.entity.TemplateEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemplatePublishAdapter extends RecyclerView.Adapter<TemplatePublishAdapter.ViewHolder> {


    private Activity activity;
    private ArrayList<TemplateEntity.Template> templates;
    private RequestOptions requestOptions;

    public TemplatePublishAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setTemplates(ArrayList<TemplateEntity.Template> templates) {
        this.templates = templates;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_material)
        TextView tvMaterial;
        @BindView(R.id.tv_use)
        TextView tvUse;
        @BindView(R.id.tv_collected)
        TextView tvCollected;
        @BindView(R.id.tv_click)
        TextView tvClick;
        @BindView(R.id.iv_choose)
        ImageView ivChoose;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_author_approve_publish, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Glide.with(activity).load(templates.get(i).getImageUrl()).apply(requestOptions).into(holder.ivCover);
        holder.tvClick.setText(String.valueOf(templates.get(i).getClickCount()));
        holder.tvCollected.setText(String.valueOf(templates.get(i).getCollectNumber()));
        holder.tvMaterial.setText(templates.get(i).getMax_material_count()+"个素材");
        holder.tvMoney.setText("¥"+templates.get(i).getPrice()+"元");
        holder.tvTime.setText(templates.get(i).getMax_duration()+"秒");
        holder.tvUse.setText(templates.get(i).getNumberOfUsers()+"次");
        holder.tvTitle.setText(templates.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return templates == null ? 0 : templates.size();
    }


}
