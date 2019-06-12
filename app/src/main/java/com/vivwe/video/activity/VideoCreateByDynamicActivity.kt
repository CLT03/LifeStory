package com.vivwe.video.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.gson.GsonBuilder
import com.shixing.sxvideoengine.SXRenderListener
import com.shixing.sxvideoengine.SXTemplate
import com.shixing.sxvideoengine.SXTemplateRender
import com.shixing.sxvideoengine.SXTextCanvas
import com.vivwe.base.activity.BaseActivity
import com.vivwe.base.cache.ImageLoaderCache
import com.vivwe.base.constant.Globals
import com.vivwe.base.ui.CircleBarView
import com.vivwe.base.ui.alert.AlertDialog
import com.vivwe.base.util.AssetsUtils
import com.vivwe.base.util.TimeUtils
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
    @BindView(R.id.tv_music_name)
    lateinit var musicNameTv: TextView
    @BindView(R.id.tv_music_duration)
    lateinit var musicDurationTv: TextView
    @BindView(R.id.iv_cover)
    lateinit var coverIv: ImageView

    @BindView(R.id.tv_video_time)
    lateinit var videoTimeTv: TextView
    // 编辑
    @BindView(R.id.tv_edit)
    lateinit var editTv: TextView
    // 添加素材
    @BindView(R.id.tv_assets_add)
    lateinit var addAssetsTv: TextView
    // 添加文字
    @BindView(R.id.tv_add_text)
    lateinit var addTextTv: TextView
    // 复制图片
    @BindView(R.id.tv_copy_images)
    lateinit var copyImagesTv: TextView
    // 删除图片
    @BindView(R.id.tv_del_images_label)
    lateinit var delImagesLabelTv: TextView
    // 清空
    @BindView(R.id.tv_clear_label)
    lateinit var clearAllLabelTv: TextView
    // 保存草稿
    @BindView(R.id.tv_save_todraft)
    lateinit var saveToDraftTv: TextView
    // 下一步
    @BindView(R.id.tv_next)
    lateinit var nextTv: TextView

    lateinit var adapter: VideoCreateDynamicImagesAdapter

    private var mFolder: File? = null
    /** 当前音乐 */
    private var currentAudioPath:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_create_dynamic)

        ButterKnife.bind(this)

        init()
    }

    fun init(){

//        // 获取APP内缓存目录
//        mFolder = getExternalFilesDir("dynamic")
//        val folder = File(mFolder, "Chinese Style")


        mFolder = File(intent.getStringExtra("path"))

        if (!mFolder!!.exists()) { // 当模板不在临时文件夹，拷贝模板到临时文件夹
            Toast.makeText(this, "非法模板！", Toast.LENGTH_LONG).show()
            finish()
//            Thread (object: Runnable {
//                override fun run() {
//                    // 因为这是测试，所以文件一定不会不存在，如果正式时需要判断是否成功！
//                    AssetsUtils.copyDirFromAssets(this@VideoCreateByDynamicActivity, "dynamic" + File.separator + "Chinese Style", folder.path)
//                }
//            }).start()
        }

        adapter = VideoCreateDynamicImagesAdapter(this)
        imagesRcv!!.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
        imagesRcv.adapter = adapter

        adapter!!.setListener(object: OnVideoCreateImageItemClicListener {
            override fun onItemClick(path: String, postion: Int) {
                Toast.makeText(this@VideoCreateByDynamicActivity, "图片被点击了", Toast.LENGTH_LONG).show()
            }

            override fun onAddClick() {
                Toast.makeText(this@VideoCreateByDynamicActivity, "新增图片", Toast.LENGTH_LONG).show()

                addImagesByNative()

            }

            override fun onEditClick(path: String, postion: Int) {
                Toast.makeText(this@VideoCreateByDynamicActivity, "编辑图片", Toast.LENGTH_LONG).show()
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /** 添加照片 */
        if(requestCode == 1 && data != null) {
            var paths: ArrayList<String> = data!!.getStringArrayListExtra("result")
            if(Globals.isDebug){
                Log.v(">>>", GsonBuilder().create().toJson(paths))
            }

            adapter.addDatas(paths)
        } else if(requestCode == 2 && data != null){

            // 获取歌曲信息
            currentAudioPath = data!!.getStringExtra("path")
            val title = data!!.getStringExtra("name")
            val cover = data!!.getStringExtra("cover")
            val duration = data!!.getLongExtra("duration", 0)

            musicNameTv.setText(title)
            musicDurationTv.setText(TimeUtils.toMusicFormatTime(duration))
            ImageLoaderCache.getInstance().displayImage(cover, coverIv)
        }
    }

    /**
     * 清空所有图片
     */
    @OnClick(R.id.tv_clear)
    fun clearAll(){
        if(!adapter.isEdit) return
        adapter.datas = null
    }

    /**
     * 添加图片
     */
    fun addImagesByNative(){
        var intent = Intent()
        intent.setClass(this@VideoCreateByDynamicActivity, ImageLoadActivity::class.java)
        intent.putExtra("choose_count", 10)
        this@VideoCreateByDynamicActivity.startActivityForResult(intent, 1)
    }

    /**
     * 素材库中选择图片
     */
    fun addImagesByLibaray(){

    }

    @OnClick(R.id.tv_assets_add)
    fun addAssetsUI(){

        var alert = AlertDialog.createCustom(this@VideoCreateByDynamicActivity, R.layout.item_alert_video_addassets);

        // 本地库选择图片
        alert.findViewById<TextView>(R.id.tv_native).setOnClickListener(View.OnClickListener {
            alert.dismiss()
            addImagesByNative()
        })

        // 素材库中选择图片
        alert.findViewById<TextView>(R.id.tv_libary).setOnClickListener(View.OnClickListener {
            alert.dismiss()
            addImagesByLibaray()
        })

        // 关闭窗口
        alert.findViewById<TextView>(R.id.ibtn_close).setOnClickListener(View.OnClickListener {
            alert.dismiss()
        })
    }

    /**
     *
     * 拷贝图片
     */
    @OnClick(R.id.tv_copy_images)
    fun copyImages(){

        if(!adapter.isEdit) return

        var chooseImages = ArrayList<String>()

        adapter.chooseIndexs.forEachIndexed { index, value ->
            if(value){
                chooseImages.add(adapter.datas!!.get(index))
            }
        }

        adapter.addDatas(chooseImages)
    }

    /**
     * 删除图片
     */
    @OnClick(R.id.tv_del_images)
    fun delImages(){
        if(!adapter.isEdit) return

        var chooseImages = ArrayList<String>()

        adapter.datas!!.forEachIndexed { index, value ->
            if(!adapter.chooseIndexs.get(index)){
                chooseImages.add(adapter.datas!!.get(index))
            }
        }

        adapter.datas = chooseImages
    }

    /**
     * 选择音乐
     */
    @OnClick(R.id.v_choose_music)
    fun toChooseMusic(){
        var intent = Intent()
        intent.setClass(this, MusicLibraryActivity::class.java)
        startActivityForResult(intent, 2)
    }

    @OnClick(R.id.tv_edit)
    fun toEdit(){
        adapter.isEdit = !adapter.isEdit

        addAssetsTv.setTextColor(Color.parseColor(if(adapter.isEdit) "#C0C0C0" else "#FFFFFF"))
        addTextTv.setTextColor(Color.parseColor(if(adapter.isEdit) "#C0C0C0" else "#FFFFFF"))
        nextTv.setTextColor(Color.parseColor(if(adapter.isEdit) "#C0C0C0" else "#FFFFFF"))

        editTv.setTextColor(Color.parseColor(if(adapter.isEdit) "#FFFFFF" else "#999999"))
        copyImagesTv.setTextColor(Color.parseColor(if(adapter.isEdit) "#FFFFFF" else "#999999"))
        delImagesLabelTv.setTextColor(Color.parseColor(if(adapter.isEdit) "#FFFFFF" else "#999999"))
        clearAllLabelTv.setTextColor(Color.parseColor(if(adapter.isEdit) "#FFFFFF" else "#999999"))
    }


    var template: SXTemplate? = null
    fun createrTemplate(){
        val paths = adapter.datas

//        val folder = File(mFolder, "Chinese Style").getPath()

        if(template == null){
            template = SXTemplate(mFolder!!.path, SXTemplate.TemplateUsage.kForRender)

            val title = template!!.getAssetJsonForUIKey("title")
            if (!TextUtils.isEmpty(title)) {
                val sxTextCanvas = SXTextCanvas(title)
                val format = SimpleDateFormat("制作日期：\nyyyy年M月d日", Locale.US)
                sxTextCanvas.setContent(format.format(Date()))
                sxTextCanvas.adjustSize()
                val path = sxTextCanvas.saveToPath(externalCacheDir.toString() + File.separator + UUID.randomUUID() + ".png")
                template!!.setFileForAsset("title", path)

            }
        }

        template!!.setReplaceableFilePaths(paths!!.toArray(arrayOfNulls<String>(paths.size)))

        var duration:Int = template!!.realDuration()
        videoTimeTv.setText("" + duration)
    }

    /***
     * 生成视频
     *
     */
    @OnClick(R.id.tv_next)
    fun generatorVideo() {

        val paths = adapter.datas
        if(paths!!.size < 3){
            Toast.makeText(this, "至少需要三张图片哦！", Toast.LENGTH_LONG).show()
            return
        }
        val folder = mFolder!!.getPath()
        if(Globals.isDebug){
            Log.v(">>>generatorVideo", folder)
            Log.v(">>>currentAudioPath", currentAudioPath)
        }

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
        template.commit()

        var alert = AlertDialog.createCustom(this, R.layout.item_alert_video_merge_loading);
        alert.show()

        var progressCbv = alert.findViewById(R.id.cbv_progress) as CircleBarView
        var progressTv = alert.findViewById(R.id.tv_progress) as TextView

        val sxTemplateRender = SXTemplateRender(template, currentAudioPath, outputFilePath)
        sxTemplateRender.setRenderListener(object : SXRenderListener {

            override fun onFinish(success: Boolean, msg: String?) {
                var intent = Intent()
                intent.setClass(this@VideoCreateByDynamicActivity, VideoPreviewActivity::class.java)
                intent.putExtra("path", outputFilePath)
                this@VideoCreateByDynamicActivity.startActivity(intent)

                alert.dismiss()
            }

            override fun onStart() {

            }

            override fun onUpdate(progress: Int) {
                runOnUiThread(object: Runnable {
                    override fun run() {
                        progressTv.setText(progress.toString() + "%")
                        progressCbv.currentValue = progress
                    }
                })
                Log.v(">>>", progress.toString())
//                mDialog.setProgress(progress)
            }

            override fun onCancel() {

                alert.dismiss()
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