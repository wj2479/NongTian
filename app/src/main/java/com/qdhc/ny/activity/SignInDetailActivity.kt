package com.qdhc.ny.activity

import android.os.Bundle
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bmob.Sign
import kotlinx.android.synthetic.main.activity_sign_in_detail.*
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 签到详情
 * @author shenjian
 * @date 2019/3/27
 */
class SignInDetailActivity : BaseActivity() {

    //初始化地图控制器对象
    lateinit var aMap: AMap

    override fun intiLayout(): Int {
        return (R.layout.activity_sign_in_detail)
    }

    override fun initView() {
        title_tv_title.text = "签到详情"
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)

        var sign = intent.getSerializableExtra("sign") as Sign

        tv_address_abbreviation.text = sign.address
        tv_location_type.text = sign.createdAt
        edt_content.text = sign.content
        aMap = mapView.map

        initMap()

        var cameraUpdate = CameraUpdateFactory
                .newCameraPosition(CameraPosition(LatLng(sign.lat, sign.lng), 16f, 0f, 0f))
        aMap.moveCamera(cameraUpdate)
    }

    override fun initData() {

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
