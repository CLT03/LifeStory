package com.vivwe.base.ui.cardstack

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.vivwe.base.ui.cardstack.animation.RelativeLayoutParamsEvaluator
import java.util.ArrayList
import java.util.HashMap

/**
 * ahtor: super_link
 * date: 2019/6/12 14:36
 * remark:
 */
class CardAnimator(viewCollection: ArrayList<View>, backgroundColor: Int, margin: Int) {

    private val DEBUG_TAG = "CardAnimator"

    companion object {
        final val TOP = 48
        final val BOTTOM = 80
    }




    private val REMOTE_DISTANCE = 2000
    private var mBackgroundColor: Int
    var mCardCollection: ArrayList<View>
    private var mRotation: Float = 0.toFloat()
    private var mLayoutsMap: HashMap<View, RelativeLayout.LayoutParams>? = null
    private val mRemoteLayouts = arrayOfNulls<RelativeLayout.LayoutParams>(5)
    private var baseLayout: RelativeLayout.LayoutParams? = null
    private var mStackMargin: Int = 0
    private var mGravity = BOTTOM
    private var mEnableRotation: Boolean = false // 是否允许旋转

    init {
        mCardCollection = viewCollection
        mBackgroundColor = backgroundColor
        mStackMargin = margin
        setup()
    }

    private fun setup() {
        mLayoutsMap = HashMap()

        for (v in mCardCollection) {
            //setup basic layout
            val params = v.layoutParams as RelativeLayout.LayoutParams
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP)
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT
            params.height = RelativeLayout.LayoutParams.WRAP_CONTENT

            if (mBackgroundColor != -1) {
                v.setBackgroundColor(mBackgroundColor)
            }

            v.layoutParams = params
        }

        baseLayout = mCardCollection[0].layoutParams as RelativeLayout.LayoutParams
        baseLayout = CardUtils.cloneParams(baseLayout!!)

    }

    fun initLayout() {
        val size = mCardCollection.size
        for (v in mCardCollection) {
            var index = mCardCollection.indexOf(v)
            if (index != 0) {
                index -= 1
            }
            val params = CardUtils.cloneParams(baseLayout!!)
            v.layoutParams = params

            CardUtils.scale(v, -(size - index - 1) * 5, mGravity)

            val margin = index * mStackMargin
            CardUtils.move(v, if (mGravity == TOP) -margin else margin, 0)
            v.rotation = 0f

            val paramsCopy = CardUtils.cloneParams(v.layoutParams as RelativeLayout.LayoutParams)
            mLayoutsMap!![v] = paramsCopy
        }

        setupRemotes()
    }

    /**
     * 设置方向，支持上、下。
     * 设置后调用[.initLayout] 来重新初始化布局
     *
     * @param gravity [.TOP] 向上 [.BOTTOM] 向下，默认值
     */
    fun setGravity(gravity: Int) {
        mGravity = gravity
    }

    private fun setupRemotes() {
        val topView = getTopView()
        mRemoteLayouts[0] = CardUtils.getMoveParams(topView, REMOTE_DISTANCE, -REMOTE_DISTANCE)
        mRemoteLayouts[1] = CardUtils.getMoveParams(topView, REMOTE_DISTANCE, REMOTE_DISTANCE)
        mRemoteLayouts[2] = CardUtils.getMoveParams(topView, -REMOTE_DISTANCE, -REMOTE_DISTANCE)
        mRemoteLayouts[3] = CardUtils.getMoveParams(topView, -REMOTE_DISTANCE, REMOTE_DISTANCE)
        mRemoteLayouts[4] = CardUtils.getMoveParams(topView, -REMOTE_DISTANCE, REMOTE_DISTANCE)

    }

    private fun getTopView(): View {
        return mCardCollection[mCardCollection.size - 1]
    }

    private fun moveToBack(child: View) {
        val parent = child.parent as ViewGroup
        if (null != parent) {
            parent.removeView(child)
            parent.addView(child, 0) // 移到最后一个
        }
    }

    // 卡片排序，抽出一个，底部上来一个
    private fun reorder() {

        val temp = getTopView()
        //RelativeLayout.LayoutParams tempLp = mLayoutsMap.get(mCardCollection.get(0));
        //mLayoutsMap.put(temp,tempLp);
        moveToBack(temp)

        for (i in mCardCollection.size - 1 downTo 1) {
            //View next = mCardCollection.get(i);
            //RelativeLayout.LayoutParams lp = mLayoutsMap.get(next);
            //mLayoutsMap.remove(next);
            val current = mCardCollection[i - 1]

            //current replace next
            mCardCollection[i] = current
            //mLayoutsMap.put(current,lp);

        }

        mCardCollection[0] = temp
    }

    // 销毁卡片
    fun discard(direction: Int, al: Animator.AnimatorListener?) {
        val `as` = AnimatorSet()
        val aCollection = ArrayList<Animator>()


        val topView = getTopView()
        val topParams = topView.layoutParams as RelativeLayout.LayoutParams
        val layout = CardUtils.cloneParams(topParams)
        val discardAnim = ValueAnimator.ofObject(RelativeLayoutParamsEvaluator(), layout, mRemoteLayouts[direction])

        discardAnim.addUpdateListener { value -> topView.layoutParams = value.animatedValue as RelativeLayout.LayoutParams }

        discardAnim.duration = 250
        aCollection.add(discardAnim)

        for (i in mCardCollection.indices) {
            val v = mCardCollection[i]

            if (v === topView) continue
            val nv = mCardCollection[i + 1]
            val layoutParams = v.layoutParams as RelativeLayout.LayoutParams
            val endLayout = CardUtils.cloneParams(layoutParams)
            val layoutAnim = ValueAnimator.ofObject(RelativeLayoutParamsEvaluator(), endLayout, mLayoutsMap!![nv])
            layoutAnim.duration = 250
            layoutAnim.addUpdateListener { value -> v.layoutParams = value.animatedValue as RelativeLayout.LayoutParams }
            aCollection.add(layoutAnim)
        }

        `as`.addListener(object : AnimatorListenerAdapter() {


            override fun onAnimationEnd(animation: Animator) {
                reorder()
                al?.onAnimationEnd(animation)
                mLayoutsMap = HashMap()
                for (v in mCardCollection) {
                    val params = v.layoutParams as RelativeLayout.LayoutParams
                    val paramsCopy = CardUtils.cloneParams(params)
                    mLayoutsMap!![v] = paramsCopy
                }

            }

        })


        `as`.playTogether(aCollection)
        `as`.start()
    }

    /**
     * 还原卡片位置
     */
    fun reverse(e1: MotionEvent, e2: MotionEvent) {
        val topView = getTopView()
        val rotationAnim = ValueAnimator.ofFloat(mRotation, 0f)
        rotationAnim.duration = 250
        rotationAnim.addUpdateListener { v -> topView.rotation = (v.animatedValue as Float).toFloat() }

        rotationAnim.start()

        for (v in mCardCollection) {
            val layoutParams = v.layoutParams as RelativeLayout.LayoutParams
            val endLayout = CardUtils.cloneParams(layoutParams)
            val layoutAnim = ValueAnimator.ofObject(RelativeLayoutParamsEvaluator(), endLayout, mLayoutsMap!![v])
            layoutAnim.duration = 100
            layoutAnim.addUpdateListener { value -> v.layoutParams = value.animatedValue as RelativeLayout.LayoutParams }
            layoutAnim.start()
        }

    }

    fun drag(e1: MotionEvent, e2: MotionEvent, distanceX: Float,
             distanceY: Float) {

        val topView = getTopView()
        val x_diff = (e2.rawX - e1.rawX).toInt()
        val y_diff = (e2.rawY - e1.rawY).toInt()
        val rotation_coefficient = 40f
        val layoutParams = topView.layoutParams as RelativeLayout.LayoutParams
        val topViewLayouts = mLayoutsMap!![topView]
        layoutParams.leftMargin = topViewLayouts!!.leftMargin + x_diff
        layoutParams.rightMargin = topViewLayouts.rightMargin - x_diff
        layoutParams.topMargin = topViewLayouts.topMargin + y_diff
        layoutParams.bottomMargin = topViewLayouts.bottomMargin - y_diff

        if (mEnableRotation) {
            mRotation = x_diff / rotation_coefficient
            topView.rotation = mRotation
            topView.layoutParams = layoutParams
        }

        //animate secondary views.
        //        for (View v : mCardCollection) {
        //            int index = mCardCollection.indexOf(v);
        //            if (v != getTopView() && index != 0) {
        //                LayoutParams l = CardUtils.scaleFrom(v, mLayoutsMap.get(v), (int) (Math.abs(x_diff) * 0.05), mGravity);
        //                CardUtils.moveFrom(v, l, 0, (int) (Math.abs(x_diff) * index * 0.05), mGravity);
        //            }
        //        }
    }

    fun setStackMargin(margin: Int) {
        mStackMargin = margin
    }


    fun isEnableRotation(): Boolean {
        return mEnableRotation
    }

    fun setEnableRotation(enableRotation: Boolean) {
        mEnableRotation = enableRotation
    }
}