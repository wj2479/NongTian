package com.qdhc.ny.fragment

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ExpandableListView
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.activity.DailyReportDetailsActivity
import com.qdhc.ny.adapter.ProjectReportExpandAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.ProjectWithReport
import com.sj.core.net.Rx.RxRestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_attention.*
import kotlinx.android.synthetic.main.fragment_unqualified_report.expandListView
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

/**
 * 关注列表
 */
class AttentionFragment : BaseFragment() {

    var focusReportList = ArrayList<ProjectWithReport>()

    lateinit var mAdapter: ProjectReportExpandAdapter

    override fun lazyLoad() {
    }

    override fun initClick() {
    }

    override fun intiLayout(): Int {
        return R.layout.fragment_attention
    }

    override fun initData() {
    }

    override fun initView() {
        emptyView.findViewById<TextView>(R.id.tv_empty).text = "没有关注的日志"

        initRefresh()
    }

    private fun initRefresh() {
        expandListView.setGroupIndicator(null)

        mAdapter = ProjectReportExpandAdapter(context, focusReportList)
        expandListView.setAdapter(mAdapter)
        expandListView.setOnChildClickListener(object : ExpandableListView.OnChildClickListener {
            override fun onChildClick(parent: ExpandableListView?, v: View?, groupPosition: Int, childPosition: Int, id: Long): Boolean {
                var report = focusReportList[groupPosition].reports[childPosition]
                var intent = Intent(context, DailyReportDetailsActivity::class.java)
                intent.putExtra("report", report)
                startActivity(intent)
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getFocusReports(ProjectData.getInstance().userInfo.id)
    }

    /**
     * 获取关注的项目日报列表
     */
    private fun getFocusReports(uid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["uid"] = uid

        RxRestClient.create()
                .url("report/getFocusReports")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.e("TAG", "关注:" + result)
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                // 清除关注的列表
                                ProjectData.getInstance().focusReportIdList.clear()
                                focusReportList.clear()
                                var jArray = json.getJSONArray("result")
                                if (jArray.length() > 0) {
                                    for (index in 0 until jArray.length()) {
                                        val jsonObject = jArray.getJSONObject(index)
                                        val project = gson.fromJson(jsonObject.toString(), ProjectWithReport::class.java)
                                        focusReportList.add(project)
                                        // 记录关注的日报id
                                        project.reports.forEach { report ->
                                            ProjectData.getInstance().focusReportIdList.add(report.id)
                                        }
                                    }
                                    emptyView.visibility = View.GONE
                                } else {
                                    emptyView.visibility = View.VISIBLE
                                }
                                mAdapter.notifyDataSetChanged()
                                expandGroup()
                            }
                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }

    /**
     * 展开一级列表
     */
    fun expandGroup() {
        var groupCount = mAdapter.groupCount;
        Log.e("TAG", "关注条目:" + groupCount)
        for (i in 0 until focusReportList.size) {
            expandListView.expandGroup(i)
        }
    }
}
