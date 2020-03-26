package com.qdhc.ny.fragment


import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.util.Log
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.luck.picture.lib.permissions.RxPermissions
import com.qdhc.ny.activity.CameraActivity
import com.qdhc.ny.activity.ProjectInfoActivity
import com.qdhc.ny.activity.UpdateDailyReportActivity
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.common.Constant
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.Area
import com.qdhc.ny.entity.LiveWeather
import com.qdhc.ny.entity.Project
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.UriToPathUtils
import com.sj.core.net.Rx.RxRestClient
import com.sj.core.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_jianli.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * 监理主页面
 */
class JianliFragment : BaseFragment() {

    var projectList = ArrayList<Project>()

    lateinit var userInfo: User

    var project: Project? = null

    var orderList = ArrayList<Project>()

    override fun intiLayout(): Int {
        return com.qdhc.ny.R.layout.fragment_jianli
    }

    override fun initView() {
//        viewPager.setAdapter(mAdapter);
    }

    override fun initClick() {
        cameraIv.setOnClickListener {
            if (project != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions()
                } else {
                    startActivityForResult(Intent(activity!!, CameraActivity::class.java), 100);
                }
            } else {
                ToastUtil.show(context, "您还没有项目")
            }
        }
        projectLayout.setOnClickListener {
            if (project != null) {
                var intent = Intent(context, ProjectInfoActivity::class.java)
                intent.putExtra("project", project)
                startActivity(intent)
            }
        }
    }

    override fun initData() {
        userInfo = ProjectData.getInstance().userInfo
        helloTv.text = "您好, " + userInfo.nickName
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy年MM月dd日")
        val str1 = arrayOf("", "日", "一", "二", "三", "四", "五", "六")
        dateTv.text = "今天是 " + simpleDateFormat.format(Date()) + "\t星期" + str1[calendar.get(Calendar.DAY_OF_WEEK)]

        var cityName = getAreaLevelName(userInfo.area, Constant.AREA_LEVEL_CITY)
        tv_title.text = cityName + "高标准农田建设"

        var districtName = getAreaLevelName(userInfo.area, Constant.AREA_LEVEL_DISTRICT)
        tv_district.text = districtName

        getProjectData(userInfo.id)
    }

    /**
     * 获取地区的名字
     */
    fun getAreaLevelName(area: Area, level: Int): String {
        if (area != null) {
            if (area.regionLevel == level) {
                return area.name
            } else {
                if (area.regionLevel > level) {
                    return getAreaLevelName(area.parent, level)
                }
            }
        }
        return ""
    }

    /**
     * 获得排序
     */
    private fun getOrderProject(pid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["pid"] = pid
        RxRestClient.create()
                .url("project/getProjectRank")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                var rank = json.getInt("result")
                                order_city_tv.text = rank.toString()

                                if (rank == 1) {     // 如果排名是第一 就显示自己
                                    companyTv.text = project!!.name
                                } else {
                                    getTop1Project(pid)
                                }
                            }
                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }


    override fun onResume() {
        super.onResume()
    }

    override fun lazyLoad() {
    }

    /**
     * 获取项目基本信息
     */
    fun getProjectData(uid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["uid"] = uid
        RxRestClient.create()
                .url("project/getProjectByUserId")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                var jsonObject = json.getJSONObject("result")
                                project = gson.fromJson(jsonObject.toString(), Project::class.java)
                                project_name_tv.text = project!!.name
                                Log.e("TAG", "请求成功:" + project.toString())
                                progressbar.setProgress(project!!.process)
                                progressTv.text = project!!.process.toString() + "%"
                                getOrderProject(project!!.id)
                            } else {
                                Log.e("TAG", "请求失败:" + result)
                                project_name_tv.text = "您还没有项目"
                            }

                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }

    /**
     * 获取项目的进度
     */
    fun getTop1Project(pid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["pid"] = pid
        RxRestClient.create()
                .url("project/getTop1ProjectInArea")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                var result = json.getJSONArray("result")
                                if (result.length() > 0) {
                                    var jsonObj = result.getJSONObject(0)
                                    companyTv.text = jsonObj.getString("name")
                                }
                            }
                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("TAG", "onActivityResult--> " + resultCode)
        if (resultCode == 101 || resultCode == 102) {
            val intent = Intent(data)
            intent.setClass(activity, UpdateDailyReportActivity::class.java);
            intent.putExtra("code", resultCode)
            intent.putExtra("project", project)
            startActivity(intent)
        } else if (resultCode == 103) {
            Toast.makeText(activity, "请检查相机权限~", Toast.LENGTH_SHORT).show();
        }
    }

    private fun getPathByUri(context: Context, uri: Uri): String? {
        val path: String
        path = if (DocumentsContract.isDocumentUri(context, uri)) {
            UriToPathUtils.getPath(context, uri)
        } else {
            UriToPathUtils.getEncodedPath(context, uri)
        }
        return path
    }

    /**
     * 检查定位权限
     */
    private fun requestPermissions() {
        var rxPermission = RxPermissions(activity!!)

        rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        ).subscribe({ permission ->
            if (permission.granted) {
                // 用户已经同意该权限
                Log.e("TAG", permission.name + " is granted.")
                if (permission.name.equals(Manifest.permission.CAMERA)) {
                    startActivityForResult(Intent(activity!!, CameraActivity::class.java), 100);
                }
            } else if (permission.shouldShowRequestPermissionRationale) {
                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                ToastUtil.show(activity!!, "拒绝了该权限，没有选中『不再询问』")
                Log.e("TAG", permission.name + " is denied. More info should be provided.")
            } else {
                // 用户拒绝了该权限，而且选中『不再询问』
                ToastUtil.show(activity!!, "拒绝了该权限，而且选中『不再询问』")
                Log.e("TAG", permission.name + " is denied.")
            }

        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(location: AMapLocation) {
        Log.e("TAG", "定位事件")
        if (null != location) {
            getWeather(location.adCode)
        }
    }

    private fun getWeather(code: String) {
        var params: HashMap<String, Any> = HashMap()
        params["city"] = code
        params["key"] = "eb11935d19133c09530dc67e63cb1393"
        // 可选值：base/all
        // base:返回实况天气
        // all:返回预报天气
        params["extensions"] = "base"

        RxRestClient.create()
                .url("https://restapi.amap.com/v3/weather/weatherInfo")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.e("TAG", "天气:" + result)
                            var weather = gson.fromJson(result, LiveWeather::class.java)
                            tempTv.text = weather.lives[0].temperature + "℃"
                            weatherTv.text = weather.lives[0].weather
                            ProjectData.getInstance().weather = weather
                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }

}
