package com.qdhc.ny.fragment


import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.activity.ReportDetailsActivity
import com.qdhc.ny.adapter.ReportAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.bmob.Report
import com.qdhc.ny.entity.Project
import com.qdhc.ny.entity.User
import com.qdhc.ny.eventbus.ProjectEvent
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_sign_in_sear.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * 日报 周报 月报
 * A simple [Fragment] subclass.
 */
@SuppressLint("ValidFragment")
class WeekMonthReportFragment(project: Project, type: Int) : BaseFragment() {

    lateinit var userInfo: User

    var reportProjects = ArrayList<Project>()

    /**
     * 项目的日报记录
     */
    var reports = ArrayList<Report>()

    lateinit var mAdapter: ReportAdapter

    var type = 0
    var project: Project

    init {
        this.type = type
        this.project = project
    }

    override fun intiLayout(): Int {
        return com.qdhc.ny.R.layout.fragment_week_month_report
    }

    override fun initView() {
        initRefresh()
    }

    private fun initRefresh() {
        smrw!!.layoutManager = LinearLayoutManager(context)
        smrw.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(context!!, R.color.backgroundColor)))
        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            if (reports.size > position) {
                var report = reports[position]
                var intent = Intent(context, ReportDetailsActivity::class.java)
                intent.putExtra("project", project)
                intent.putExtra("report", report)
                startActivity(intent)
            }
        }

        mAdapter = ReportAdapter(activity, reports)
        smrw.adapter = mAdapter

        val emptyView = layoutInflater.inflate(com.qdhc.ny.R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(R.id.tv_empty).text = "暂无记录"
        //添加空视图
        mAdapter.emptyView = emptyView
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ProjectEvent) {
//        Log.e("TAG", "进度事件--->" + ProjectData.getInstance().projects)
    }

    override fun initClick() {
    }

    override fun initData() {
        userInfo = SharedPreferencesUtils.loadLogin(context)
    }

    override fun lazyLoad() {
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    //获取数据
    fun getData() {
    }
}
