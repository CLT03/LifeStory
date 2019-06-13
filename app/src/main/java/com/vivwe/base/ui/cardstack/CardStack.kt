package com.vivwe.base.ui.cardstack

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.database.DataSetObserver
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.vivwe.main.R
import java.util.*

/**
 * ahtor: super_link
 * date: 2019/6/12 14:35
 * remark:
 */
class CardStack : RelativeLayout {

    private val mContext: Context
    private var mEnableRotation: Boolean = false
    private var mGravity: Int = 0
    private var mColor = -1
    private var mIndex = 0
    private var mNumVisible = 4
    private var canSwipe = true
    private var mAdapter: ArrayAdapter<*>? = null
    private val mOnTouchListener: View.OnTouchListener? = null
    private var mCardAnimator: CardAnimator? = null
    private var mEnableLoop = true// 是否允许循环滚动


    private var mEventListener: CardEventListener = DefaultStackEventListener(300)
    private var mContentResource = 0
    private var mMargin: Int = 0
    private val currentIndex: Int = 0
    private var mTouchStartX: Float = 0.toFloat()
    private var mTouchStartY: Float = 0.toFloat()
    private var x: Float? = 0.toFloat()
    private var y: Float? = 0.toFloat()
    internal var state: Int = 0
    private var StartX: Float = 0.toFloat()
    private var StartY: Float = 0.toFloat()
    private var dd: DragGestureDetector? = null



    constructor(context: Context): super(context){
        this.mContext = context
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.mContext = context
        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.CardStack)

            mColor = array.getColor(R.styleable.CardStack_card_backgroundColor, mColor)
            mGravity = array.getInteger(R.styleable.CardStack_card_gravity, Gravity.BOTTOM)
            mEnableRotation = array.getBoolean(R.styleable.CardStack_card_enable_rotation, false)
            mNumVisible = array.getInteger(R.styleable.CardStack_card_stack_size, mNumVisible)
            mEnableLoop = array.getBoolean(R.styleable.CardStack_card_enable_loop, mEnableLoop)
            mMargin = array.getDimensionPixelOffset(R.styleable.CardStack_card_margin, 5)
            array.recycle()
        }

        //get attrs assign minVisiableNum
        for (i in 0 until mNumVisible) {
            addContainerViews(false)
        }
        setupAnimation()
    }


    interface CardEventListener {
        //section
        // 0 | 1
        //--------
        // 2 | 3
        // swipe distance, most likely be used with height and width of a view ;

        fun swipeEnd(section: Int, distance: Float): Boolean

        fun swipeStart(section: Int, distance: Float): Boolean

        fun swipeContinue(section: Int, distanceX: Float, distanceY: Float): Boolean

        fun discarded(mIndex: Int, direction: Int)

        fun topCardTapped()
    }

    fun discardTop(direction: Int) {
        mCardAnimator!!.discard(direction, object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(arg0: Animator) {
                mCardAnimator!!.initLayout()
                mIndex++
                loadLast()

                viewCollection[0].setOnTouchListener(null)
                viewCollection[viewCollection.size - 1].setOnTouchListener(mOnTouchListener)
                mEventListener.discarded(mIndex - 1, direction)
            }
        })
    }

    /**
     * 设置方向，支持上、下。
     * 设置后调用[.reset] 来重新初始化布局
     *
     * @param gravity [CardAnimator.TOP] 向上 [CardAnimator.BOTTOM] 向下，默认值
     */
    fun setStackGravity(gravity: Int) {
        mGravity = gravity
    }

    /**
     * 获取当前方向
     *
     * @return
     */
    fun getStackGravity(): Int {
        return mGravity
    }

    /**
     * 是否允许旋转
     *
     *
     * 设置后调用[.reset] 来重新初始化布局
     *
     * @param enableRotation
     */
    fun setEnableRotation(enableRotation: Boolean) {
        mEnableRotation = enableRotation
    }

    /**
     * 是否循环滚动
     * 设置后调用[.reset] 来重新初始化布局
     *
     * @param enableLoop
     */
    fun setEnableLoop(enableLoop: Boolean) {
        mEnableLoop = enableLoop
    }

    /**
     * 是否允许旋转
     *
     * @return
     */
    fun isEnableRotation(): Boolean {
        return mEnableRotation
    }

    /**
     * 是否循环滚动
     *
     * @return
     */
    fun isEnableLoop(): Boolean {
        return mEnableLoop
    }

    fun getCurrIndex(): Int {
        //sync?
        return mIndex
    }

    private fun addContainerViews(anim: Boolean) {
        val v = FrameLayout(context)
        viewCollection.add(v)
        addView(v)
        if (anim) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.undo_anim)
            v.startAnimation(animation)
        }
    }

    fun setStackMargin(margin: Int) {
        mMargin = margin
        mCardAnimator!!.setStackMargin(mMargin)
        mCardAnimator!!.initLayout()
    }

    fun getStackMargin(): Int {
        return mMargin
    }


    fun setContentResource(res: Int) {
        mContentResource = res
    }

    fun setCanSwipe(can: Boolean) {
        this.canSwipe = can
    }

    fun reset(resetIndex: Boolean) {
        reset(true, false)
    }

    private fun reset(resetIndex: Boolean, animFirst: Boolean) {
        if (resetIndex) mIndex = 0
        removeAllViews()
        viewCollection.clear()
        for (i in 0 until mNumVisible) {
            addContainerViews(i == mNumVisible - 1 && animFirst)
        }
        setupAnimation()
        loadData()
    }

    fun setVisibleCardNum(visiableNum: Int) {
        mNumVisible = visiableNum
        if (mNumVisible >= mAdapter!!.count) {
            mNumVisible = mAdapter!!.count
        }
        reset(false)
    }

    fun setThreshold(t: Int) {
        mEventListener = DefaultStackEventListener(t)
    }

    fun setListener(cel: CardEventListener) {
        mEventListener = cel
    }

    private fun setupAnimation() {
        val cardView = viewCollection[viewCollection.size - 1]
        mCardAnimator = CardAnimator(viewCollection, mColor, mMargin)
        mCardAnimator!!.setGravity(mGravity)
        mCardAnimator!!.setEnableRotation(mEnableRotation)
        //mCardAnimator.setStackMargin(mMargin);
        mCardAnimator!!.initLayout()


        //        mOnTouchListener = new OnTouchListener() {
        //
        //            private static final String DEBUG_TAG = "MotionEvents";
        //
        //            @Override
        //            public boolean onTouch(View view, MotionEvent event) {
        //                x = event.getRawX();
        //                y = event.getRawY() - 25; // 25是系统状态栏的高度
        //                Log.i("currP", "currX" + x + "====currY" + y);// 调试信息
        //                switch (event.getAction()) {
        //                    case MotionEvent.ACTION_DOWN:
        //                        state = MotionEvent.ACTION_DOWN;
        //                        StartX = x;
        //                        StartY = y;
        //                        // 获取相对View的坐标，即以此View左上角为原点
        //                        mTouchStartX = event.getX();
        //                        mTouchStartY = event.getY();
        //                        Log.i("startP", "startX" + mTouchStartX + "====startY"
        //                                + mTouchStartY);// 调试信息
        //
        //                        break;
        //                    case MotionEvent.ACTION_MOVE:
        //                        state = MotionEvent.ACTION_MOVE;
        ////                        updateViewPosition();
        //                        break;
        //
        //                    case MotionEvent.ACTION_UP:
        //                        state = MotionEvent.ACTION_UP;
        //
        ////                        updateViewPosition();
        //                        //关键部分：移动距离较小，视为onclick点击行为
        //                        if (Math.abs(x - StartX) <= 2 && Math.abs(y - StartY) <= 2){
        //                            return CardStack.super.onTouchEvent(event);
        //                        }
        //                        mTouchStartX = mTouchStartY = 0;
        //                        break;
        //                }
        //                dd.onTouchEvent(event);
        //                return true;
        //            }
        //
        //        };
        //        cardView.setOnTouchListener(mOnTouchListener);

    }

    internal var downX: Float = 0.toFloat()
    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        return false
    }

    internal var isOne = false

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        x = event.rawX
        y = event.rawY - 25 // 25是系统状态栏的高度
        Log.i("currP", "currX$x====currY$y")// 调试信息
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                state = MotionEvent.ACTION_DOWN
                StartX = x!!
                StartY = y!!
                // 获取相对View的坐标，即以此View左上角为原点
                mTouchStartX = event.x
                mTouchStartY = event.y
                Log.i("startP", "startX" + mTouchStartX + "====startY"
                        + mTouchStartY)// 调试信息
            }
            MotionEvent.ACTION_MOVE -> state = MotionEvent.ACTION_MOVE

            MotionEvent.ACTION_UP -> {
                state = MotionEvent.ACTION_UP

                //                        updateViewPosition();
                //关键部分：移动距离较小，视为onclick点击行为
                if (Math.abs(x!! - StartX) <= 5 && Math.abs(y!! - StartY) <= 5 || mAdapter!!.count <= 1) {
                    val position = getCurrIndex() % mAdapter!!.count
                    Toast.makeText(mContext, "点击了 ：$position", Toast.LENGTH_SHORT).show()
                    return true
                }
            }
        }//                        updateViewPosition();
        dd!!.onTouchEvent(event)
        return true
    }

    internal var clickListener: View.OnClickListener = OnClickListener { }

    private val mOb = object : DataSetObserver() {
        override fun onChanged() {
            reset(false)
        }
    }


    //ArrayList

    internal var viewCollection = ArrayList<View>()



    fun setAdapter(adapter: ArrayAdapter<*>) {
        if (mAdapter != null) {
            mAdapter!!.unregisterDataSetObserver(mOb)
        }
        mAdapter = adapter
        adapter.registerDataSetObserver(mOb)
        dd = DragGestureDetector(mAdapter!!, this@CardStack.context, object : DragGestureDetector.DragListener {
            override fun onDragEnd(e1: MotionEvent?, e2: MotionEvent): Boolean {
                parent.requestDisallowInterceptTouchEvent(true)
                val x1 = e1!!.rawX
                val y1 = e1!!.rawY
                val x2 = e2!!.rawX
                val y2 = e2!!.rawY
                val distance = CardUtils.distance(x1, y1, x2, y2)
                val direction = CardUtils.direction(x1, y1, x2, y2)

                val discard = mEventListener.swipeEnd(direction, distance)
                if (discard) {
                    if (canSwipe) {
                        mCardAnimator!!.discard(direction, object : AnimatorListenerAdapter() {

                            override fun onAnimationEnd(arg0: Animator) {

                                mCardAnimator!!.initLayout()
                                mIndex++
                                mEventListener.discarded(mIndex, direction)
                                //
                                //mIndex = mIndex%mAdapter.getCount();
                                loadLast()
                                startTimer()
                                //                                viewCollection.get(0).setOnTouchListener(null);
                                //                                viewCollection.get(viewCollection.size() - 1)
                                //                                        .setOnTouchListener(mOnTouchListener);
                            }

                        })
                    }
                } else {
                    if (canSwipe) {

                        mCardAnimator!!.reverse(e1, e2)
                    }
                }
                return true
            }


            override fun onDragStart(e1: MotionEvent, e2: MotionEvent,
                            distanceX: Float, distanceY: Float): Boolean {
                parent.requestDisallowInterceptTouchEvent(true)
                if (canSwipe) {
                    mCardAnimator!!.drag(e1, e2, distanceX, distanceY)
                }
                stopTimer()
                val x1 = e1.rawX
                val y1 = e1.rawY
                val x2 = e2.rawX
                val y2 = e2.rawY
                val direction = CardUtils.direction(x1, y1, x2, y2)
                val distance = CardUtils.distance(x1, y1, x2, y2)
                mEventListener.swipeStart(direction, distance)
                return true
            }

            override fun onDragContinue(e1: MotionEvent, e2: MotionEvent,
                               distanceX: Float, distanceY: Float): Boolean {
                parent.requestDisallowInterceptTouchEvent(true)
                val x1 = e1.rawX
                val y1 = e1.rawY
                val x2 = e2.rawX
                val y2 = e2.rawY
                val direction = CardUtils.direction(x1, y1, x2, y2)
                if (canSwipe) {
                    mCardAnimator!!.drag(e1, e2, distanceX, distanceY)
                }
                mEventListener.swipeContinue(direction, Math.abs(x2 - x1), Math.abs(y2 - y1))
                return true
            }




            override fun onTapUp(): Boolean {
                mEventListener.topCardTapped()
                return true
            }
        }
        )
        loadData()
    }


    fun getAdapter(): ArrayAdapter<*>? {
        return mAdapter
    }

    fun getTopView(): View {
        return (viewCollection[viewCollection.size - 1] as ViewGroup).getChildAt(0)
    }

    private fun loadData() {
        for (i in mNumVisible - 1 downTo 0) {
            val parent = viewCollection[i] as ViewGroup
            val index = mIndex + mNumVisible - 1 - i
            if (index > mAdapter!!.count - 1) {
                parent.visibility = View.GONE

            } else {
                val child = mAdapter!!.getView(index, getContentView(), this)
                parent.addView(child)
                parent.visibility = View.VISIBLE
            }
        }
    }

    private fun getContentView(): View? {
        var contentView: View? = null
        if (mContentResource != 0) {
            val lf = LayoutInflater.from(context)
            contentView = lf.inflate(mContentResource, null)
        }
        return contentView

    }

    // 加载下一个
    private fun loadLast() {
        val parent = viewCollection[0] as ViewGroup
        var lastIndex = mNumVisible - 1 + mIndex

        // 超出索引
        if (lastIndex > mAdapter!!.count - 1) {
            if (mEnableLoop && mAdapter!!.count > 0) {
                // 循环处理
                lastIndex = lastIndex % mAdapter!!.count
            } else {
                parent.visibility = View.GONE
                return
            }
        }

        val child = mAdapter!!.getView(lastIndex, getContentView(), parent)
        parent.removeAllViews()
        parent.addView(child)
    }

    // 加载下一个
    private fun loadAnimaLast() {
        val parent = viewCollection[0] as ViewGroup
        var lastIndex = mNumVisible - 1 + mCurrent

        // 超出索引
        if (lastIndex > mAdapter!!.count - 1) {
            if (mEnableLoop) {
                // 循环处理
                lastIndex = lastIndex % mAdapter!!.count
            } else {
                parent.visibility = View.GONE
                return
            }
        }

        val child = mAdapter!!.getView(lastIndex, getContentView(), parent)
        parent.removeAllViews()
        parent.addView(child)
    }

    /**
     * 获取可见卡片个数
     *
     * @return
     */
    fun getVisibleCardNum(): Int {
        return mNumVisible
    }

    fun undo() {
        if (mIndex == 0) return
        mIndex--
        reset(false, true)
    }

    internal var mCurrent = viewCollection.size - 1
    fun exitWithAnimation() {
        val childView = getChildAt(mCurrent)
        val direction = CardUtils.direction(0f, 200f, 0f, 500f)
        val distance = CardUtils.distance(0f, 200f, 0f, 200f)
        //        mEventListener.swipeStart(direction, distance);
        animationSet = AnimatorSet()
        animationSet!!.interpolator = AccelerateInterpolator()


        val translationY = childView.translationY
        animationSet!!.playTogether(
                ObjectAnimator.ofFloat(childView, "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(childView, "translationY", translationY, childView.translationY + 150 * getsinValue(0f,
                        100f).toFloat()))

        animationSet!!.setDuration(800).start()

        animationSet!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {

                mCardAnimator!!.discard(CardUtils.DIRECTION_BOTTOM, object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(arg0: Animator) {
                        childView.translationY = translationY
                        childView.alpha = 1f
                        mCardAnimator!!.initLayout()
                        mIndex++
                        mEventListener.discarded(mIndex, CardUtils.DIRECTION_BOTTOM)
                        //
                        //mIndex = mIndex%mAdapter.getCount();
                        loadLast()
                        //
                        //                                viewCollection.get(0).setOnTouchListener(null);
                        //                                viewCollection.get(viewCollection.size() - 1)
                        //                                        .setOnTouchListener(mOnTouchListener);
                    }

                })
                //                        mCardAnimator.initLayout();
                //                        mEventListener.discarded(mCurrent, DIRECTION_BOTTOM_LEFT);
                //                        mCurrent--;
                //                        //mIndex = mIndex%mAdapter.getCount();
                //                        loadLast();
                //
                //                        viewCollection.get(0).setOnTouchListener(null);
                //                        viewCollection.get(viewCollection.size() - 1)
                //                                .setOnTouchListener(mOnTouchListener);


            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })


    }

    private var mRunnable: StackRunnable? = null
    private var animationSet: AnimatorSet? = null
    internal var mIsAnimation: Boolean = false

    private inner class StackRunnable : Runnable {

        override fun run() {
            exitWithAnimation()
        }
    }

    private val mHandler = Handler(Looper.getMainLooper())

    private fun getsinValue(x: Float, y: Float): Double {
        return y / Math.sqrt(Math.pow(x.toDouble(), 2.0) + Math.pow(y.toDouble(), 2.0))
    }

    fun startLoop() {


        if (mRunnable != null || mIsAnimation) {
            return
        }
        mRunnable = StackRunnable()
        exitWithAnimation()
        mIsAnimation = true
    }

    fun stopLoop() {

        mRunnable = null
        mHandler.removeCallbacks(null)
        if (animationSet != null) {
            animationSet!!.cancel()
        }
        mIsAnimation = false
    }


    internal var timer: Timer? = null // 定时器
    // 停止滚动
    fun stopTimer() {
        Log.d("", "stopTimer")
        if (timer != null) {
            timer!!.cancel()
            if (animationSet != null) {
                animationSet!!.cancel()
            }

            timer = null
        }
    }

    // 开始滚动
    fun startTimer() {
        stopTimer()
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                (mContext as Activity).runOnUiThread {
                    for (index in viewCollection.indices) {
                        mCurrent = index
                    }
                    exitWithAnimation()
                }
            }
        }, 1000, 3000)
    }
<<<<<<< HEAD


    // 调用的一些方法
//    val id = item.getItemId()
//    // 重置
//    if (id == R.id.action_reset)
//    {
//        mCardStack.reset(true)
//        return true
//    }
//
//    // 底部
//    if (id == R.id.action_bottom)
//    {
//        mCardStack.setStackGravity(if (mCardStack.getStackGravity() === CardAnimator.TOP) CardAnimator.BOTTOM else CardAnimator.TOP)
//        mCardStack.reset(true)
//        return true
//    }
//
//    // 循环
//    if (id == R.id.action_loop)
//    {
//        mCardStack.setEnableLoop(!mCardStack.isEnableLoop())
//        mCardStack.reset(true)
//    }
//
//    // 是否允许旋转
//    if (id == R.id.action_rotation)
//    {
//        mCardStack.setEnableRotation(!mCardStack.isEnableRotation())
//        mCardStack.reset(true)
//    }
//
//    // 可见个数
//    if (id == R.id.action_visibly_size)
//    {
//        mCardStack.setVisibleCardNum(mCardStack.getVisibleCardNum() + 1)
//    }
//
//    // 间隔
//    if (id == R.id.action_span)
//    {
//        mCardStack.setStackMargin(mCardStack.getStackMargin() + 10)
//    }
//
//    if (id == R.id.action_settings)
//    {
//        mCardStack.undo()
//    }
=======
>>>>>>> 6c7b0e53cb6702995fb197eb2be3181452c27ad1
}