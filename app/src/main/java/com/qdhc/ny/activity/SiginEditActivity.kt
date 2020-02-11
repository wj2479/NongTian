package com.qdhc.ny.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.reflect.TypeToken
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.qdhc.ny.R
import com.qdhc.ny.adapter.GridImageAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.HttpResult
import com.qdhc.ny.bean.SignListInfo
import com.qdhc.ny.bean.UploadFile
import com.sj.core.net.RestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.*
import com.vondear.rxui.view.dialog.RxDialogLoading
import kotlinx.android.synthetic.main.activity_sigin_edit.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import org.json.JSONObject
import java.util.ArrayList

/**
 * 签到编辑
 * @author shenjian
 * @date 2019/3/27
 */

class SiginEditActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return (R.layout.activity_sigin_edit)
    }

    var maxSelectNum = 9
    var info = SignListInfo.DataListBean()
    lateinit var rxDialogLoading: RxDialogLoading
    var selectList = ArrayList<LocalMedia>()
    internal lateinit var adapter: GridImageAdapter
    override fun initView() {
        title_tv_right.visibility = View.VISIBLE
        title_tv_right.text = "保存"
        title_tv_title.text="签到信息修改"
        info = intent.getParcelableExtra("info")
        rxDialogLoading = RxDialogLoading(mContext)
        rxDialogLoading.textView.text = "签到信息上传中"

        //禁止rcyc嵌套滑动
        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = GridLayoutManager(mContext, 4)
        adapter = GridImageAdapter(mContext, maxSelectNum, {
            PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                    // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    // .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
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
                    .minimumCompressSize(500)// 小于500kb的图片不压缩
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
        title_tv_right.setOnClickListener {
            rxDialogLoading.show()
            if (selectList.isEmpty()) {
                upData()
            } else {
                var postiton =0
                selectList.forEach {
                    if (it.path.substring(0,4)=="http"){
                        postiton++
                        imageDaPaths +=it.path + ","
                    }else{
                        return@forEach
                    }
                }
                if (postiton<selectList.size){
                    upImages(selectList[postiton].compressPath, postiton)
                }else{
                    upData()
                }
            }
        }
        tv_location_type.setOnClickListener {
            getSignType()
        }
    }

    override fun initData() {
        tv_location_type.text = info.category
        edt_content.setText(info.description)
        PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(maxSelectNum-info.imgDa.size)
        info.imgDa.forEach {
            var localMedia=LocalMedia()
            localMedia.path=it
            selectList.add(localMedia)
        }
        adapter.setList(selectList)
        adapter.notifyDataSetChanged()
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
                    Log.i("图片-----》", media.compressPath)
                }
                adapter.setList(selectList)
                adapter.notifyDataSetChanged()
            }

        }
    }

    fun upData() {
        var param = java.util.HashMap<String, Any>()
        param["signId"] = info.id
        //描述
        param["Description"] = edt_content.text.toString()
        param["ImgDa"] = imageDaPaths
        //类别
        param["Category"] = tv_location_type.text.toString()
        //拜访对象
//        param["personId"] = info.signUserID
//        param["Lat"] = info.lat
//        param["Lon"] = info.lon
//        param["AddressAuto"] = info.addressAuto
        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(mContext, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(mContext, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(mContext, "signature")
        RestClient.create()
                .params(param)
                .headers(headers)
                .url("api/Sign/EditSign")
                .request(object : IRequest {
                    override fun onRequestStart() {
                        Log.e("request", "onRequestStart")
                    }

                    override fun onRequestEnd() {
                        rxDialogLoading.cancel()

                    }
                }).success {
                    //获取token并保存
                    var data = GsonUtil.getInstance().fromJson<HttpResult<JSONObject>>(it,
                            object : TypeToken<HttpResult<JSONObject>>() {}.type)
                    if (data.isSuccess) {
                        finish()

                    }
                    ToastUtil.show(mContext, data.message)
                }.error { code, msg ->
                    ToastUtil.show(mContext, "请求错误code:$code$msg")
                }.failure {
                    if (NetWorkUtil.isNetworkConnected(mContext)) {
                        ToastUtil.show(mContext, resources.getString(R.string.net_error))
                    }

                }
                .build()
                .post()
    }

    //多个用，号隔开
    var imageXiaoPaths = ""//服务器图片路径
    var imageDaPaths = ""//服务器图片路径
    /***
     * 单个上传图片
     * [file_str] 图片地址
     * [postiton] 当前上传
     * [up_count]  上传总数
     */
    private fun upImages(file_str: String, postiton: Int) {

        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(mContext, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(mContext, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(mContext, "signature")
        var url = "api/ImgUpload/Upload"
        RestClient.create()
                .headers(headers)
                .url(url)
                .file(file_str)
                .request(object : IRequest {
                    override fun onRequestStart() {
                        Log.e("request", "onRequestStart")
                    }

                    override fun onRequestEnd() {
                        Log.e("request", "onRequestEnd")
                    }
                }).success {
                    Log.i("json_getDate", it)
                    var postiton_temp = postiton + 1
                    var data = GsonUtil.getInstance().fromJson<HttpResult<UploadFile>>(it, object : TypeToken<HttpResult<UploadFile>>() {}.type)
                    imageDaPaths += data.data.imgDa + ","
                    imageXiaoPaths += data.data.imgXiao + ","
                    if (imageDaPaths.length < 3) {
                        imageDaPaths = data.data.img
                    }
                    if (postiton_temp < selectList.size) {
                        //还有图片则继续上传
                        upImages(selectList[postiton_temp].compressPath, postiton_temp)
                    } else {
                        //上传数据
                        upData()
                    }
                }.failure {
                    if (NetWorkUtil.isNetworkConnected(mContext)) {
                        ToastUtil.show(mContext, resources.getString(R.string.net_error))
                    } else {
                        ToastUtil.show(mContext, resources.getString(R.string.net_no_worker))
                    }
                }
                .build()
                .upload()
    }


    fun getSignType() {
        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(mContext, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(mContext, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(mContext, "signature")
        RestClient.create()
                .headers(headers)
                .url("api/Sign/GetSignCategory")
                .request(object : IRequest {
                    override fun onRequestStart() {
                        Log.e("request", "onRequestStart")
                    }

                    override fun onRequestEnd() {
                        Log.e("request", "onRequestEnd")
                    }
                }).success {
                    var data = GsonUtil.getInstance().fromJson<HttpResult<JSONObject>>(it,
                            object : TypeToken<HttpResult<JSONObject>>() {}.type)
                    if (data.isSuccess) {
                        JSONObject(it).get("data")
                        var dataTypes = (JSONObject(it).get("data") as JSONObject).getJSONArray("categorys")
                        var string_arra = arrayOfNulls<String>(dataTypes.length())
                        var i = 0
                        while (i < dataTypes.length()) {
                            string_arra[i] = dataTypes[i].toString()
                            i++
                        }
                        alrtShow(string_arra)
                    }
//                    string_array=data.data.
//                    Log.e("main_locat",it)
                }.failure {
                }.error { code, msg ->
                }
                .build()
                .post()
    }

    /**
     * 签到类型
     */
    fun alrtShow(string_array: kotlin.Array<String?>) {

        var alertDialog = AlertDialog.Builder(mContext)
                .setTitle("选择签到类别")
                // .setIcon(R.mipmap.ic_launcher)
                .setSingleChoiceItems(string_array, 0) { p0, p1 -> }
                .setPositiveButton("确定" //添加"Yes"按钮
                ) { p0, p1 ->
                    tv_location_type.text = string_array[p1 + 1]
                }.setNegativeButton("取消" //添加取消
                ) { p0, p1 ->
                }
                .create()
        alertDialog.show()
    }

}
