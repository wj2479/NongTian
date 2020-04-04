package com.qdhc.ny.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.qdhc.ny.R
import com.qdhc.ny.adapter.UserLocationAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.User
import com.qdhc.ny.entity.UserLastLocation
import com.sj.core.net.Rx.RxRestClient
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_location_gather.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * 定位汇总
 * @author shenjian
 * @date 2019/3/23
 */
class LocationGatherActivity : BaseActivity() {

    val sdf = SimpleDateFormat("yyyy-MM-dd");

    lateinit var userInfo: User

    var locationList = ArrayList<UserLastLocation>()

    var selectIndex = -1

    lateinit var mAdapter: UserLocationAdapter

    override fun intiLayout(): Int {
        return R.layout.activity_location_gather
    }

    lateinit var mAMap: AMap

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
        mAMap.setMyLocationEnabled(true)// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        // 绑定 Marker 被点击事件
        mAMap.setOnMarkerClickListener(object : AMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker?): Boolean {
                Log.e("TAG", "点击--》" + marker?.`object`)
                selectIndex = marker?.`object`.toString().toInt()

                mAdapter.setSelectIndex(selectIndex)
                mAdapter.notifyDataSetChanged()

                smrw!!.scrollToPosition(selectIndex)
                drawMarksOnMap(locationList)
                return true
            }
        })
    }

    override fun initView() {
        title_tv_title.text = "监理位置"
//        tv_date.text = sdf.format(Date()) + "  位置信息"

        smrw!!.layoutManager = LinearLayoutManager(this)
        smrw!!.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(this, R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            if (locationList.size <= position) {
                return@setSwipeItemClickListener
            }
            var loc = locationList[position]
            if (loc.lng.toInt() == 0 || loc.lat.toInt() == 0)
                return@setSwipeItemClickListener

            selectIndex = position
            mAdapter.setSelectIndex(selectIndex)
            mAdapter.notifyDataSetChanged()

            drawMarksOnMap(locationList)
        }

        mAdapter = UserLocationAdapter(this, locationList)
        smrw.adapter = mAdapter

        // 子控件的点击事件
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            Log.e("TAG", "点击--》" + position)
            if (locationList.size <= position) {
                return@setOnItemChildClickListener
            }
            var loc = locationList[position]

            var intent = Intent(this, TraceRecordActivity::class.java)
            intent.putExtra("userId", loc.uid)
            intent.putExtra("nickName", loc.nickName)
            startActivity(intent)
        }

        val emptyView = layoutInflater.inflate(R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(R.id.tv_empty).text = "暂无监理位置信息"
        //添加空视图
        mAdapter.emptyView = emptyView
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        userInfo = ProjectData.getInstance().userInfo

        getLastLocation(userInfo.id)
    }

    /**
     * 转移视角到定位的位置
     */
    fun cameraToLocation(latLng: LatLng) {
        var cameraUpdate = CameraUpdateFactory
                .newCameraPosition(CameraPosition(latLng, 9f, 0f, 0f))
        mAMap.moveCamera(cameraUpdate)
    }

    /**
     * 转移视角到定位的位置
     */
    fun cameraToMyLocation() {
        var cameraUpdate = CameraUpdateFactory
                .newCameraPosition(CameraPosition(LatLng(ProjectData.getInstance().location.latitude, ProjectData.getInstance().location.longitude), 16f, 0f, 0f))
        mAMap.moveCamera(cameraUpdate)
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

    /**
     * 获取最后的位置
     */
    fun getLastLocation(uid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["uid"] = uid
//        params["date"] = "2020-03-27"
        RxRestClient.create()
                .url("track/getChildUserLastPosition")
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
                            Log.e("TAG", "位置成功:" + jArray.toString())
                            if (jArray.length() > 0) {
                                for (index in 0 until jArray.length()) {
                                    val jsonObject = jArray.getJSONObject(index)
                                    val lastLocation = gson.fromJson(jsonObject.toString(), UserLastLocation::class.java)
                                    locationList.add(lastLocation)

                                    if (selectIndex == -1) {
                                        if (lastLocation.lat.toInt() != 0 && lastLocation.lng.toInt() != 0) {
                                            selectIndex = index
                                        }
                                    }
                                }

                                mAdapter.setSelectIndex(selectIndex)
                                mAdapter.notifyDataSetChanged()
                                smrw!!.scrollToPosition(selectIndex)
                                drawMarksOnMap(locationList)

                                // 如果index 没有赋值 就说明 所有的记录都没有位置
                                if (selectIndex == -1) {
                                    cameraToMyLocation()
                                }
                            } else {
                                // 如果没有记录 就回到定位的位置
                                cameraToMyLocation()
                            }
                        } else {
                            Log.e("TAG", "位置失败:" + result)
                        }
                    }
                },
                        object : Consumer<Throwable> {
                            override fun accept(t: Throwable?) {
                                t?.printStackTrace()
                            }
                        })
    }

    fun drawMarksOnMap(locationList: List<UserLastLocation>) {
        if (locationList == null || locationList.size == 0)
            return

        mAMap.clear()
        locationList.forEachIndexed { index, userLastLocation ->
            if (userLastLocation.lat.toInt() != 0 && userLastLocation.lng.toInt() != 0) {
                var markerOption = MarkerOptions();
                var latlng = LatLng(userLastLocation.lat, userLastLocation.lng)
                if (index == selectIndex) {
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.b_poi_geo_hl)));
                    cameraToLocation(latlng)
                } else {
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.b_poi_hl)));
                }
                markerOption.position(latlng)
                markerOption.title(userLastLocation.nickName)
                markerOption.snippet(userLastLocation.createTime)
                markerOption.draggable(true);//设置Marker可拖动
                // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                markerOption.setFlat(true);//设置marker平贴地图效果
                var marker = mAMap.addMarker(markerOption)
                marker.`object` = index
            }
        }

    }
}
