package com.vivwe.base.ui.cardstack

import android.util.Log

/**
 * ahtor: super_link
 * date: 2019/6/12 14:37
 * remark:
 */
class DefaultStackEventListener(i:Int) : CardStack.CardEventListener {

    private var mThreshold: Float

     init {
         mThreshold = i.toFloat()
     }

    override fun swipeEnd(section: Int, distance: Float): Boolean {

        Log.d("rae", "swipeEnd:$section-$distance")

        return distance > mThreshold
    }

    override fun swipeStart(section: Int, distance: Float): Boolean {

        Log.d("rae", "swipeStart:$section-$distance")
        return false
    }

    override fun swipeContinue(section: Int, distanceX: Float, distanceY: Float): Boolean {

        Log.d("rae", "swipeContinue:$section-$distanceX-$distanceY")

        return false
    }

    override fun discarded(mIndex: Int, direction: Int) {

        Log.d("rae", "discarded:$mIndex-$direction")
    }

    override fun topCardTapped() {
        Log.d("rae", "topCardTapped")
    }
}