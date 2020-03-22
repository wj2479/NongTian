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
import com.qdhc.ny.entity.Project
import com.qdhc.ny.entity.ProjectWithReport
import com.sj.core.net.Rx.RxRestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_unqualified_report.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

/**
 * 不合格的日报列表
 */
class UnqualifiedReportFragment : BaseFragment() {

    var unqualifiedReportList = ArrayList<ProjectWithReport>()

    lateinit var mAdapter: ProjectReportExpandAdapter

    override fun lazyLoad() {
    }

    override fun initClick() {
    }

    override fun intiLayout(): Int {
        return R.layout.fragment_unqualified_report
    }

    override fun initData() {
    }

    override fun initView() {
        emptyView.findViewById<TextView>(R.id.tv_empty).text = "没有不合格的日志"
        initRefresh()
    }

    private fun initRefresh() {
        expandListView.setGroupIndicator(null)

        mAdapter = ProjectReportExpandAdapter(context, unqualifiedReportList)
        expandListView.setAdapter(mAdapter)

        expandListView.setOnChildClickListener(object : ExpandableListView.OnChildClickListener {
            override fun onChildClick(parent: ExpandableListView?, v: View?, groupPosition: Int, childPosition: Int, id: Long): Boolean {
                var report = unqualifiedReportList[groupPosition].reports[childPosition]
                var intent = Intent(context, DailyReportDetailsActivity::class.java)
                intent.putExtra("report", report)
                startActivity(intent)
                return true
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(proj: Project) {
        Log.e("TAG", "项目事件")
        if (null != proj) {
            getUnqualifiedReports(proj.id)
        }
    }

    /**
     * 获取不合格的项目列表
     */
    private fun getUnqualifiedReports(pid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["pid"] = pid

        RxRestClient.create()
                .url("report/getUnqualifiedReportList")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.e("TAG", "不合格:" + result)
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                var jArray = json.getJSONArray("result")
                                if (jArray.length() > 0) {
                                    for (index in 0 until jArray.length()) {
                                        val jsonObject = jArray.getJSONObject(index)
                                        val project = gson.fromJson(jsonObject.toString(), ProjectWithReport::class.java)
                                        unqualifiedReportList.add(project)
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

    fun expandGroup() {
        var groupCount = mAdapter.groupCount;
        for (i in 0 until groupCount) {
            expandListView.expandGroup(i)
        }
    }

}
