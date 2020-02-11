package com.qdhc.ny.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ProjectWithReportAndScheduleAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.entity.Project
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_region_project_list.*
import kotlinx.android.synthetic.main.activity_sign_in_sear.smrw

/**
 * 区域工程列表
 */
class RegionProjectListActivity : BaseActivity() {

    lateinit var project: Project
    lateinit var projectList: List<Project>

    override fun intiLayout(): Int {
        return R.layout.activity_region_project_list
    }

    override fun initView() {
    }

    override fun initClick() {
    }

    override fun initData() {
        project = intent.getSerializableExtra("regionProject") as Project
        projectList = ArrayList()

        titleTv.text = project.name + "工程列表"

        initRefresh()
    }

    lateinit var mAdapter: ProjectWithReportAndScheduleAdapter
    private fun initRefresh() {
        smrw!!.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        smrw!!.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(this, R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            if (projectList.size == 0) {
                return@setSwipeItemClickListener
            }
//
            var project = projectList.get(position)
            var intent = Intent(this, ProjectInfoActivity::class.java)
            intent.putExtra("info", project)
            startActivity(intent)
        }

        mAdapter = ProjectWithReportAndScheduleAdapter(this, projectList)
        smrw.adapter = mAdapter
        val emptyView = layoutInflater.inflate(com.qdhc.ny.R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(com.qdhc.ny.R.id.tv_empty).text = "暂无数据"
        //添加空视图
        mAdapter.emptyView = emptyView
    }

}
