package com.qdhc.ny.fragment


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.qdhc.ny.activity.CameraActivity
import com.qdhc.ny.activity.ProjectInfoActivity
import com.qdhc.ny.activity.UpdateDailyReportActivity
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.common.Constant
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.Area
import com.qdhc.ny.entity.Project
import com.qdhc.ny.entity.User
import com.sj.core.net.Rx.RxRestClient
import com.sj.core.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_jianli.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

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
                getPermissions()
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

//        tv_all_sort.text = "18"
//        tv_sort.text = "80%"

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

    val mHandler = Handler()

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

    val GET_PERMISSION_REQUEST = 100; //权限申请自定义码

    private fun getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                            .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context!!, Manifest.permission.RECORD_AUDIO) == PackageManager
                            .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) == PackageManager
                            .PERMISSION_GRANTED) {
                startActivityForResult(Intent(activity, CameraActivity::class.java), 100);
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(activity!!, arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA
                ), GET_PERMISSION_REQUEST);
            }
        } else {
            startActivityForResult(Intent(activity!!, CameraActivity::class.java), 100);
        }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GET_PERMISSION_REQUEST) {
            var size = 0;
            if (grantResults.size >= 1) {
                var writeResult = grantResults[0];
                //读写内存权限
                var writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;//读写内存权限
                if (!writeGranted) {
                    size++;
                }
                //录音权限
                var recordPermissionResult = grantResults[1];
                var recordPermissionGranted = recordPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!recordPermissionGranted) {
                    size++;
                }
                //相机权限
                var cameraPermissionResult = grantResults[2];
                var cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!cameraPermissionGranted) {
                    size++;
                }
                if (size == 0) {
                    startActivityForResult(Intent(activity, CameraActivity::class.java), 100);
                } else {
                    Toast.makeText(activity, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
