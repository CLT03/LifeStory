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

    private float y;//起始y绝对坐标
    private boolean intercept=true;//是否拦截滑动事件
    private StartY startY;//设置y值的接口
    private boolean firstReachToTop =true;//第一次recyclerview到顶部
    private boolean firstScroll =true;//第一次滑动


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.e("ououou", "onTouchEvent2");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y=event.getRawY();//只记一次起始位置，当recyclerview中的item绑定了onclick事情 会不走这里
                startY.setStartY(y);
               // firstScroll=false;
                Log.e("ououou", "ACTION_DOWN" + event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                if(firstScroll){
                    firstScroll=false;
                    y=event.getRawY();//只记一次起始位置
                }
                if (event.getRawY() > y ) {//手指位置大于起始位置 向下滑
                    Log.e("ououou","手指位置大于起始位置");
                    if(!canScrollVertically(-1)){//向下滑动到顶部
                        if(firstReachToTop){//第一次recyclerview到顶部 需要更新起始y
                            firstReachToTop =false;
                            startY.setStartY(event.getRawY());
                            Log.e("ououou", "ACTION_MOVE" + event.getRawY());
                        }
                        intercept = false;
                    }else intercept=true;
                } else {//手指位置小于起始位置 向上滑
                    intercept=true;
                    firstReachToTop =true;
                    Log.e("ououou","手指位置小于起始位置");
                }
                 //Log.e("ououou", "dsf" + event.getRawY()+" "+y);
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
