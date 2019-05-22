package com.vivwe.personal.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vivwe.main.R;
import com.vivwe.video.activity.TemplateDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCollectedDemoAdapter extends RecyclerView.Adapter<MyCollectedDemoAdapter.ViewHolder> {


    private Activity activity;

    public MyCollectedDemoAdapter(Activity activity) {
        this.activity = activity;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity,TemplateDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }


}

