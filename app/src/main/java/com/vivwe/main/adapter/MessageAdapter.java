package com.vivwe.main.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.main.R;
import com.vivwe.main.activity.ActivityMessageActivity;
import com.vivwe.main.activity.ReplyMessageActivity;
import com.vivwe.main.activity.SystemMessageActivity;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private Activity activity;

    public MessageAdapter(Activity activity) {
        this.activity = activity;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.iv_type)
        ImageView ivType;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_number)
        TextView tvNumber;
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
        View v = LayoutInflater.from(activity).inflate(R.layout.item_message, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        switch (i) {
            case 0:
                holder.ivType.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_mesage_notice));
                holder.tvTitle.setText("系统通知");
                break;
            case 1:
                holder.ivType.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_message_activity));
                holder.tvTitle.setText("活动通知");
                break;
            default:
                holder.ivType.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_attention_del));
                holder.tvTitle.setText("私信这一块");
                break;
        }
        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.getAdapterPosition()) {
                    case 0:
                        activity.startActivity(new Intent(activity,SystemMessageActivity.class));
                        break;
                    case 1:
                        activity.startActivity(new Intent(activity,ActivityMessageActivity.class));
                        break;
                    default:
                        activity.startActivity(new Intent(activity,ReplyMessageActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }


}
