package com.vivwe.base.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.vivwe.main.R

/**
 * ahtor: super_link
 * date: 2019/5/23 14:12
 * remark:
 */
class CircleBarView: View {

//    lateinit var rPaint: Paint
    lateinit var progressPaint: Paint
    lateinit var tatalProgressPaint: Paint
    var maxValue:Int = 100
    set(value) {
        field = value
        invalidate()
    }
    var currentValue:Int = 10
    set(value) {
        field = value
        invalidate()
    }

   constructor(context: Context?): this(context, null){

   }

    constructor(context: Context?, attrs: AttributeSet?): this(context, attrs, 0){

        init(context!!, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr){

    }


    private fun init(context: Context, attrs: AttributeSet? ){
//        rPaint = Paint()
//        rPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
//        rPaint.setColor(Color.RED);

        progressPaint = Paint()
        progressPaint.style = Paint.Style.STROKE //只描边，不填充
        progressPaint.strokeWidth = context.resources.getDimension(R.dimen.x8)
        progressPaint.color = Color.parseColor("#B35CFF")
        progressPaint.setAntiAlias(true) //设置抗锯齿

        tatalProgressPaint = Paint()
        tatalProgressPaint.style = Paint.Style.STROKE
        tatalProgressPaint.strokeWidth = context.resources.getDimension(R.dimen.x8)
        tatalProgressPaint.color = Color.WHITE
        tatalProgressPaint.setAntiAlias(true)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas);
        var x:Float = context.resources.getDimension(R.dimen.x4)
        var y:Float = context.resources.getDimension(R.dimen.x4)
        var rectF = RectF(x, y, measuredWidth.toFloat() - x, measuredWidth.toFloat() - y);//建一个大小为300 * 300的正方形区域

        var sweepAngle = currentValue.toFloat() * 360 / maxValue.toFloat()

        Log.v(">>>angle",sweepAngle.toString());

        canvas.drawArc(rectF, 0f, 360f, false, tatalProgressPaint)
        canvas.drawArc(rectF,-90f,sweepAngle.toFloat(),false,progressPaint);//这里角度0对应的是三点钟方向，顺时针方向递增
//        canvas.drawRect(rectF,rPaint);
    }

}