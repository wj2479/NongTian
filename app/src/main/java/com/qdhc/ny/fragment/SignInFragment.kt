package com.qdhc.ny.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.amap.api.location.AMapLocation
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.geocoder.RegeocodeAddress
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.qdhc.ny.R
import com.qdhc.ny.adapter.GridImageAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.bean.ClientManagerInfo
import com.qdhc.ny.bmob.Sign
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.sj.core.utils.ToastUtil
import com.vondear.rxui.view.dialog.RxDialogLoading
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_upload.rlv
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * 签到页面
 * @author shenjian
 * @date 2019/3/22
 */
class SignInFragment : BaseFragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    lateinit var userInfo: User

    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): SignInFragment {
            val fragment = SignInFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }

        val GUIUPDATEIDENTIFIER: Int = 1

    }

    //初始化地图控制器对象
    lateinit var aMap: AMap

    // 当前定位的数据
    var mLocation: AMapLocation? = null

    override fun intiLayout(): Int {
        return R.layout.fragment_sign_in
    }

    var selectList = ArrayList<LocalMedia>()
    internal lateinit var adapter: GridImageAdapter
    lateinit var rxDialogLoading: RxDialogLoading

    override fun initView() {
        rxDialogLoading = RxDialogLoading(activity)
        rxDialogLoading.textView.text = "签到信息上传中"
        //禁止rcyc嵌套滑动
        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = GridLayoutManager(activity, 4)
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

        //  获取时间
        myHandler.postDelayed(countDown, 0)
    }

    private val myLocationStyle: MyLocationStyle = MyLocationStyle()
    fun initMap() {

        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) //定位一次，且将视角移动到地图中心点
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(3000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。

        aMap.myLocationStyle = myLocationStyle//设置定位蓝点的Style
        aMap.isMyLocationEnabled = true// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.showMyLocation(false)//打开定位蓝点
        //禁止所有手势滑动
        aMap.uiSettings.setAllGesturesEnabled(false)
        //设置缩放按钮显示
        aMap.uiSettings.isZoomControlsEnabled = true
        //设置放缩图标在右下
        aMap.uiSettings.zoomPosition = AMapOptions.ZOOM_POSITION_RIGHT_CENTER
        // 设置默认定位按钮是否显示，非必需设置。
        aMap.uiSettings.isMyLocationButtonEnabled = false

        if (mLocation != null) {
            var cameraUpdate = CameraUpdateFactory
                    .newCameraPosition(CameraPosition(LatLng(mLocation!!.latitude, mLocation!!.longitude), 16f, 0f, 0f))
            aMap.moveCamera(cameraUpdate)
        }
    }

    override fun initClick() {
        //签到按钮
        ll_sign_in.setOnClickListener {
            if (mLocation == null) {
                ToastUtil.show(activity, "获取定位失败，请重新进入")
                return@setOnClickListener
            } else {
                var content = edt_content.text.toString()
                if (content.isNotEmpty()) {
                    rxDialogLoading.show()

                    var sign = Sign()
                    sign.address = mLocation?.address
                    sign.content = content
                    sign.lat = mLocation!!.latitude
                    sign.lng = mLocation!!.longitude

                    sign.save(object : SaveListener<String>() {
                        override fun done(objectId: String?, e: BmobException?) {
                            if (e == null) {
                                ToastUtil.show(context, "签到成功")
                                rxDialogLoading.dismiss()
                                activity!!.finish()
                            } else {
                                ToastUtil.show(context, "签到失败：" + e.toString())
                            }
                        }
                    })

                } else {
                    ToastUtil.show(activity, "请输入签到内容")
                }
            }
        }
        //更新定位
        iv_location_update.setOnClickListener {
        }
    }

    val countDown = object : Runnable {
        override fun run() {
            if (tv_time != null) {
                tv_time.text = getNow()
            }
            //获取时间
            myHandler.postDelayed(this, 1000)
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

    override fun initData() {
        userInfo = SharedPreferencesUtils.loadLogin(context)

        mLocation = ProjectData.getInstance().location

        if (mLocation != null) {
            tv_address_abbreviation.text = mLocation?.address
            tv_address_map.text = mLocation?.address
            tv_address.text = mLocation?.address
        }
    }

    override fun lazyLoad() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        aMap = mapView.map
        initMap()
    }

    private var myHandler = handler(this)

    private class handler(fragment: SignInFragment) : Handler() {
        private val mActivity: WeakReference<SignInFragment> = WeakReference(fragment)

        override fun handleMessage(msg: Message) {
            if (mActivity.get() == null) {
                return
            }
            Log.e("location__167", msg.obj.toString())
            val address = msg.obj as RegeocodeAddress
            val activity = mActivity.get()
            var abbAddreaa = address.formatAddress.toString()

            activity!!.tv_address_map.text = abbAddreaa + "附近"
            activity!!.tv_address.text = address.formatAddress + "附近"
            activity!!.tv_address_abbreviation.text = abbAddreaa + "附近"

            super.handleMessage(msg)
        }
    }


    /**
     * 必须重写以下方法
     */
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mapView != null) {
            mapView.onDestroy()
        }
    }

}

