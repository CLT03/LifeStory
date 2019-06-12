package com.vivwe.base.ui.cardstack

import android.content.Context
import android.support.v4.view.GestureDetectorCompat
import android.support.v4.view.MotionEventCompat
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ArrayAdapter

/**
 * ahtor: super_link
 * date: 2019/6/12 14:46
 * remark:
 */
class DragGestureDetector(adapter: ArrayAdapter<*>, context: Context, myDragListener: DragListener) {
    var DEBUG_TAG = "DragGestureDetector"
    private var mGestureDetector: GestureDetectorCompat
    private var mListener: DragListener?
    private var mStarted = false
    private var mOriginalEvent: MotionEvent? = null
    private var mAdapter: ArrayAdapter<*>?
    private var downX: Float = 0.toFloat()
    val upX: Float = 0.toFloat()
    val upY: Float = 0.toFloat()
    private var downY: Float = 0.toFloat()

    init {
        mGestureDetector = GestureDetectorCompat(context, MyGestureListener())
        mListener = myDragListener
        mAdapter = adapter
    }

    interface DragListener {
        fun onDragStart(e1: MotionEvent, e2: MotionEvent, distanceX: Float,
                        distanceY: Float): Boolean

        fun onDragContinue(e1: MotionEvent, e2: MotionEvent, distanceX: Float,
                           distanceY: Float): Boolean

        fun onDragEnd(e1: MotionEvent?, e2: MotionEvent): Boolean

        fun onTapUp(): Boolean
    }




    fun onTouchEvent(event: MotionEvent) {
        mGestureDetector.onTouchEvent(event)
        val action = MotionEventCompat.getActionMasked(event)
        when (action) {
            MotionEvent.ACTION_UP -> {

                downX = event.x
                downY = event.y
                Log.d(DEBUG_TAG, "Action was UP")
                if (mStarted) {
                    mListener!!.onDragEnd(mOriginalEvent, event)
                }
                mStarted = false
                //need to set this, quick tap will not generate drap event, so the
                //originalEvent may be null for case action_up
                //which lead to null pointer
                downX = event.x
                downY = event.y
                mOriginalEvent = event
            }
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                mOriginalEvent = event
            }
        }
    }

    internal inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float,
                              distanceY: Float): Boolean {
            if (mListener == null) return true
            if (mAdapter != null && mAdapter!!.count > 1) {
                if (!mStarted) {
                    mListener!!.onDragStart(e1, e2, distanceX, distanceY)
                    mStarted = true
                } else {
                    mListener!!.onDragContinue(e1, e2, distanceX, distanceY)
                }
                mOriginalEvent = e1
                return true
            }
            return false
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {

            return mListener!!.onTapUp()
        }
        //
        //        @Override
        //        public boolean onSingleTapConfirmed(MotionEvent e) {
        //            return super.onSingleTapConfirmed(e);
        //        }
    }
}