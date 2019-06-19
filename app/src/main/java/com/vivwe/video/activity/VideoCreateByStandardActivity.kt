package com.vivwe.video.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.gson.GsonBuilder
import com.shixing.sxvideoengine.SXRenderListener
import com.shixing.sxvideoengine.SXTemplate
import com.shixing.sxvideoengine.SXTemplateRender
import com.vivwe.base.activity.BaseActivity
import com.vivwe.base.cache.ImageLoaderCache
import com.vivwe.base.constant.Globals
import com.vivwe.base.ui.CircleBarView
import com.vivwe.base.ui.alert.AlertDialog
import com.vivwe.base.util.TimeUtils
import com.vivwe.base.util.imgeloader.ImageLoadActivity
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
        pickSingleMedia(1, 1)
    }

    @OnClick(R.id.tv_batchImport, R.id.iv_batchImport)
    fun batchImport(){
        pickSingleMedia(2, mTemplateModel!!.assetsSize)
    }

    /**
     * 选择图片（替换操作）
     * @param count 图层数量
     */
    private fun pickSingleMedia(requestCode: Int, count:Int) {
        var intent = Intent()
        intent.setClass(this, ImageLoadActivity::class.java)
        intent.putExtra("choose_count", count)
        this.startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /** 添加照片 */
        if(requestCode == 1 && data != null) {
            var paths: ArrayList<String> = data!!.getStringArrayListExtra("result")
            if(Globals.isDebug){
                Log.v(">>>", GsonBuilder().create().toJson(paths))
            }

            if(paths.size == 1){
                mModel!!.setImageAsset(paths[0])
            }

        } else if(requestCode == 2 && data != null){
            var paths: ArrayList<String> = data!!.getStringArrayListExtra("result")
            if(Globals.isDebug){
                Log.v(">>>", GsonBuilder().create().toJson(paths))
            }

            mTemplateModel!!.setReplaceFiles(paths)
        }
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

    @OnClick(R.id.tv_next)
    fun render() {

        var alert = AlertDialog.createCustom(this, R.layout.item_alert_video_merge_loading);
        alert.show()

        var progressCbv = alert.findViewById(R.id.cbv_progress) as CircleBarView
        var progressTv = alert.findViewById(R.id.tv_progress) as TextView

        Thread(Runnable {
            val paths = mTemplateModel!!.getReplaceableFilePaths(externalCacheDir!!.path)

            val template = SXTemplate(mFolder!!.path, SXTemplate.TemplateUsage.kForRender)
            template.setReplaceableFilePaths(paths)
            template.commit()
            val outputPath = getOutputPath()

            if(Globals.isDebug) Log.v("---", "standard render music: " + mFolder!!.path + "/music.mp3")
            val sxTemplateRender = SXTemplateRender(template, mFolder!!.path + "/music.mp3", outputPath)
            sxTemplateRender.start()
            sxTemplateRender.setRenderListener(object : SXRenderListener {
                override fun onStart() {

                }

                override fun onUpdate(progress: Int) {
                    if(Globals.isDebug) Log.v("---","sandard render progress: "+ progress)
                    progressTv.setText(progress.toString() + "%")
                    progressCbv.currentValue = progress
                }

                override fun onFinish(success: Boolean, msg: String) {
                    alert.dismiss()
                    if (!success) {
                        Toast.makeText(this@VideoCreateByStandardActivity, msg, Toast.LENGTH_SHORT).show()
                    } else {
                        var intent = Intent()
                        intent.setClass(this@VideoCreateByStandardActivity, VideoPreviewActivity::class.java)
                        intent.putExtra("path", outputPath)
                        this@VideoCreateByStandardActivity.startActivity(intent)
                    }
                }

                override fun onCancel() {

                }
            })
        }).start()
    }

    private fun getOutputPath(): String {
        return getExternalFilesDir("video").path + File.separator + System.currentTimeMillis() + ".mp4"
    }

}