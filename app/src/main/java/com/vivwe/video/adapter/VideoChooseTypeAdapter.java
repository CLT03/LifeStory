package com.vivwe.video.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.main.R;
import com.vivwe.video.entity.VideoTypeEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoChooseTypeAdapter extends RecyclerView.Adapter<VideoChooseTypeAdapter.ViewHolder> {



    private Activity activity;
    private ArrayList<VideoTypeEntity> arrayVideoType;
    private ImageView preChooseIv;//上一次选择的iv

    public VideoChooseTypeAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setArrayVideoType(ArrayList<VideoTypeEntity> arrayVideoType) {
        this.arrayVideoType = arrayVideoType;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.iv_choose)
        ImageView ivChoose;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_video_choose_type, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.tvType.setText(arrayVideoType.get(i).getName());
        holder.tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preChooseIv!=null) preChooseIv.setVisibility(View.GONE);
                holder.ivChoose.setVisibility(View.VISIBLE);
                preChooseIv=holder.ivChoose;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayVideoType != null ? arrayVideoType.size() : 0;
    }


}
