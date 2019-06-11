package com.vivwe.personal.adapter;

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
import com.vivwe.personal.entity.AssetsEntity;
import com.vivwe.personal.entity.TemplateEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAssetsAdapter extends RecyclerView.Adapter<MyAssetsAdapter.ViewHolder> {


    private Activity activity;
    private RequestOptions requestOptions;
    private ArrayList<AssetsEntity.Assests> assests;
    private boolean ifEdit;//是否编辑
    private ArrayList<AssetsEntity.Assests> waitDeleteAssets;//要删除的素材

    public MyAssetsAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setAssests(ArrayList<AssetsEntity.Assests> assests) {
        this.assests = assests;
        if(waitDeleteAssets==null) waitDeleteAssets=new ArrayList<>();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_time)
        TextView tvTime;
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
        View v = LayoutInflater.from(activity).inflate(R.layout.item_personal_myassets, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity).load(assests.get(i).getUrl()).apply(requestOptions).into(holder.ivCover);
        holder.tvTime.setText(assests.get(i).getGmt_create());
        if(ifEdit){
            holder.ivChoose.setVisibility(View.VISIBLE);
            if(waitDeleteAssets.contains(assests.get(i))) holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_checked));
            else holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_check));
        }else holder.ivChoose.setVisibility(View.GONE);

        holder.ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waitDeleteAssets.contains(assests.get(holder.getAdapterPosition()))){
                    holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_check));
                    waitDeleteAssets.remove(assests.get(holder.getAdapterPosition()));
                }else{
                    holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_checked));
                    waitDeleteAssets.add(assests.get(holder.getAdapterPosition()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return assests==null?0:assests.size();
    }

    public void setIfEdit(boolean ifEdit) {
        this.ifEdit = ifEdit;
        notifyDataSetChanged();
    }

    public void allChoose(boolean ifAllChoose){
        if(waitDeleteAssets!=null){
            if(ifAllChoose){
                waitDeleteAssets.clear();
            }else{
                waitDeleteAssets.clear();
                waitDeleteAssets.addAll(assests);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getChooseIdList() {
        ArrayList<Integer> chooseIdList=new ArrayList<>();
        if(waitDeleteAssets!=null) {
            for (int i = 0; i < waitDeleteAssets.size(); i++) {
                chooseIdList.add(waitDeleteAssets.get(i).getId());
            }
        }
        return chooseIdList;
    }

    public void deleteSuccess(){
        assests.removeAll(waitDeleteAssets);
        waitDeleteAssets.clear();
        notifyDataSetChanged();
    }

    public ArrayList<String> getChooseUrlList() {
        ArrayList<String> chooseIdList=new ArrayList<>();
        if(waitDeleteAssets!=null) {
            for (int i = 0; i < waitDeleteAssets.size(); i++) {
                 chooseIdList.add(waitDeleteAssets.get(i).getUrl());
            }
        }
        return chooseIdList;
    }
}
