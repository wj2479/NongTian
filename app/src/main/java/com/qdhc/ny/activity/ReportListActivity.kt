package com.qdhc.ny.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ReportAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bmob.Project
import com.qdhc.ny.bmob.Report
import com.qdhc.ny.bmob.UserInfo
import com.qdhc.ny.common.Constant
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 周报日报 月报 列表
 */
class ReportListActivity : BaseActivity() {

    lateinit var project: Project

    lateinit var user: UserInfo

    var type = 0

    override fun intiLayout(): Int {
        return R.layout.activity_report_list
    }

    override fun initView() {

        user = intent.getSerializableExtra("user") as UserInfo
        if (user.role == 1) {
            title_tv_right.visibility = View.VISIBLE
            title_tv_right.text = "添加  "
        }
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
        title_tv_right.setOnClickListener {
            var intent = Intent(this, AddReportActivity::class.java)
            intent.putExtra("project", project)
            intent.putExtra("type", type)
            startActivity(intent)
        }
    }

    override fun initData() {
        project = intent.getSerializableExtra("project") as Project
        type = intent.getIntExtra("type", 0)

        when (type) {
            Constant.REPORT_TYPE_DAY -> title_tv_title.text = "工程日报列表"
            Constant.REPORT_TYPE_WEEK -> title_tv_title.text = "工程周报列表"
            Constant.REPORT_TYPE_MONTH -> title_tv_title.text = "工程月报列表"
        }
        initRefresh()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    /**
     * 项目的日报记录
     */
    var reports = ArrayList<Report>()

    lateinit var mAdapter: ReportAdapter

    private fun initRefresh() {
        smrw!!.layoutManager = LinearLayoutManager(mContext)
        smrw.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(mContext, R.color.backgroundColor)))
        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            if (reports.size > position) {
                var report = reports[position]
                var intent = Intent(this@ReportListActivity, ReportDetailsActivity::class.java)
                intent.putExtra("project", project)
                intent.putExtra("report", report)
                startActivity(intent)
            }
        }

        mAdapter = ReportAdapter(this, reports)
        smrw.adapter = mAdapter

        val emptyView = layoutInflater.inflate(com.qdhc.ny.R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(com.qdhc.ny.R.id.tv_empty).text = "暂无记录"
        //添加空视图
        mAdapter.emptyView = emptyView
    }

    //获取数据
    fun getData() {
        val categoryBmobQuery = BmobQuery<Report>()
        categoryBmobQuery.addWhereEqualTo("pid", project.objectId)
        categoryBmobQuery.addWhereEqualTo("type", type)
        categoryBmobQuery.order("-createdAt")
        categoryBmobQuery.findObjects(
                object : FindListener<Report>() {
                    override fun done(list: List<Report>?, e: BmobException?) {
                        if (e == null) {
                            reports.clear()
                            reports.addAll(list!!)
                            mAdapter.notifyDataSetChanged()
                        } else {
                            Log.e("异常-----》", e.toString())
                        }
                    }
                })
    }
}
