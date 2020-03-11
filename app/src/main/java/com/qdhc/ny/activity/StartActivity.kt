package com.qdhc.ny.activity

import android.content.Intent
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import com.qdhc.ny.LoginActivity
import com.qdhc.ny.Main3Activity
import com.qdhc.ny.MainActivity
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.Role
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.sj.core.net.Rx.RxRestClient
import com.sj.core.utils.SharedPreferencesUtil
import com.sj.core.utils.ToastUtil
import com.tencent.bugly.crashreport.CrashReport
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class StartActivity : BaseActivity() {

    override fun intiLayout(): Int {
        return R.layout.activity_start
    }

    override fun initView() {

    }

    private fun init() {
        var saveUser = SharedPreferencesUtils.loadLogin(mContext);
        var savePwd = SharedPreferencesUtil.get(mContext, "pwd")
        if (TextUtils.isEmpty(saveUser.userName) || TextUtils.isEmpty(savePwd)) {
            startActivity(Intent(mContext, LoginActivity::class.java))
            finish()
        } else {
            loginByUserName(saveUser.userName, savePwd)
        }
    }

    var map = TreeMap<String, Int>();
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    fun getData() {
//        val categoryBmobQuery = BmobQuery<Project>()
////        categoryBmobQuery.addWhereEqualTo("role", 1)
////        categoryBmobQuery.addWhereEqualTo("city", "hARs111J")
//        categoryBmobQuery.setLimit(500)
//        categoryBmobQuery.findObjects(object : FindListener<Project>() {
//            override fun done(list: List<Project>?, e: BmobException?) {
//                if (e == null) {
//                    if (list != null) {
//
//                        var ss = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "data.txt";
//
//                        var file = File(ss)
//                        if (file.exists()) {
//                            file.delete()
//                        }
//
//                        file.createNewFile()
//
//                        var fos = FileOutputStream(file)
//
//                        var title = "objectid,pname\n"
//                        fos.write(title.toByteArray())
//
//                        list.forEach { user ->
//
//                            var data = user.objectId + "," + user.name + "\n"
//                            fos.write(data.toByteArray())
//                            fos.flush()
//                            Log.e("TAG", data)
//
////                            val categoryBmobQuery = BmobQuery<DailyReport>()
////                            categoryBmobQuery.addWhereEqualTo("uid", user.objectId);
////                            var createdAtStart = "2019-11-01 00:00:00";
////                            var createdAtDateStart = sdf.parse(createdAtStart);
////                            var bmobCreatedAtDateStart = BmobDate(createdAtDateStart);
////
////                            var createdAtEnd = "2020-01-01 00:00:00";
////                            var createdAtDateEnd = sdf.parse(createdAtEnd);
////                            var bmobCreatedAtDateEnd = BmobDate(createdAtDateEnd);
////
////                            categoryBmobQuery.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDateStart);
////                            categoryBmobQuery.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDateEnd);
////
////                            categoryBmobQuery.findObjects(object : FindListener<DailyReport>() {
////                                override fun done(list: MutableList<DailyReport>?, e: BmobException?) {
////                                    if (e == null) {
////                                        if (list != null) {
////                                            list.forEach { report ->
////                                                val categoryBmobQuery = BmobQuery<ContradictPic>()
////                                                categoryBmobQuery.addWhereEqualTo("contradict", report.objectId)
////                                                categoryBmobQuery.count(ContradictPic::class.java, object : CountListener() {
////                                                    override fun done(count: Int?, e: BmobException?) {
////                                                        if (e == null && count != null) {
//////                                                            Log.e("TAG", user.objectId + " " + user.nickName + " " + report.objectId + "  " + report.createdAt.substring(0, 10) + " 计数:" + count.toString())
////                                                            var key = user.nickName + "," + report.createdAt.substring(0, 10)
////
////                                                            if (map.containsKey(key)) {
////                                                                map.put(key, count + map.get(key)!!)
////                                                            } else {
////                                                                map.put(key, count)
////                                                            }
////                                                        }
////                                                    }
////                                                })
////                                            }
////                                        }
////                                    }
////                                }
////                            })
//                        }
//                        fos.close()
//                    }
//                }
//            }
//        })
//
////        Handler().postDelayed({
////            var ss = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "data.txt";
////
////            var file = File(ss)
////            if (file.exists()) {
////                file.delete()
////            }
////
////            file.createNewFile()
////
////            var fos = FileOutputStream(file)
////
////            var title = "name,date,count\n"
////            fos.write(title.toByteArray())
////
////            map.entries.forEach { entry ->
////                var data = entry.key + "," + entry.value.toString() + "\n"
////                fos.write(data.toByteArray())
////                fos.flush()
////                Log.e("TAG", data)
////            }
////            fos.close()
////
////        }, 5000)
//    }


    fun loginByUserName(username: String, password: String) {
        var params: HashMap<String, Any> = HashMap()
        params["username"] = username
        params["password"] = password
        RxRestClient.create()
                .url("user/login")
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
                                var user = gson.fromJson(jsonObject.toString(), User::class.java)
                                Log.e("TAG", "登录成功:" + user.toString())
                                onLoginSuccess(user)
                            } else {
                                Log.e("TAG", "登录失败:" + result)
                                onLoginFailed()
                            }
                            finish()
                        },
                        { throwable ->
                            throwable.printStackTrace()
                            onLoginFailed()
                            finish()
                        })
    }

    /**
     * 登录成功
     */
    fun onLoginSuccess(user: User) {
        ProjectData.getInstance().userInfo = user

        CrashReport.setUserId(user.userName)

        when (user.role.code) {
            Role.CODE.SUPERVISOR.value -> startActivity(Intent(mContext, MainActivity::class.java)) // 监理角色
            else -> startActivity(Intent(mContext, Main3Activity::class.java))
        }
    }

    /**
     * 登录失败
     */
    fun onLoginFailed() {
        ToastUtil.show(this, "自动登录失效，请重新登录")
        startActivity(Intent(mContext, LoginActivity::class.java))
    }

    override fun initClick() {
    }

    var flag = true
    private val TIMER = 999
    lateinit var thread: Thread
    override fun initData() {
        thread = Thread(Runnable {
            if (flag) {
                try {
                    Thread.sleep(2000) //休眠
                    mHanler.sendEmptyMessage(TIMER)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        })
        thread.start()
    }

    private val mHanler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                TIMER -> {
                    init()
                    flag = false
                }
                else -> {
                }
            }//去执行定时操作逻辑
        }
    }

    private fun stopTimer() {
        flag = false
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
        mHanler.removeCallbacks(thread)
    }

}
