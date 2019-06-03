package com.vivwe.video.activity

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
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
import com.vivwe.base.ui.CircleBarView
import com.vivwe.base.ui.alert.AlertDialog
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
    @BindView(R.id.tv_music_name)
    lateinit var musicNameTv: TextView
    @BindView(R.id.tv_music_duration)
    lateinit var musicDurationTv: TextView


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

                addImages()

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
        } else if(requestCode == 2 && data != null){
            var musicUrl = data!!.getStringExtra("result")

            Log.v(">>>music", musicUrl)
            var mmr = MediaMetadataRetriever()
            mmr.setDataSource(musicUrl, null)

            // 获取歌曲信息
            val title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            val album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
            val duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val pic = mmr.embeddedPicture

            // 设置信息到显示
            var date = Date()
            date.time = duration.toLong()
            var format = SimpleDateFormat("m:S").format(date)
            musicNameTv.setText(title)
            musicDurationTv.setText(format)
        }
    }

    /**
     * 清空所有图片
     */
    @OnClick(R.id.tv_images_clear)
    fun clearAll(){
        adapter.datas = null
    }

    /**
     * 添加图片
     */
    @OnClick(R.id.tv_images_add)
    fun addImages(){
        var intent = Intent()
        intent.setClass(this@VideoCreateByDynamicActivity, ImageLoadActivity::class.java)
        intent.putExtra("choose_count", 10)
        this@VideoCreateByDynamicActivity.startActivityForResult(intent, 1)
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