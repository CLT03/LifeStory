package com.vivwe.video.activity

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.BindViews
import butterknife.ButterKnife
import butterknife.OnClick
import com.vivwe.base.activity.BaseActivity
import com.vivwe.base.activity.BaseFragmentActivity
import com.vivwe.base.ui.imageview.CropImageView
import com.vivwe.base.ui.textview.LinearGradientTextView
import com.vivwe.main.R
import com.vivwe.video.fragment.BasePhotoFragment
import com.vivwe.video.fragment.PhotoCropFragment
import com.vivwe.video.fragment.PhotoRotateFragment

/**
 * ahtor: super_link
 * date: 2019/6/14 15:30
 * remark:
 */
class TestActivity: BaseFragmentActivity() {

    @BindView(R.id.fl_content)
    lateinit var contentFl: FrameLayout
    @BindViews(R.id.tv_crop, R.id.tv_rotate)
    lateinit var menuLabels : Array<LinearGradientTextView>
    @BindViews(R.id.v_crop, R.id.v_rotate)
    lateinit var menuIcons : Array<View>

    var fragments: ArrayList<BasePhotoFragment> = ArrayList<BasePhotoFragment>()
    var showFragment : BasePhotoFragment? = null
    var showIndex : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        ButterKnife.bind(this)
        init()
    }

    fun init(){

        fragments.add(PhotoCropFragment())
        fragments.add(PhotoRotateFragment())



//        Thread(Runnable {
//            if(Globals.isDebug) Log.v("---TestActivity", "runable")
//            contentEiv.setImageBitmap(BitmapFactory.decodeResource(resources, R.mipmap.wellcome))
//        }).start()

        showFragment(0)
    }

    /** 裁剪 */
    @OnClick(R.id.tv_crop)
    fun toCrop(){
        showFragment(0)

    }

    /** 旋转 */
    @OnClick(R.id.tv_rotate)
    fun toRotate(){
        showFragment(1)
    }

    fun showFragment(index: Int){
        val transaction = supportFragmentManager.beginTransaction()

        val fragment: BasePhotoFragment = fragments[index]

        if (!fragment.isAdded()) {
            transaction.add(contentFl.id, fragment)
        }

        // 将上次选中选项回复为正常状态
        if (showFragment != null) {
            transaction.hide(showFragment!!)
            menuLabels.get(showIndex).setTextColor(Color.parseColor("#2C2E30"))
            menuIcons.get(showIndex).visibility = View.GONE
        }

        // 将选中选项设为选中状态
        menuLabels.get(index).setTextColor(Color.parseColor("#52D3FF"), Color.parseColor("#B35CFF"))
        menuIcons.get(index).visibility = View.VISIBLE

        showFragment = fragment
        showIndex = index
        // 展示当前选中页面并提交
        transaction.show(fragment).commitAllowingStateLoss()

    }

    @OnClick(R.id.iv_back)
    fun onBack(){
        finish()
    }

}