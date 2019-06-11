package com.vivwe.personal.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.main.R;
import com.vivwe.personal.entity.AssetEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAssetsAdapter extends RecyclerView.Adapter<MyAssetsAdapter.ViewHolder> {

    private Activity activity;
    private List<AssetEntity> datas = new ArrayList<>();

    public MyAssetsAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setDatas(List<AssetEntity> datas){
        if(datas == null){
            this.datas.clear();
        } else {
            this.datas = datas;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.datas.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_personal_myassets, viewGroup, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {



    }


    class ViewHolder extends RecyclerView.ViewHolder {

        /** 图片 */
        @BindView(R.id.iv_image)
        ImageView imageIv;
        /** 有效时间 */
        @BindView(R.id.tv_time)
        TextView timeTv;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
