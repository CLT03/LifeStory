package com.vivwe.main.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReplyMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private Activity activity;
    int count;

    public ReplyMessageAdapter(Activity activity) {
        this.activity = activity;
        count = 20;
    }

    class ViewHolderLeft extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        ViewHolderLeft(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ViewHolderRight extends RecyclerView.ViewHolder {
        ViewHolderRight(View itemView) {
            super(itemView);
        }
    }

    public void setCount(int count) {
        this.count = count;
        notifyDataSetChanged();
    }

    public int getCount() {
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_reply_message_left, viewGroup, false);
        return new ViewHolderLeft(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolderLeft) {
           ((ViewHolderLeft) viewHolder).tvContent.setText("i"+i);
        }
    }


    @Override
    public int getItemCount() {
        return count;
    }


}
