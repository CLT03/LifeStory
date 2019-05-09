package com.vivwe.base.ui.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.vivwe.main.R;

/**
 * ahtor: super_link
 * date: 2019/5/9 11:27
 * remark:
 */
public class LinearGradientTextView extends AppCompatTextView {

    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private int mViewWidth = 0;//文字的宽度
    private int mViewHeight = 0;//文字的高度
    private Rect mTextBound = new Rect();
    private boolean isVertrial;//默认是横向

    int startColor = 0;
    int endColor = 0;
    int textColor = 0;

    public LinearGradientTextView(Context context) {
        super(context);
    }

    public LinearGradientTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinearGradientTextView, defStyleAttr, defStyleRes);

        startColor = typedArray.getColor(R.styleable.LinearGradientTextView_startColor, Color.BLACK);
        endColor = typedArray.getColor(R.styleable.LinearGradientTextView_endColor, Color.BLACK);
        textColor = typedArray.getColor(R.styleable.LinearGradientTextView_endColor, Color.BLACK);
    }

    public LinearGradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinearGradientTextView);
        startColor = typedArray.getColor(R.styleable.LinearGradientTextView_startColor, Color.BLACK);
        endColor = typedArray.getColor(R.styleable.LinearGradientTextView_endColor, Color.BLACK);
        textColor = typedArray.getColor(R.styleable.LinearGradientTextView_endColor, Color.BLACK);
    }

    public LinearGradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinearGradientTextView);
        startColor = typedArray.getColor(R.styleable.LinearGradientTextView_startColor, Color.BLACK);
        endColor = typedArray.getColor(R.styleable.LinearGradientTextView_endColor, Color.BLACK);
        textColor = typedArray.getColor(R.styleable.LinearGradientTextView_endColor, Color.BLACK);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isVertrial) {
            mViewHeight = getMeasuredHeight();
        } else {
            mViewWidth = getMeasuredWidth();
        }
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        //前面4个参数分别表示渐变的开始x轴,开始y轴,结束的x轴,结束的y轴,mcolorList表示渐变的颜色数组
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, mViewHeight, new int[]{Color.RED,Color.GREEN}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
        //画出文字
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);
    }

}
