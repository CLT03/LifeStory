package com.vivwe.personal.adapter;

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
import com.vivwe.personal.entity.TemplateEntity;
import com.vivwe.video.activity.TemplateDetailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCollectedDemoAdapter extends RecyclerView.Adapter<MyCollectedDemoAdapter.ViewHolder> {



    private Activity activity;
    private ArrayList<TemplateEntity.Template> templates;
    private RequestOptions requestOptions;

    public MyCollectedDemoAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setTemplates(ArrayList<TemplateEntity.Template> templates) {
        this.templates = templates;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_charge)
        ImageView ivCharge;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_personal_mycollected_demo, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity).load(templates.get(i).getImageUrl()).apply(requestOptions).into(holder.ivCover);
        holder.tvTitle.setText(templates.get(i).getTitle());
        if(Float.parseFloat(templates.get(i).getPrice())==0.0){
            holder.ivCharge.setVisibility(View.GONE);
        }else holder.ivCharge.setVisibility(View.VISIBLE);
        holder.ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, TemplateDetailActivity.class)
                        .putExtra("templateId",templates.get(holder.getAdapterPosition()).getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return templates==null?0:templates.size();
    }


}

