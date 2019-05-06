package com.vivwe.faceunity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.faceunity.p2a_art.constant.AvatarConstant;
import com.faceunity.p2a_art.entity.Scenes;
import com.vivwe.main.R;
import static com.vivwe.base.app.MyApplication.getContext;

/**
 * ahtor: super_link
 * date: 2019/5/6 09:20
 * remark:
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    Scenes[] scenes;

    public TestAdapter(Scenes[] scenes){
        this.scenes = scenes;
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(new ImageView(getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.image.setImageResource(scenes[position].resId);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mScenesSelectListener != null) {
                    mScenesSelectListener.onScenesSelectListener(scenes == AvatarConstant.SCENES_ART_ANIMATION, scenes[position]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return scenes.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView;
            image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            image.setLayoutParams(new ViewGroup.LayoutParams(getContext().getResources().getDimensionPixelSize(R.dimen.x280), ViewGroup.LayoutParams.MATCH_PARENT));
            int len = getContext().getResources().getDimensionPixelSize(R.dimen.x10);
            image.setPadding(len, 0, len, 0);
        }
    }

    private ScenesSelectListener mScenesSelectListener;

    public void setScenesSelectListener(ScenesSelectListener scenesSelectListener) {
        this.mScenesSelectListener = scenesSelectListener;
    }

    public interface ScenesSelectListener {
        void onScenesSelectListener(boolean isAnim, Scenes scenes);
    }
}
