package com.vivwe.base.ui.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
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
    private int mViewWidth = 0;
    private Rect mTextBound = new Rect();

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
//        super.onDraw(canvas);

        int start = startColor == endColor ? textColor : startColor;
        int end = startColor == endColor ? textColor : endColor;

        mViewWidth = getMeasuredWidth();
        mPaint = getPaint();
        Paint.FontMetricsInt fmi = mPaint.getFontMetricsInt();
        float baseline = (float) (getMeasuredHeight()/2 - (fmi.bottom / 2.0 + fmi.top / 2.0));
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                new int[]{start, end},
                null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, baseline, mPaint);
    }


    /**
     * 设置渐变色
     * @param startColor 开始颜色
     * @param endColor 结束颜色
     */
    public void setTextColor(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        invalidate();
    }

    /**
     * 设置文紫颜色
     * @param textColor 颜色
     */
    public void setTextColor(int textColor) {
        this.startColor = textColor;
        this.endColor = textColor;
        this.textColor = textColor;
        invalidate();
    }

}
