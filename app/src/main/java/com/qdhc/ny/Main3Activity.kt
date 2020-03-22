package com.qdhc.ny

import android.Manifest
import android.os.Build
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.luck.picture.lib.permissions.RxPermissions
import com.qdhc.ny.adapter.TabFragmentPagerAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.TabIconBean
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.User
import com.qdhc.ny.fragment.MyFragment
import com.qdhc.ny.fragment.NotifyFragment
import com.qdhc.ny.fragment.ProjectInfoListFragment
import com.qdhc.ny.fragment.ToDoListFragment
import com.qdhc.ny.service.UpadateManager
import com.sj.core.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * 市领导
 */
class Main3Activity : BaseActivity() {

    lateinit var userInfo: User

    override fun intiLayout(): Int {
        return (R.layout.activity_main2)
    }

    override fun initClick() {
    }

    override fun initData() {
        userInfo = ProjectData.getInstance().userInfo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions()
        }
    }

    /**
     * 申请权限
     */
    private fun requestPermissions() {
        var rxPermission = RxPermissions(this)

        rxPermission.requestEach(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        ).subscribe({ permission ->
            if (permission.granted) {
                // 用户已经同意该权限
                Log.e("TAG", permission.name + " is granted.")
                if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    initLocation()
                } else if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    UpadateManager.checkVersion(this)
                }
            } else if (permission.shouldShowRequestPermissionRationale) {
                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                ToastUtil.show(mContext, "拒绝了该权限，没有选中『不再询问』")
                Log.e("TAG", permission.name + " is denied. More info should be provided.")
            } else {
                // 用户拒绝了该权限，而且选中『不再询问』
                ToastUtil.show(mContext, "拒绝了该权限，而且选中『不再询问』")
                Log.e("TAG", permission.name + " is denied.")
            }

        })
    }


    //UI
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private val mIconUnselectIds = intArrayOf(R.drawable.ic_list,
            R.drawable.ic_to_do_list,
            R.mipmap.icon_notice,
            R.mipmap.icon_wode)
    private val mIconSelectIds = intArrayOf(R.drawable.ic_list_select,
            R.drawable.ic_to_do_list_select,
            R.mipmap.icon_notice_select,
            R.mipmap.icon_wode_select)

    override fun initView() {
        //获取数据 在values/arrays.xml中进行定义然后调用
        var tabTitle = resources.getStringArray(R.array.tab3_titles)
        //将fragment装进列表中
        var fragmentList = ArrayList<Fragment>()
        fragmentList.add(ProjectInfoListFragment())
        fragmentList.add(ToDoListFragment())
        fragmentList.add(NotifyFragment())
        fragmentList.add(MyFragment())
        //viewpager加载adapter
        vp.adapter = TabFragmentPagerAdapter(supportFragmentManager, fragmentList, tabTitle)
        for (i in fragmentList.indices) {
            mTabEntities.add(TabIconBean(tabTitle[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }
        tl.setTabData(mTabEntities)
        initPager()
    }

    var tab_position = 0
    override fun onRestart() {
        super.onRestart()
        tl.currentTab = tab_position
    }

    private fun initPager() {
        tl.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                vp.currentItem = position
                tab_position = position
            }

            override fun onTabReselect(position: Int) {
                if (position == 0) {
                    //  tl.showMsg(0, 56)
                }
            }
        })

        //默认选中第一个
        vp.currentItem = 0
        vp.offscreenPageLimit = 5
        vp.setNoScroll(true)
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
        // 设置定位参数
        locationClient.setLocationOption(locationOption)
        // 启动定位
        locationClient.startLocation()
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
        //   mOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
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
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (null != location && location.errorCode == 0) {
            Log.e("AMAP", "编码:" + location.cityCode + "  " + location.adCode)
//                getWeather(location.adCode)

            EventBus.getDefault().post(location)
            ProjectData.getInstance().location = location
        }
    }

    private var clickTime: Long = 0 // 第一次点击的时间

    override fun onBackPressed() {
        if (System.currentTimeMillis() - clickTime > 2000) {
            Toast.makeText(this, "再按一次键退出", Toast.LENGTH_SHORT).show()
            clickTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }

}
