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

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.ViewHolder> {



    private Activity activity;
    private ArrayList<TemplateEntity.Template> templates;
    private RequestOptions requestOptions;
    private boolean ifEdit;//是否编辑
    private ArrayList<TemplateEntity.Template> waitDeleteTemplates;//要删除的模板

    public TemplateAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setTemplates(ArrayList<TemplateEntity.Template> templates) {
        this.templates = templates;
        if (waitDeleteTemplates == null) waitDeleteTemplates = new ArrayList<>();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_charge)
        ImageView ivCharge;
        @BindView(R.id.iv_choose)
        ImageView ivChoose;
        @BindView(R.id.tv_dynamic)
        TextView tvDynamic;

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
        if (Float.parseFloat(templates.get(i).getPrice()) == 0.0) {
            holder.ivCharge.setVisibility(View.GONE);
        } else holder.ivCharge.setVisibility(View.VISIBLE);
        if(templates.get(i).getStyle()==1){
            holder.tvDynamic.setText("标准");
        }else holder.tvDynamic.setText("动态");
        holder.ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, TemplateDetailActivity.class)
                        .putExtra("templateId", templates.get(holder.getAdapterPosition()).getId()));
            }
        });
        if (ifEdit) {
            holder.ivChoose.setVisibility(View.VISIBLE);
            if (waitDeleteTemplates.contains(templates.get(i)))
                holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_checked));
            else
                holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_check));
        } else holder.ivChoose.setVisibility(View.GONE);

        holder.ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (waitDeleteTemplates.contains(templates.get(holder.getAdapterPosition()))) {
                    holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_check));
                    waitDeleteTemplates.remove(templates.get(holder.getAdapterPosition()));
                } else {
                    holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_checked));
                    waitDeleteTemplates.add(templates.get(holder.getAdapterPosition()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return templates == null ? 0 : templates.size();
    }

    public void setIfEdit(boolean ifEdit) {
        this.ifEdit = ifEdit;
        notifyDataSetChanged();
    }

    public void allChoose(boolean ifAllChoose) {
        if (waitDeleteTemplates != null) {
            if (ifAllChoose) {
                waitDeleteTemplates.clear();
            } else {
                waitDeleteTemplates.clear();
                waitDeleteTemplates.addAll(templates);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getChooseIdList() {
        ArrayList<Integer> chooseIdList = new ArrayList<>();
        if (waitDeleteTemplates != null) {
            for (int i = 0; i < waitDeleteTemplates.size(); i++) {
                chooseIdList.add(waitDeleteTemplates.get(i).getStarId());
            }
        }
        return chooseIdList;
    }

    public void deleteSuccess() {
        templates.removeAll(waitDeleteTemplates);
        waitDeleteTemplates.clear();
        notifyDataSetChanged();
    }
}

