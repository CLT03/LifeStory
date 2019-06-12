package com.vivwe.base.ui.cardstack.animation

import android.animation.TypeEvaluator
import android.widget.RelativeLayout
import com.vivwe.base.ui.cardstack.CardUtils

/**
 * ahtor: super_link
 * date: 2019/6/12 14:50
 * remark:
 */
class RelativeLayoutParamsEvaluator: TypeEvaluator<RelativeLayout.LayoutParams> {
    override fun evaluate(fraction: Float, start: RelativeLayout.LayoutParams,
                          end: RelativeLayout.LayoutParams): RelativeLayout.LayoutParams {

        val result = CardUtils.cloneParams(start)
        result.leftMargin = result.leftMargin + ((end.leftMargin - start.leftMargin) * fraction).toInt()
        result.rightMargin = result.rightMargin + ((end.rightMargin - start.rightMargin) * fraction).toInt()
        result.topMargin = result.topMargin + ((end.topMargin - start.topMargin) * fraction).toInt()
        result.bottomMargin = result.bottomMargin + ((end.bottomMargin - start.bottomMargin) * fraction).toInt()
        return result
    }
}