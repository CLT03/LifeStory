package com.vivwe.video.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import com.vivwe.base.ui.alert.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.gson.GsonBuilder
import com.shixing.sxvideoengine.SXRenderListener
import com.shixing.sxvideoengine.SXTemplate
import com.shixing.sxvideoengine.SXTemplateRender
import com.shixing.sxvideoengine.SXTextCanvas
import com.vivwe.base.activity.BaseActivity
import com.vivwe.base.util.AssetsUtils
import com.vivwe.base.util.imgeloader.ImageLoadActivity
import com.vivwe.main.R
import com.vivwe.video.adapter.VideoCreateDynamicImagesAdapter
import com.vivwe.video.listener.OnVideoCreateImageItemClicListener
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * ahtor: super_link
 * date: 2019/5/21 11:59
 * remark: 制作动态视频
 */
class VideoCreateByDynamicActivity: BaseActivity() {

    /** 图片显示 */
    @BindView(R.id.rcv_images)
    lateinit var imagesRcv: RecyclerView;

    lateinit var adapter: VideoCreateDynamicImagesAdapter

    private var mFolder: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_create_dynamic)

        ButterKnife.bind(this)

        init()
    }

    fun init(){

        // 获取APP内缓存目录
        mFolder = getExternalFilesDir("dynamic")

        // 创建缓存中对应该模板的File
        val folder = File(mFolder, "Chinese Style")

        if (!folder.exists()) { // 当模板不在临时文件夹，拷贝模板到临时文件夹
            Thread (object: Runnable {
                override fun run() {
                    // 因为这是测试，所以文件一定不会不存在，如果正式时需要判断是否成功！
                    AssetsUtils.copyDirFromAssets(this@VideoCreateByDynamicActivity, "dynamic" + File.separator + "Chinese Style", folder.path)
                }
            }).start()
        }

        adapter = VideoCreateDynamicImagesAdapter(this)
        imagesRcv!!.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
        imagesRcv.adapter = adapter

        adapter!!.setListener(object: OnVideoCreateImageItemClicListener {
            override fun onItemClick(path: String, postion: Int) {
                Toast.show(this@VideoCreateByDynamicActivity, "图片被点击了", 3000)

            }

            override fun onAddClick() {
                Toast.show(this@VideoCreateByDynamicActivity, "新增图片", 3000)

                var intent = Intent()
                intent.setClass(this@VideoCreateByDynamicActivity, ImageLoadActivity::class.java)
                intent.putExtra("choose_count", 10)
                this@VideoCreateByDynamicActivity.startActivityForResult(intent, 1)

            }

            override fun onEditClick(path: String, postion: Int) {
                Toast.show(this@VideoCreateByDynamicActivity, "编辑图片", 3000)
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /** 添加照片 */
        if(requestCode == 1 && data != null) {
            var paths: ArrayList<String> = data!!.getStringArrayListExtra("result")
            Log.v(">>>", GsonBuilder().create().toJson(paths))

            adapter.addDatas(paths)
        }
    }

    /**
     * 选择音乐
     */
    @OnClick(R.id.v_choose_music)
    fun toChooseMusic(){

    }

    /***
     * 生成视频
     *
     */
    @OnClick(R.id.tv_to_publish)
    fun generatorVideo(){
        val paths = adapter.datas

        if(paths!!.size < 3){
            Toast.show(this, "至少需要三张图片哦！", 3000)
            return
        }

        val folder = File(mFolder, "Chinese Style").getPath()
        val template = SXTemplate(folder, SXTemplate.TemplateUsage.kForRender)
//        val toBeStored = paths.toArray(arrayOfNulls<String>(paths.size))
        template.setReplaceableFilePaths(paths.toArray(arrayOfNulls<String>(paths.size)))
        template.commit()

        // 获取输出目录
        val outputFilePath = getOutputFilePath()
        val title = template.getAssetJsonForUIKey("title")
        if (!TextUtils.isEmpty(title)) {
            val sxTextCanvas = SXTextCanvas(title)
            val format = SimpleDateFormat("制作日期：\nyyyy年M月d日", Locale.US)
            sxTextCanvas.setContent(format.format(Date()))
            sxTextCanvas.adjustSize()
            val path = sxTextCanvas.saveToPath(externalCacheDir.toString() + File.separator + UUID.randomUUID() + ".png")
            template.setFileForAsset("title", path)
        }

        val sxTemplateRender = SXTemplateRender(template, "", outputFilePath)
        sxTemplateRender.setRenderListener(object : SXRenderListener {
            override fun onStart() {

            }

            override fun onUpdate(progress: Int) {
//                mDialog.setProgress(progress)
            }

            override fun onFinish(success: Boolean) {
//                mDialog.dismiss()
                // 播放
                var intent = Intent()
                intent.setClass(this@VideoCreateByDynamicActivity, VideoPreviewActivity::class.java)
                this@VideoCreateByDynamicActivity.startActivity(intent)
                //VideoPlayActivity.start(this@VideoCreateByDynamicActivity, outputFilePath)
            }

            override fun onCancel() {

            }
        })
        sxTemplateRender.start()
    }

    fun getOutputFilePath(): String {
        return getExternalFilesDir("video").toString() + File.separator + System.currentTimeMillis() + ".mp4"
    }

    @OnClick(R.id.tv_back)
    fun onBack() {
        finish()
    }
}