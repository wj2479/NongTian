package com.qdhc.ny.activity

import android.app.Activity
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
import com.qdhc.ny.bean.UploadFile
import com.sj.core.net.RestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.GsonUtil
import com.sj.core.utils.NetWorkUtil
import com.sj.core.utils.SharedPreferencesUtil
import com.sj.core.utils.ToastUtil
import com.vondear.rxui.view.dialog.RxDialogLoading
import kotlinx.android.synthetic.main.fragment_upload.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import org.json.JSONObject

/**
 * 案例分享
 * @author shenjian
 * @date 2019/4/18
 */
class ShareSubmitActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return (R.layout.activity_share_submit)
    }

    var selectList = ArrayList<LocalMedia>()
    internal lateinit var adapter: GridImageAdapter
    lateinit var rxDialogLoading: RxDialogLoading


    override fun initView() {
        rxDialogLoading = RxDialogLoading(mContext)
        rxDialogLoading.textView.text = "数据上传中。。。"
        title_tv_title.text = "物品分享"
        title_tv_right.text="发布"
        title_tv_right.visibility= View.VISIBLE
        //禁止rcyc嵌套滑动
        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = GridLayoutManager(mContext, 4)
        adapter = GridImageAdapter(mContext, 50, {
            adapter.setSelectMax(50)
            PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                    .maxSelectNum(50)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选
                    .previewImage(true)// 是否可预览图片
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .selectionMedia(selectList)// 是否传入已选图片
                    .minimumCompressSize(200)// 小于200kb的图片不压缩
                    .videoMaxSecond(15)
                    .previewVideo(true)// 是否可预览视频 true or false
                    .recordVideoSecond(15)//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
        })
        adapter.setOnItemClickListener { position, v ->

        }
        rlv.adapter = adapter
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
    override fun initClick() {
        title_iv_back.setOnClickListener { this.finish() }
        title_tv_right.setOnClickListener {
            rxDialogLoading.show()
            if (selectList.isEmpty()) {
                upData()
            } else {
                upImages(selectList)
                //    upImages(selectList[0].compressPath, 0)
            }
        }
    }

    override fun initData() {

    }
    var imageDaPaths = ""//服务器图片路径
    fun upData(){
        var param = java.util.HashMap<String, Any>()
        //描述
        param["Description"] = edt_content.text.toString()
        param["ImgDa"] = imageDaPaths
        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(mContext, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(mContext, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(mContext, "signature")
        RestClient.create()
                .params(param)
                .headers(headers)
                .url("/api/User/UserAnliShare")
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
                    if (NetWorkUtil.isNetworkConnected(mContext!!)) {
                        ToastUtil.show(mContext, resources.getString(R.string.net_error))
                    }

                }
                .build()
                .post()
    }

    //多图片上传

    private fun upImages(selectList: ArrayList<LocalMedia>){
        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(mContext, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(mContext, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(mContext, "signature")
        var url = "api/ImgUpload/UploadImgs"
        var paths=  ArrayList<String>()
        selectList.forEach {
            paths.add(it.compressPath)
        }
        RestClient.create()
                .headers(headers)
                .url(url)
                .files(paths)
                .request(object : IRequest {
                    override fun onRequestStart() {
                        Log.e("request", "onRequestStart")
                    }

                    override fun onRequestEnd() {
                        Log.e("request", "onRequestEnd")
                    }
                }).success {
                    var data = GsonUtil.getInstance().fromJson<HttpResult<UploadFile>>(it, object : TypeToken<HttpResult<UploadFile>>() {}.type)
                    if (data.isSuccess){
                        imageDaPaths += data.data.imgDa
                        //上传数据
                        upData()
                    }

                }.failure {
                    if (NetWorkUtil.isNetworkConnected(mContext!!)) {
                        ToastUtil.show(mContext, resources.getString(R.string.net_error))
                    } else {
                        ToastUtil.show(mContext, resources.getString(R.string.net_no_worker))
                    }
                }
                .build()
                .uploads()

    }

}
