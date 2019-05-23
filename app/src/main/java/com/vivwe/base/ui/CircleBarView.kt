package com.vivwe.base.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * ahtor: super_link
 * date: 2019/5/23 14:12
 * remark:
 */
class CircleBarView(context: Context?) : View(context) {

    lateinit var rPaint: Paint
    lateinit var progressPaint: Paint

    constructor(context: Context?, attrs: AttributeSet?) : this(context) {

    }

    private fun init(context: Context, attrs: AttributeSet ){
        rPaint = Paint()
        rPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
        rPaint.setColor(Color.RED);

        progressPaint = Paint()
        progressPaint.setStyle(Paint.Style.STROKE) //只描边，不填充
        progressPaint.setColor(Color.BLUE)
        progressPaint.setAntiAlias(true) //设置抗锯齿
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas);

        var x:Float = 50f
        var y:Float = 50f
        var rectF = RectF(x, y,x+300,y+300);//建一个大小为300 * 300的正方形区域

        canvas.drawArc(rectF,0f,270f,false,progressPaint);//这里角度0对应的是三点钟方向，顺时针方向递增
        canvas.drawRect(rectF,rPaint);
    }

}