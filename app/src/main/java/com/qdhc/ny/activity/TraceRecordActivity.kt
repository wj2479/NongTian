package com.qdhc.ny.activity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.amap.api.trace.LBSTraceClient
import com.amap.api.trace.TraceListener
import com.amap.api.trace.TraceLocation
import com.amap.api.trace.TraceOverlay
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.dialog.RxDialogWheelYearMonthDay
import com.qdhc.ny.entity.Track
import com.sj.core.net.Rx.RxRestClient
import com.sj.core.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_trace_record.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList


/**
 * 轨迹记录
 * @author shenjian
 * @date 2019/3/23
 */
class TraceRecordActivity : BaseActivity(), TraceListener {

    val sdf = SimpleDateFormat("yyyy-MM-dd");

    /**
     * 原始的数据
     */
    var originalTraceList = ArrayList<Track>()

    /**
     * 用户的ID
     */
    var uid = -1

    lateinit var mAMap: AMap

    override fun intiLayout(): Int {
        return R.layout.activity_trace_record
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mAMap = mapView.map
        mAMap.uiSettings.isRotateGesturesEnabled = false
        mAMap.uiSettings.isZoomControlsEnabled = false

        var myLocationStyle = MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//只定位一次。
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    override fun initView() {
        title_tv_title.text = "轨迹记录"
        title_tv_right.text = "选择人员"

        initWheelYearMonthDayDialog()
        initTrack()
    }

    lateinit var mRxDialogWheelYearMonthDay: RxDialogWheelYearMonthDay
    private fun initWheelYearMonthDayDialog() {
        // ------------------------------------------------------------------选择日期开始
        mRxDialogWheelYearMonthDay = RxDialogWheelYearMonthDay(this)
        mRxDialogWheelYearMonthDay.sureView.setOnClickListener {
            var date_txt = ("" +
                    mRxDialogWheelYearMonthDay.selectorYear + "-"
                    + mRxDialogWheelYearMonthDay.selectorMonth + "-"
                    + mRxDialogWheelYearMonthDay.selectorDay)
            tv_date.text = date_txt

            getTracks(uid, date_txt)
            mRxDialogWheelYearMonthDay.cancel()
        }
        mRxDialogWheelYearMonthDay.cancleView.setOnClickListener(
                { mRxDialogWheelYearMonthDay.cancel() })
        // ------------------------------------------------------------------选择日期结束
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
        title_tv_right.setOnClickListener { startActivityForResult(Intent(mContext, MyClientManageActivity::class.java), 1) }
        tv_date.setOnClickListener { mRxDialogWheelYearMonthDay.show() }

        iv_bottom_road.setOnClickListener {
            //路线
        }
        iv_bottom_location.setOnClickListener {
            //定位
        }
        iv_bottom_count.setOnClickListener {
            //            //统计
//            startActivity(Intent(mContext, TraceAnalysisActivity::class.java)
//                    .putExtra("userId", mUserId)
//                    .putExtra("title", title_tv_title.text.toString())
//                    .putExtra("date", tv_date.text.toString()))
        }
    }

    override fun initData() {
        uid = intent.getIntExtra("userId", 0)
        var nickName = intent.getStringExtra("nickName")

        var date = sdf.format(Date())
        title_tv_title.text = nickName + "的运动轨迹"
        tv_date.text = date

        Log.e("TAG", "日期--> " + date)
        getTracks(uid, date)
    }

    /**
     * 根据用户ID  日期获取 轨迹点记录
     */
    fun getTracks(uid: Int, date: String) {
        var params: HashMap<String, Any> = HashMap()
        params["uid"] = uid
        params["date"] = date
        RxRestClient.create()
                .url("track/getTracks")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<String> {
                    override fun accept(result: String?) {
                        var json = JSONObject(result)
                        if (json.getInt("code") == 1000) {
                            var jArray = json.getJSONArray("result")
                            Log.e("TAG", "获取轨迹成功:" + jArray.toString())
                            originalTraceList.clear()
                            mTraceList.clear()
                            if (jArray.length() > 0) {
                                for (index in 0 until jArray.length()) {
                                    val jsonObject = jArray.getJSONObject(index)
                                    val track = gson.fromJson(jsonObject.toString(), Track::class.java)
                                    originalTraceList.add(track)

                                    var traceLocation = TraceLocation()
                                    traceLocation.bearing = track.direction
                                    traceLocation.latitude = track.lat
                                    traceLocation.longitude = track.lng
                                    traceLocation.speed = track.speed
                                    traceLocation.time = track.locationTime
                                    mTraceList.add(traceLocation)
                                }

                                mTraceClient.queryProcessedTrace(System.currentTimeMillis().toInt(), mTraceList, LBSTraceClient.TYPE_AMAP, this@TraceRecordActivity)
                            } else {
                                clearTracksOnMap()
                                cameraToMyLocation()
                                ToastUtil.show(this@TraceRecordActivity, "当日暂无运动轨迹")
                            }
                        } else {
                            Log.e("TAG", "获取轨迹失败:" + result)
                            clearTracksOnMap()
                            cameraToMyLocation()
                            ToastUtil.show(this@TraceRecordActivity, "获取轨迹失败")
                        }
                    }
                },
                        object : Consumer<Throwable> {
                            override fun accept(t: Throwable?) {
                                t?.printStackTrace()
                                clearTracksOnMap()
                                cameraToMyLocation()
                                ToastUtil.show(this@TraceRecordActivity, "获取轨迹异常")
                            }
                        })
    }

    /**
     * 转移视角到定位的位置
     */
    fun cameraToMyLocation() {
        var cameraUpdate = CameraUpdateFactory
                .newCameraPosition(CameraPosition(LatLng(ProjectData.getInstance().location.latitude, ProjectData.getInstance().location.longitude), 16f, 0f, 0f))
        mAMap.moveCamera(cameraUpdate)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data.let {
            if (resultCode == Activity.RESULT_OK && requestCode == 1) {
//                title_tv_title.text = it!!.getParcelableExtra<UserInfo>("user").nickName
//                getPoint(it!!.getParcelableExtra<UserInfo>("user").userid, tv_date.text.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState)
    }

    private var polylines = LinkedList<Polyline>()
    private var endMarkers = LinkedList<Marker>()
    val boundsBuilder = LatLngBounds.Builder()

    /**
     * 绘制原始的轨迹数据
     */
    private fun drawTrackOnMap(points: List<Track>) {
        clearTracksOnMap()
        val polylineOptions = PolylineOptions()
        //  original.clear()
        //  original.addAll(points)
        polylineOptions.width(20f)
        polylineOptions.customTexture = BitmapDescriptorFactory
                .fromAsset("icon_road_green_arrow_up.png")
        polylineOptions.isUseTexture = true
        //       自己划线前清空一下
        Log.e(" points.size:", " points.size:" + points.size)

        points.forEachIndexed { index, it ->
            var traceLocation = TraceLocation(it.lat, it.lng, it.speed, it.direction, it.locationTime + 1)
            mTraceList.add(traceLocation)

            //自己划线
            val latLng = LatLng(it.lat, it.lng)

            polylineOptions.add(latLng)
            boundsBuilder.include(latLng)
            val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("时间：" + points[index].createTime)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(resources, R.drawable.ic_analysis_worker_ing)))
            endMarkers.add(mAMap.addMarker(markerOptions))
        }
        val polyline = mAMap.addPolyline(polylineOptions)
        polylines.add(polyline)
        //移动到可视范围
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 16))
        if (points.isNotEmpty()) {
            // 起点
            val p = points[0]
            val latLng = LatLng(p.lat, p.lng)
            val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("时间：" + points[0].createTime)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(resources, R.drawable.ic_analysis_worker_start)))
            endMarkers.add(mAMap.addMarker(markerOptions))
        }
        if (points.size > 1) {
            // 终点
            val p = points[points.size - 1]
            val latLng = LatLng(p.lat, p.lng)
            val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("时间：" + points[points.size - 1].createTime)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(resources, R.drawable.ic_analysis_worker_stop)))
            endMarkers.add(mAMap.addMarker(markerOptions))
        }
    }

    /**
     * 绘制纠偏之后的轨迹
     */
    private fun drawTracksOnMap(points: List<LatLng>) {
        clearTracksOnMap()
        val polylineOptions = PolylineOptions()
        //  original.clear()
        //  original.addAll(points)
        polylineOptions.width(20f)
        polylineOptions.customTexture = BitmapDescriptorFactory
                .fromAsset("icon_road_green_arrow.png")
        polylineOptions.isUseTexture = true
        //       自己划线前清空一下
        Log.e(" points.size:", " points.size:" + points.size)

        points.forEachIndexed { index, latLng ->
            //自己划线
            polylineOptions.add(latLng)
            boundsBuilder.include(latLng)

            if (index > 0 && index % 10 == 0) {    // 每个10个点显示一次
                val markerOptions = MarkerOptions()
                        .position(latLng)
//                    .title("时间：" + points[index].createdAt)
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                .decodeResource(resources, R.drawable.ic_analysis_worker_ing)))
                endMarkers.add(mAMap.addMarker(markerOptions))
            } else {
                val markerOptions = MarkerOptions()
                        .position(latLng)
//                    .title("时间：" + points[index].createdAt)
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                .decodeResource(resources, R.drawable.awqe)))  // 添加一个透明的icon
                endMarkers.add(mAMap.addMarker(markerOptions))
            }
        }
        val polyline = mAMap.addPolyline(polylineOptions)
        polylines.add(polyline)
        //移动到可视范围
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 16))
        if (points.isNotEmpty()) {
            // 起点
            val latLng = points[0]
            val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("时间：" + originalTraceList.get(0).createTime)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(resources, R.drawable.ic_analysis_worker_start)))
            endMarkers.add(mAMap.addMarker(markerOptions))
        }
        if (points.size > 1) {
            // 终点
            val latLng = points[points.size - 1]
            val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("时间：" + originalTraceList[originalTraceList.size - 1].createTime)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(resources, R.drawable.ic_analysis_worker_stop)))
            endMarkers.add(mAMap.addMarker(markerOptions))
        }
    }

    /**
     * 清除地图已完成或出错的轨迹
     */
    private fun clearTracksOnMap() {
        for (polyline in polylines) {
            polyline.remove()
        }
        for (marker in endMarkers) {
            marker.remove()
        }
        endMarkers.clear()
        mOverlayList.forEach { (key, overlay) ->
            if (overlay.traceStatus == TraceOverlay.TRACE_STATUS_FINISH
                    || overlay.traceStatus == TraceOverlay.TRACE_STATUS_FAILURE) {
                overlay.remove()
                mOverlayList.remove(key)
            }
        }
    }

    var mTraceList = ArrayList<TraceLocation>()
    val mOverlayList = ConcurrentHashMap<Int, TraceOverlay>()

    // 轨迹纠偏
    lateinit var mTraceClient: LBSTraceClient

    private fun initTrack() {
        mTraceClient = LBSTraceClient.getInstance(this.applicationContext)
    }

    /**
     * 轨迹纠偏失败回调
     */
    override fun onRequestFailed(lineID: Int, errorInfo: String?) {
        Log.e("Trace", "onRequestFailed");
        drawTrackOnMap(originalTraceList)
    }

    /**
     * 轨迹纠偏过程回调
     */
    override fun onTraceProcessing(lineID: Int, index: Int, segments: MutableList<LatLng>?) {
        Log.e("Trace", "onTraceProcessing");
    }

    /**
     * 轨迹纠偏结束回调
     */
    override fun onFinished(lineID: Int, segments: MutableList<LatLng>?, distance: Int, waitingtime: Int) {
        Log.e("Trace", "onFinished:" + segments?.size + " === " + originalTraceList.size);
        drawTracksOnMap(segments!!)
    }

}
