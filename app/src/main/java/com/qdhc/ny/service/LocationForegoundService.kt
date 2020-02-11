package com.qdhc.ny.service

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.location.AMapLocationQualityReport
import com.qdhc.ny.R
import com.sj.core.net.RestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.SharedPreferencesUtil
import java.util.*


class LocationForegoundService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        initLocation()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private val mBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        internal val service: LocationForegoundService
            get() = this@LocationForegoundService
    }




    lateinit var locationClient: AMapLocationClient
    lateinit var locationOption: AMapLocationClientOption

    fun initLocation() {
        //初始化client
        locationClient = AMapLocationClient(this.applicationContext)
        locationOption = getDefaultOption()
        //设置定位参数
        locationClient.setLocationOption(locationOption)
        // 设置定位监听
        locationClient.setLocationListener(locationListener)
        startLocation()

        locationClient.disableBackgroundLocation(true)
        locationClient.enableBackgroundLocation(2001, buildNotification())
        Log.e("json","json")
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
        mOption.locationMode = AMapLocationClientOption.AMapLocationMode.Device_Sensors
        mOption.httpTimeOut = 10000//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        // 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
        //   mOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mOption.interval = 1000 * 10//可选，设置定位间隔。默认为2秒
        mOption.isNeedAddress = true//可选，设置是否返回逆地理地址信息。默认是true
        mOption.isOnceLocation = false//可选，设置是否单次定位。默认是false
        mOption.isWifiScan = true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        mOption.isLocationCacheEnable = false
        return mOption
    }


    /**
     * 定位监听
     */
    var locationListener = AMapLocationListener { location ->
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
            var device = "\n设备厂商: " + Build.MANUFACTURER +// 设备厂商
                    "\nD设备型号       : " + Build.MODEL +// 设备型号
                    "\n系统版本    : " + Build.VERSION.RELEASE +// 系统版本
                    "\nSDK        : " + Build.VERSION.SDK_INT // SDK版本
            Log.e("json", "json:" + device)
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.errorCode == 0) {
//                if(location.accuracy >100f){
//                    locationClient.startLocation()
//                    return@AMapLocationListener
//                }

                sb.append("定位成功" + "\n")
                Log.e("location_main", "__" + sb.toString())

                sb.append("定位成功" + "\n")
                sb.append("定位类型: " + location.getLocationType() + "\n")
                sb.append("经    度    : " + location.getLongitude() + "\n")
                sb.append("纬    度    : " + location.getLatitude() + "\n")
                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n")
                sb.append("提供者    : " + location.getProvider() + "\n")

                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n")
                sb.append("角    度    : " + location.getBearing() + "\n")
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : " + location.getSatellites() + "\n")
                sb.append("国    家    : " + location.getCountry() + "\n")
                sb.append("省            : " + location.getProvince() + "\n")
                sb.append("市            : " + location.getCity() + "\n")
                sb.append("城市编码 : " + location.getCityCode() + "\n")
                sb.append("区            : " + location.getDistrict() + "\n")
                sb.append("区域 码   : " + location.getAdCode() + "\n")
                sb.append("地    址    : " + location.getAddress() + "\n")
                sb.append("兴趣点    : " + location.getPoiName() + "\n")
                //定位完成的时间
                //  sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                var result = jsonObject.toString()
                Log.e("location_main", "__" + sb.toString())
//            Log.e("location", result + "__" + isUpdata())
                if (location.accuracy > 100f) {
                    locationOption.interval = 1000 * 1
                    startLocation()
                    return@AMapLocationListener
                } else {
                    locationOption.interval = 1000 * 10
                    startLocation()
                    // isUpdata()
                    var sb_temp = StringBuffer()
                    sb_temp.append("***定位质量报告***").append("\n");
                    sb_temp.append("* WIFI开关：").append(if (location.locationQualityReport.isWifiAble) "开启" else "关闭").append("\n");
                    sb_temp.append("* GPS状态：").append(getGPSStatusString(location.locationQualityReport.gpsStatus)).append("\n");
                    sb_temp.append("* GPS星数：").append(location.locationQualityReport.gpsSatellites).append("\n");
                    sb_temp.append("* 网络类型：" + location.locationQualityReport.networkType).append("\n");
                    sb.append("* 网络耗时：" + location.locationQualityReport.netUseTime).append("\n");
                    sb.append("****************");
                    var device = "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商
                            "\nDevice Model       : " + Build.MODEL +// 设备型号
                            "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本
                            "\nAndroid SDK        : " + Build.VERSION.SDK_INT // SDK版本
                    sb_temp.append(device)
                    Log.e("json", "json:" + sb_temp.toString())
                    jsonObject["remark"] = sb_temp.toString()
                    upLocation(jsonObject)
//            if (isUpdata()) {
//                upLocation(jsonObject)
//            }

                }
            } else {
                //定位失败
                sb.append("定位失败" + "\n")
                sb.append("错误码:" + location.getErrorCode() + "\n")
                sb.append("错误信息:" + location.getErrorInfo() + "\n")
                sb.append("错误描述:" + location.locationDetail + "\n")
                locationClient.startLocation()
            }
            //   var sb_temp= StringBuffer()
//            sb_temp.append("***定位质量报告***").append("\n");
//            sb_temp.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启":"关闭").append("\n");
//            sb_temp.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
//            sb_temp.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
//            sb_temp.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
//            sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
//            sb.append("****************").append("\n");
            //定位之后的回调时间
            // sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

            //解析定位结果，


        } else {

        }
    }



    /**
     * 获取GPS状态的字符串
     * @param statusCode GPS状态码
     * @return
     */
    private fun getGPSStatusString(statusCode: Int): String {
        var str = ""
        when (statusCode) {
            AMapLocationQualityReport.GPS_STATUS_OK ->
                str = "GPS状态正常"
            AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER ->
                str = "手机中没有GPS Provider，无法进行GPS定位"
            AMapLocationQualityReport.GPS_STATUS_OFF ->
                str = "GPS关闭，建议开启GPS，提高定位质量"
            AMapLocationQualityReport.GPS_STATUS_MODE_SAVING ->
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
            AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION ->
                str = "没有GPS定位权限，建议开启gps定位权限"
        }
        return str;
    }

    /***
     * 判断是否可以上传数据
     */
    private fun isUpdata(): Boolean {
        var startTime = SharedPreferencesUtil.get(this, "startTime")
        var endTime = SharedPreferencesUtil.get(this, "endTime")
        val calendar = Calendar.getInstance()
        //小时
        val dhs = calendar.get(Calendar.HOUR_OF_DAY)
        //分钟
        val dms = calendar.get(Calendar.MINUTE)
        //开始时间 小于当前时间 并且小于结束时间
        //开始时间
        val sth = startTime.split(":")[0].toInt()//小时
        val stm = startTime.split(":")[1].toInt()//秒
        //结束时间
        val eth = endTime.split(":")[0].toInt()//小时
        val etm = endTime.split(":")[1].toInt()//秒
        return if (dhs in sth..eth) {
            if (sth <= dhs && stm <= dms && dhs <= eth && etm >= dms) {
                true
            } else {
                println("在外围外")
                false
            }
        } else {
            println("在外围外")
            false
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
            destroyLocation()
//            locationClient = null
//            locationOption = null
        }
    }


    fun upLocation(info: HashMap<String, Any>) {
        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(this, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(this, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(this, "signature")
        RestClient.create()
                .params(info)
                .headers(headers)
                .url("api/Position/Upload")
                .request(object : IRequest {
                    override fun onRequestStart() {
                        Log.e("request", "onRequestStart")
                    }

                    override fun onRequestEnd() {
                        Log.e("request", "onRequestEnd")
                    }
                }).success {
                    Log.e("main_locat", it)
                }.failure {
                }.error { code, msg ->

                }
                .build()
                .post()
    }
    private val NOTIFICATION_CHANNEL_NAME = "BackgroundLocation"
    lateinit var notificationManager: NotificationManager
    var isCreateChannel = false
    @SuppressLint("NewApi", "WrongConstant")
    @TargetApi(Build.VERSION_CODES.O)
    private fun buildNotification(): Notification {

        var builder: NotificationCompat.Builder
        var notification: Notification
        if (android.os.Build.VERSION.SDK_INT >= 26) {

            var channelId = packageName
            if (!isCreateChannel) {
                var notificationChannel = NotificationChannel(channelId,
                        NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
                notificationChannel.enableLights(true)//是否在桌面icon右上角展示小圆点
                notificationChannel.lightColor = Color.BLUE //小圆点颜色
                notificationChannel.setShowBadge(true)//是否在久按桌面图标时显示此渠道的通知
                notificationManager.createNotificationChannel(notificationChannel)
                isCreateChannel = true
            }
            builder = NotificationCompat.Builder(applicationContext, channelId)
        } else {
            builder = NotificationCompat.Builder(applicationContext)
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("智慧外勤")
                .setContentText("正在后台运行")
                .setWhen(System.currentTimeMillis())

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification = builder.build()
        } else {
            return builder.build()
        }
        return notification
    }

}
