package com.vivwe.author.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vivwe.main.R;
import com.vivwe.personal.entity.TransactionRecordEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionRecordAdapter extends RecyclerView.Adapter<TransactionRecordAdapter.ViewHolder> {


    private Activity activity;
    private ArrayList<TransactionRecordEntity.TransactionRecord> records;

    public void setRecords(ArrayList<TransactionRecordEntity.TransactionRecord> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    public TransactionRecordAdapter(Activity activity) {
        this.activity = activity;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_author_transaction_record, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvContent.setText("购买了"+records.get(i).getNickname()+"特效模板");
        viewHolder.tvMoney.setText("+¥"+records.get(i).getPrice());
        viewHolder.tvTime.setText(records.get(i).getGmtOrderTime());
    }

    @Override
    public int getItemCount() {
        return records==null?0:records.size();
    }


}
