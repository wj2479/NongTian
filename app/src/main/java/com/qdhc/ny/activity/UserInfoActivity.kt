package com.qdhc.ny.activity

import android.app.Activity
import android.content.Intent
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.Constant
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.User
import com.sj.core.utils.ImageLoaderUtil
import com.sj.core.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 个人信息修改
 * @author shenjian
 * @date 2018/8/19
 */
class UserInfoActivity : BaseActivity() {
    lateinit var user: User
    override fun intiLayout(): Int {
        return (R.layout.activity_user_info)
    }

    override fun initData() {
        user = ProjectData.getInstance().userInfo
        edt_nickname.setText(user.nickName)
        //头像
//        if (user.avatar != null) {
//            ImageLoaderUtil.loadCorners(mContext, user.avatar.url, iv_photo, -1, R.drawable.ic_defult_user)
//        }
    }

    override fun initClick() {
        //保存
        btn_save.setOnClickListener({
            if (edt_nickname.text.isNullOrEmpty()) {
                ToastUtil.show(mContext, "请输入姓名")
            } else {
//                showDialog("正在保存...")
            }
        })
        //头像
        rl_photo.setOnClickListener({
            PictureSelector.create(mContext)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    // .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(Constant.MAXSELECTNUM)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.SINGLE)// 单选
                    .previewImage(true)// 是否可预览图片
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(true)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    // .hideBottomControls(if (cb_hide.isChecked()) false else true)// 是否显示uCrop工具栏，默认不显示
                    //      .isGif(cb_isGif.isChecked())// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    //    .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
                    //    .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    //   .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    //   .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                    //.selectionMedia(selectList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                    //                        .videoMaxSecond(15)
                    //                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    //.recordVideoSecond()//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (resultCode == Activity.RESULT_OK) {
                when (requestCode) {
                    PictureConfig.CHOOSE_REQUEST -> {
                        // 图片选择结果回调
                        var path = PictureSelector.obtainMultipleResult(data)[0].compressPath
                        //头像
                        ImageLoaderUtil.loadCorners(mContext, path,
                                iv_photo, -1, R.drawable.ic_defult_user)
                        iv_photo.setTag(R.id.TAG, path)
                        // 例如 LocalMedia 里面返回三种path
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                        // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的

                    }
                }
            }
        }
    }

    private fun upUser(path: String, nickName: String) {
        user.nickName = nickName
    }



    override fun initView() {
        title_tv_title.text = "个人中心"
        title_iv_back.setOnClickListener({
            finish()
        })
    }

}
