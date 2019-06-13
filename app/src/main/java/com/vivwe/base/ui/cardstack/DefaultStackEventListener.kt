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

<<<<<<< HEAD
        //Log.d("rae", "swipeEnd:$section-$distance")
=======
        Log.d("rae", "swipeEnd:$section-$distance")
>>>>>>> 6c7b0e53cb6702995fb197eb2be3181452c27ad1

        return distance > mThreshold
    }

    override fun swipeStart(section: Int, distance: Float): Boolean {

<<<<<<< HEAD
        //Log.d("rae", "swipeStart:$section-$distance")
=======
        Log.d("rae", "swipeStart:$section-$distance")
>>>>>>> 6c7b0e53cb6702995fb197eb2be3181452c27ad1
        return false
    }

    override fun swipeContinue(section: Int, distanceX: Float, distanceY: Float): Boolean {

<<<<<<< HEAD
       // Log.d("rae", "swipeContinue:$section-$distanceX-$distanceY")
=======
        Log.d("rae", "swipeContinue:$section-$distanceX-$distanceY")
>>>>>>> 6c7b0e53cb6702995fb197eb2be3181452c27ad1

        return false
    }

    override fun discarded(mIndex: Int, direction: Int) {

<<<<<<< HEAD
       // Log.d("rae", "discarded:$mIndex-$direction")
    }

    override fun topCardTapped() {
        //Log.d("rae", "topCardTapped")
=======
        Log.d("rae", "discarded:$mIndex-$direction")
    }

    override fun topCardTapped() {
        Log.d("rae", "topCardTapped")
>>>>>>> 6c7b0e53cb6702995fb197eb2be3181452c27ad1
    }
}