package com.qdhc.ny.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.adapter.DailyReportAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.entity.DailyReport
import com.qdhc.ny.entity.DaySchedule
import com.qdhc.ny.entity.Media
import com.qdhc.ny.entity.Project
import com.sj.core.net.Rx.RxRestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_daily_schedule_details.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

/**
 * 每日汇总详情
 */
class DailyScheduleDetailsActivity : BaseActivity() {

    var selectList = ArrayList<Media>()

    /**
     * 每日汇总列表
     */
    var reportList = ArrayList<DailyReport>()

    lateinit var adapter: DailyReportAdapter

    override fun intiLayout(): Int {
        return R.layout.activity_daily_schedule_details
    }

    override fun initView() {
        rlv.layoutManager = LinearLayoutManager(this)
        adapter = DailyReportAdapter(this, reportList)
        rlv.adapter = adapter

        adapter.setOnItemClickListener { adapter, v, position ->
            if (reportList.size == 0)
                return@setOnItemClickListener

            var dailyReport = reportList[position]
            var intent = Intent(this, DailyReportDetailsActivity::class.java)
            intent.putExtra("report", dailyReport)
            startActivity(intent)
        }

        val emptyView = layoutInflater.inflate(R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(R.id.tv_empty).text = "暂无记录"

        adapter.emptyView = emptyView
    }

    override fun initClick() {
        backIv.setOnClickListener { finish() }
    }

    override fun initData() {
        var project = intent.getSerializableExtra("project") as Project
        var daySchedule = intent.getSerializableExtra("daySchedule") as DaySchedule

        tv_title.text = project.name
        tv_district.text = daySchedule.date + " 上传列表"

        getProjectDailyReports(project.id, daySchedule.date)
    }

    /**
     * 获取项目的日报记录
     */
    fun getProjectDailyReports(pid: Int, date: String) {
        var params: HashMap<String, Any> = HashMap()
        params["pid"] = pid
        params["date"] = date
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
                                for (index in 0 until result.length()) {
                                    var jobj = result.getJSONObject(index)
                                    var dReport = gson.fromJson<DailyReport>(jobj.toString(), DailyReport::class.java)
                                    reportList.add(dReport)
                                }
                            }
                            adapter.notifyDataSetChanged()
                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }
}
