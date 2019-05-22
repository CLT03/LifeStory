package com.vivwe.video.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(@NonNull Context context) {
        super(context);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float y;
    private boolean intercept=true;
    private StartY startY;//起始y
    private boolean first=true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.e("ououou", "onTouchEvent2");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y=event.getRawY();
                startY.setStartY(y);
                Log.e("ououou", "ACTION_DOWN" + event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getRawY() > y ) {//手指位置大于起始位置
                    if(!canScrollVertically(-1)){
                        if(first){
                            first=false;
                            startY.setStartY(event.getRawY());
                            //Log.e("ououou", "ACTION_MOVE" + event.getRawY());
                        }
                        intercept = false;
                    }else intercept=true;
                } else {//手指位置小于起始位置
                    intercept=true;
                    first=true;
                    //Log.e("ououou", "true");
                    //Log.e("ououou","手指位置小于起始位置");
                }
                // Log.e("ououou", "dsf" + event.getRawY()+" "+y);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        if(intercept) return super.onTouchEvent(event);
        else return false;
    }


    public void setStartY(StartY startY) {
        this.startY = startY;
    }



    public interface StartY{
        void setStartY(float y);
    }


    public void setIntercept(boolean intercept) {
        this.intercept = intercept;
    }
}
