package com.vivwe.base.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.vivwe.main.R

/**
 * ahtor: super_link
 * date: 2019/5/23 14:12
 * remark:
 */
class CircleBarView(context: Context?) : View(context) {

//    lateinit var rPaint: Paint
    lateinit var progressPaint: Paint
    lateinit var tatalProgressPaint: Paint

    constructor(context: Context?, attrs: AttributeSet?) : this(context) {
        init(context!!, attrs!!)
    }

    private fun init(context: Context, attrs: AttributeSet ){
//        rPaint = Paint()
//        rPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
//        rPaint.setColor(Color.RED);

        progressPaint = Paint()
        progressPaint.style = Paint.Style.STROKE //只描边，不填充
        progressPaint.strokeWidth = context.resources.getDimension(R.dimen.x4)
        tatalProgressPaint.color = Color.BLUE
        progressPaint.setAntiAlias(true) //设置抗锯齿

        tatalProgressPaint = Paint()
        tatalProgressPaint.style = Paint.Style.STROKE
        progressPaint.strokeWidth = context.resources.getDimension(R.dimen.x4)
        tatalProgressPaint.color = Color.WHITE
        tatalProgressPaint.setAntiAlias(true)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas);
        var x:Float = 0f
        var y:Float = 0f
        var rectF = RectF(x, y, measuredWidth as Float, measuredWidth as Float);//建一个大小为300 * 300的正方形区域

        canvas.drawArc(rectF, 0f, 360f, false, tatalProgressPaint)
        canvas.drawArc(rectF,270f,180f,false,progressPaint);//这里角度0对应的是三点钟方向，顺时针方向递增
//        canvas.drawRect(rectF,rPaint);
    }

}