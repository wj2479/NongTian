package com.qdhc.ny.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.qdhc.ny.R
import com.qdhc.ny.adapter.GridImageAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.Project
import com.qdhc.ny.entity.User
import com.sj.core.utils.ToastUtil
import com.xw.repo.BubbleSeekBar
import kotlinx.android.synthetic.main.activity_add_project_schedule.*
import kotlinx.android.synthetic.main.layout_title_theme.*

class AddProjScheduleActivity : BaseActivity() {

    lateinit var project: Project

    lateinit var adapter: GridImageAdapter

    lateinit var userInfo: User

    var selectList = ArrayList<LocalMedia>()

    // 初始进度
    var initSchedule = 0f

    override fun intiLayout(): Int {
        return R.layout.activity_add_project_schedule
    }

    override fun initView() {
        title_tv_title.text = "添加工程进度"

        //禁止rcyc嵌套滑动
        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = GridLayoutManager(this, 4) as RecyclerView.LayoutManager?
        adapter = GridImageAdapter(this, 9, {
            adapter.setSelectMax(9)
            PictureSelector.create(this).openGallery(PictureMimeType.ofAll())
                    // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    // .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(9)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选
                    .previewImage(true)// 是否可预览图片
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    //  .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    // .hideBottomControls(if (cb_hide.isChecked()) false else true)// 是否显示uCrop工具栏，默认不显示
                    //      .isGif(cb_isGif.isChecked())// 是否显示gif图片
                    //    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    //    .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
                    //    .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    //   .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    //   .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                    //                        .videoMaxSecond(15)
                    //                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                    .minimumCompressSize(200)// 小于300kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    .videoMaxSecond(15)
                    .previewVideo(true)// 是否可预览视频 true or false
                    //.videoSecond(15)//显示多少秒以内的视频or音频也可适用
                    .recordVideoSecond(15)//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
        })
        adapter.setOnItemClickListener { position, v ->

        }
        rlv.adapter = adapter
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
        //签到按钮
        bt_add.setOnClickListener {

            if (bubbleSeekbar.progress < initSchedule) {
                ToastUtil.show(this@AddProjScheduleActivity, "工程没有新的进度")
                return@setOnClickListener
            }

            var content = edt_content.text.toString().trim()
            if (TextUtils.isEmpty(content)) {
                ToastUtil.show(this@AddProjScheduleActivity, "工程进度描述信息为空")
                return@setOnClickListener
            }


        }

        bubbleSeekbar.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
            }

            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                if (progress < initSchedule) {
                    ToastUtil.show(this@AddProjScheduleActivity, "新进度不能小于当前进度:" + initSchedule + "%")
                    bubbleSeekbar.setProgress(initSchedule)
                }
            }

            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == Activity.RESULT_OK) {
                // 图片选择结果回调
                selectList = PictureSelector.obtainMultipleResult(data) as ArrayList<LocalMedia>

                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                for (media in selectList) {
                    Log.i("图片1-----》", media.compressPath)
                    Log.i("图片2-----》", media.path)
                }
                adapter.setList(selectList)
                adapter.notifyDataSetChanged()
            }

        }
    }

    override fun initData() {
        userInfo = ProjectData.getInstance().userInfo

        project = intent.getSerializableExtra("project") as Project

        nameTv.setText(project.name)

        initSchedule = project.process * 1.0f

        bubbleSeekbar.setProgress(initSchedule)

        processTv.text = initSchedule.toString() + "%"

        project
    }


}
