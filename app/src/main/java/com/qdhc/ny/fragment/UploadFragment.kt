package com.qdhc.ny.fragment

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.qdhc.ny.R
import com.qdhc.ny.adapter.GridImageAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.bean.ClientManagerInfo
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.sj.core.utils.ToastUtil
import com.vondear.rxui.view.dialog.RxDialogLoading
import kotlinx.android.synthetic.main.fragment_upload.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * 签到页面
 * @author shenjian
 * @date 2019/3/22
 */
class UploadFragment : BaseFragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    lateinit var userInfo: User

    var district = ""
    var street = ""

    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): UploadFragment {
            val fragment = UploadFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }

        val GUIUPDATEIDENTIFIER: Int = 1

    }

    override fun intiLayout(): Int {
        return R.layout.fragment_upload
    }

    var selectList = ArrayList<LocalMedia>()
    internal lateinit var adapter: GridImageAdapter
    lateinit var rxDialogLoading: RxDialogLoading

    override fun initView() {
        rxDialogLoading = RxDialogLoading(activity)
        rxDialogLoading.textView.text = "信息上传中"
        //禁止rcyc嵌套滑动
        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = GridLayoutManager(activity, 4) as RecyclerView.LayoutManager?
        adapter = GridImageAdapter(activity, 9, {
            adapter.setSelectMax(9)
            PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
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

//        // 建立数据源
//        val typeArrays = resources.getStringArray(com.qdhc.md.R.array.types)
//        // 建立Adapter并且绑定数据源
//        val typeAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, typeArrays)
//        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        //绑定 Adapter到控件
//        typeSpinner.setAdapter(typeAdapter)
//
//        // 建立数据源
//        val levelArrays = resources.getStringArray(com.qdhc.md.R.array.levels)
//        // 建立Adapter并且绑定数据源
//        val levelAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, levelArrays)
//        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        //绑定 Adapter到控件
//        levelSpinner.setAdapter(levelAdapter)
    }

    override fun initClick() {
        //签到按钮
        btn_upload.setOnClickListener {

            var partyName = nameEt.text.trim().toString()
            if (partyName.isEmpty()) {
                ToastUtil.show(context, "上报人不能为空")
                nameEt.requestFocus()
                return@setOnClickListener
            }

            var partyPhone = phoneEt.text.trim().toString()
            if (!partyPhone.startsWith("1") || partyPhone.length != 11) {
                ToastUtil.show(context, "手机号码不合法")
                phoneEt.requestFocus()
                return@setOnClickListener
            }

            var numbers = numbersEt.text.trim().toString()
//            if (numbers.isEmpty()) {
//                ToastUtil.show(context, "涉及人数不能为空")
//                numbersEt.requestFocus()
//                return@setOnClickListener
//            }

            var description = edt_content.text.trim().toString()
            if (description.isEmpty()) {
                ToastUtil.show(context, "信息描述不能为空")
                edt_content.requestFocus()
                return@setOnClickListener
            }

            showDialog("正在上报数据...")

        }
    }


    fun getNow(): String {
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            return SimpleDateFormat("HH:mm").format(Date())
        } else {
            var tms = Calendar.getInstance()
            var hour = if (tms.get(Calendar.HOUR_OF_DAY).toString().length == 1) "0" + tms.get(Calendar.HOUR_OF_DAY).toString()
            else tms.get(Calendar.HOUR_OF_DAY).toString()

            var minute = if (tms.get(Calendar.MINUTE).toString().length == 1) "0" + tms.get(Calendar.MINUTE).toString()
            else tms.get(Calendar.MINUTE).toString()
            return "$hour:$minute"
        }

    }

    var user: ClientManagerInfo = ClientManagerInfo()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            // 图片选择结果回调
            selectList = PictureSelector.obtainMultipleResult(data) as ArrayList<LocalMedia>

            Log.i("数量-----》", selectList.size.toString())

            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
            for (media in selectList) {
//                Log.i("图片1-----》", media.compressPath)
//                Log.i("图片2-----》", media.path)
//
//                Log.i("mime-----》", media.mimeType.toString())
            }
            adapter.setList(selectList)
            adapter.notifyDataSetChanged()
        }
    }

    override fun initData() {
        userInfo = SharedPreferencesUtils.loadLogin(context)

        nameEt.setText(userInfo.nickName)
        nameEt.setSelection(userInfo.nickName.length)

        initLocation()
    }

    override fun lazyLoad() {
    }


    lateinit var locationClient: AMapLocationClient
    lateinit var locationOption: AMapLocationClientOption

    fun initLocation() {
        //初始化client
        locationClient = AMapLocationClient(activity)
        locationOption = getDefaultOption()
        //设置定位参数
        locationClient.setLocationOption(locationOption)
        // 设置定位监听
        locationClient.setLocationListener(locationListener)
        startLocation()
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private fun getDefaultOption(): AMapLocationClientOption {
        var mOption = AMapLocationClientOption()
        //Battery_Saving
//        低功耗模式
//        Device_Sensors
//        仅设备模式,不支持室内环境的定位
//        Hight_Accuracy
//        高精度模式
        mOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mOption.httpTimeOut = 10000//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        // 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
        mOption.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        mOption.interval = 1000 * 10//可选，设置定位间隔。默认为2秒
        mOption.isNeedAddress = true//可选，设置是否返回逆地理地址信息。默认是true
        mOption.isOnceLocation = true//可选，设置是否单次定位。默认是false
        mOption.isWifiScan = true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        mOption.isLocationCacheEnable = false
        return mOption
    }

    /**
     * 定位监听
     */
    var locationListener = AMapLocationListener { location ->
        Log.e("TAG", "定位结果:" + location.errorInfo)
        if (null != location) {
            var jsonObject = HashMap<String, Any>()
            jsonObject["location"] = "" + location.longitude + "," + location.latitude
            jsonObject["locatetime"] = location.time
            jsonObject["speed"] = location.speed
            //  jsonObject["speed"] ="精    度    : " + location.accuracy + "米"  +";提供者    : " + location.provider
            jsonObject["direction"] = location.bearing
            jsonObject["height"] = location.altitude
            jsonObject["accuracy"] = location.accuracy
            jsonObject["address"] = location.address
            var sb = StringBuffer()
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.errorCode == 0) {
                if (location.address.isNotEmpty()) {
                    var it = Location("d")
                    it.latitude = location.latitude
                    it.longitude = location.longitude
                    Log.e("TAG", "定位地址:" + location.address)
                    district = location.district
                    street = location.street
                    locationTv.setText(location.city + location.district + location.street)
                    stopLocation()
                }

            } else {
                locationClient.startLocation()
            }
        } else {

        }
    }

    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     */
    private fun startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption)
        // 启动定位
        locationClient.startLocation()
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     */
    private fun stopLocation() {
        // 停止定位
        locationClient.stopLocation()
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     */
    private fun destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyLocation()
    }

}

