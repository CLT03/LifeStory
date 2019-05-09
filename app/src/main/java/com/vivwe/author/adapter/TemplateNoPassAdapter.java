package com.vivwe.author.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vivwe.main.R;

public class TemplateNoPassAdapter extends RecyclerView.Adapter<TemplateNoPassAdapter.ViewHolder> {

    private Activity activity;

    public TemplateNoPassAdapter(Activity activity) {
        this.activity = activity;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_author_approve_no_pass, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


}
