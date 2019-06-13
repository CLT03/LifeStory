package com.vivwe.video.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.vivwe.base.activity.BaseActivity
import com.vivwe.main.R
import com.vivwe.video.AssetDelegate
import com.vivwe.video.adapter.VideoCreateStandaryThumbAdapter
import com.vivwe.video.model.MediaUiModel
import com.vivwe.video.model.TemplateModel
import com.vivwe.video.model.TextUiModel
import com.vivwe.video.ui.TemplateView
import com.vivwe.video.ui.TestAssetEditLayout
import com.vivwe.video.util.GroupThumbDecoration
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.*

/**
 * ahtor: super_link
 * date: 2019/6/11 10:09
 * remark:
 */
class VideoCreateByStandardActivity : BaseActivity(), AssetDelegate {


    /** 元素编辑 */
    @BindView(R.id.fl_container)
    lateinit var containerFl:FrameLayout
    /** 元素列表 */
    @BindView(R.id.rcv_list_thumb)
    lateinit var thumbListRcv: RecyclerView
    /** 文件编辑框 */
    @BindView(R.id.tael_text_edit)
    lateinit var textEditTael: TestAssetEditLayout

    var thumbAdapter: VideoCreateStandaryThumbAdapter? = null
    /** 模板路径 */
    private var mFolder: File? = null
    /** 模板模型 */
    private var mTemplateModel: TemplateModel? = null
    /** 模板元素列表 */
    private var mTemplateViews: ArrayList<TemplateView>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_create_standard)

        ButterKnife.bind(this)

        init()
    }

    /** 初始化 */
    private fun init(){
        mFolder = File(intent.getStringExtra("path"))

        thumbListRcv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        thumbListRcv.addItemDecoration(GroupThumbDecoration())

        thumbAdapter = VideoCreateStandaryThumbAdapter()
        thumbListRcv.adapter = thumbAdapter
        thumbAdapter!!.setOnItemSelectedListener(object : VideoCreateStandaryThumbAdapter.OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                for (i in mTemplateViews!!.indices) {
                    mTemplateViews!!.get(i).setVisibility(if (i == index) View.VISIBLE else View.GONE)
                }
            }

        })

        mTemplateViews = ArrayList()
        loadTemplate(mFolder!!.path)
    }


    /**
     * 加载模板内容
     */
    private fun loadTemplate(path: String) {

        Observable.create(object: ObservableOnSubscribe<TemplateModel> {
            override fun subscribe(emitter: ObservableEmitter<TemplateModel>) {
                var templateModel = TemplateModel(path, this@VideoCreateByStandardActivity)
                emitter.onNext(templateModel)
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<TemplateModel>{
                    override fun onComplete() { }

                    override fun onSubscribe(d: Disposable) { }

                    override fun onNext(templateModel: TemplateModel) {
                        if(templateModel != null){
                            mTemplateModel = templateModel
                            thumbAdapter!!.setTemplateModel(templateModel)

                            for (i in 1..templateModel.groupSize) { //group从1开始
                                val templateView = TemplateView(this@VideoCreateByStandardActivity)
                                templateView.setBackgroundColor(Color.BLACK)
                                templateView.visibility = if (i == 1) View.VISIBLE else View.GONE
                                val groupModel = templateModel.groups.get(i)
                                templateView.setAssetGroup(groupModel)

                                val params = FrameLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                params.gravity = Gravity.CENTER

                                mTemplateViews!!.add(templateView)
                                containerFl.addView(templateView, params)
                            }
                        }
                    }

                    override fun onError(e: Throwable) {}

                })


    }

    private var mModel: MediaUiModel? = null
    override fun pickMedia(model: MediaUiModel?) {
        mModel = model
        pickSingleMedia()
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION_SINGLE)
//        } else {
//            pickSingleMedia()
//        }
    }

    /** 选择图片（替换操作） */
    private fun pickSingleMedia() {
        Toast.makeText(this, "去选择图片", Toast.LENGTH_LONG).show()
    }

    override fun editText(model: TextUiModel?) {
        textEditTael.setVisibility(View.VISIBLE)
        textEditTael.setupWidth(model)
    }

    @OnClick(R.id.iv_back)
    override fun onBackPressed() {
        if (textEditTael.getVisibility() === View.VISIBLE) {
            textEditTael.hide()
        } else {
            super.onBackPressed()
        }
    }

}