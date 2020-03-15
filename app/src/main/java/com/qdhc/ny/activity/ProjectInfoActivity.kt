package com.qdhc.ny.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.lcodecore.ILabel
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ProjectSchueduleAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.TagLabel
import com.qdhc.ny.entity.DailyReport
import com.qdhc.ny.entity.Project
import com.qdhc.ny.entity.Role
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.sj.core.net.Rx.RxRestClient
import com.sj.core.utils.ToastUtil
import com.xw.repo.BubbleSeekBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_contradiction_info.bt_comment
import kotlinx.android.synthetic.main.activity_contradiction_info.descriptionTv
import kotlinx.android.synthetic.main.activity_contradiction_info.projectTv
import kotlinx.android.synthetic.main.activity_contradiction_info.rlv
import kotlinx.android.synthetic.main.activity_project_info.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


/**
 * 工程详情
 */
class ProjectInfoActivity : BaseActivity() {

    lateinit var project: Project

    lateinit var adapter: ProjectSchueduleAdapter

    lateinit var userInfo: User

    var reportList = ArrayList<DailyReport>()

    var initSchedule = 0

    override fun intiLayout(): Int {
        return R.layout.activity_project_info
    }

    override fun initView() {
        title_tv_title.text = "工程详情"

        rlv.layoutManager = LinearLayoutManager(this)
        adapter = ProjectSchueduleAdapter(this, reportList)
        rlv.adapter = adapter

        adapter.setOnItemClickListener { adapter, v, position ->
            var dailyReport = reportList[position]
            var intent = Intent(this, DailyReportDetailsActivity::class.java)
            intent.putExtra("report", dailyReport)
            startActivity(intent)
        }
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
        bt_comment.setOnClickListener {
            var intent = Intent(this, UpdateDailyReportActivity::class.java)
            intent.putExtra("project", project)
            startActivityForResult(intent, 103)
        }

        bt_report.setOnClickListener {
            var intent = Intent(this, ReportAllListActivity::class.java)
            intent.putExtra("user", userInfo)
            intent.putExtra("project", project)
            startActivity(intent)
        }

        targetTv.setOnClickListener {
            val target = targetTv.text.toString().trim()
            if (target.isNullOrEmpty()) {
                initDialog()
            } else {
                ToastUtil.show(this@ProjectInfoActivity, "本月的目标已经设置完毕，请下个月在来设置")
            }
        }
    }

    private fun initDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_set_target, null);
        val bubbleSeekbar = view.findViewById<BubbleSeekBar>(R.id.bubbleSeekbar)
        bubbleSeekbar.setProgress(initSchedule * 1.0f)
        bubbleSeekbar.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
            }

            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                if (progress < initSchedule) {
                    bubbleSeekbar.setProgress(initSchedule * 1.0f)
                }
            }

            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
            }
        }
        val builder = AlertDialog.Builder(this)
                .setView(view)
                .setTitle("请设置本月的目标进度")
                .setPositiveButton("确 定", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        var target = bubbleSeekbar.progress
                        if (target > initSchedule) {
                            showDialog("正在设置")
                            setProjectTarget(project.id, userInfo.id, target)
                        }
                    }
                });

        builder.create().show();
    }

    override fun initData() {
        userInfo = SharedPreferencesUtils.loadLogin(this)

        project = intent.getSerializableExtra("project") as Project

        projectTv.text = project.name
        descriptionTv.text = project.info
        areaTv.text = project.area.name

        initSchedule = project.process

        if (null != project.tags) {
            var split = project.tags.trim().split(",")

            // 标签的数据
            var labels = ArrayList<ILabel>()
            split.forEach { text ->
                if (!text.trim().isEmpty())
                    labels.add(TagLabel(text, text))
            }

            if (labels.size == 0) {
                labels.add(TagLabel("无标签", "无标签"))
            }

            label_me.setLabels(labels)
        }

        // 检测是不是需要显示
        checkBtnShow(project.process)
        scheduleTv.text = project.process.toString() + "%"

        if (userInfo.role.code != Role.CODE.SUPERVISOR.value) {
            commentButLayout.visibility = View.GONE
        }

        getProjectTarget(project.id)
        initCreateProcess()
        getProjectDailyReports(project.id)
    }

    /**
     * 获取项目的阅读计划
     */
    fun getProjectTarget(pid: Int) {
        // 获取当月目标进度
        var params: HashMap<String, Any> = HashMap()
        params["pid"] = pid
        RxRestClient.create()
                .url("project/getProjectPlanMonth")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                var target = json.getInt("result")
                                if (target > 0 && target <= 100) {
                                    targetTv.text = target.toString() + "%"
                                } else {
                                    targetTv.hint = "本月未设置"
                                }
                            }
                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }

    /**
     * 设置项目当月进度
     */
    fun setProjectTarget(pid: Int, uid: Int, target: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["pid"] = pid
        params["uid"] = uid
        params["target"] = target
        RxRestClient.create()
                .url("project/setProjectPlanThisMonth")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.e("TAG", "设置结果  " + result)
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                targetTv.text = target.toString() + "%"
                                ToastUtil.show(this@ProjectInfoActivity, "本月目标进度设置成功")
                            } else {

                            }
                            dismissDialogNow()
                        },
                        { throwable ->
                            ToastUtil.show(this@ProjectInfoActivity, "本月目标进度设置失败")
                            throwable.printStackTrace()
                            dismissDialogNow()
                        })
    }

    fun initCreateProcess() {
        var dailyReport = DailyReport()
        dailyReport.title = "工程创建"
        dailyReport.content = "工程创建"
        dailyReport.schedule = 0
        dailyReport.pid = project.id
        dailyReport.createTime = project.createTime
        reportList.add(dailyReport)
    }

    /**
     * 获取项目的日报记录
     */
    fun getProjectDailyReports(pid: Int) {
        // 获取当月目标进度
        var params: HashMap<String, Any> = HashMap()
        params["pid"] = pid
        RxRestClient.create()
                .url("report/getDailyReports")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.e("TAG", "日报结果  " + result)
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                var result = json.getJSONArray("result")
                                reportList.clear()
                                for (index in 0 until result.length()) {
                                    var jobj = result.getJSONObject(index)
                                    var dReport = gson.fromJson<DailyReport>(jobj.toString(), DailyReport::class.java)

                                    reportList.add(dReport)
                                }
                            }
                            adapter.notifyDataSetChanged()
                        },
                        { throwable ->
                            adapter.notifyDataSetChanged()
                            throwable.printStackTrace()
                        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 103) {
            if (data != null) {
                val schedule = data.getIntExtra("schedule", project.process)
                project.process = schedule
                scheduleTv.text = project.process.toString() + "%"
                checkBtnShow(schedule)

                // 重新请求日志数据
                getProjectDailyReports(project.id)
            }
        }
    }

    fun checkBtnShow(process: Int) {
        if (process == 100) {
            bt_comment.visibility = View.GONE
        }
    }
}
