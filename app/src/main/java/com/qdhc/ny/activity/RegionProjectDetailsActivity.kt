package com.qdhc.ny.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.qdhc.ny.R
import com.qdhc.ny.adapter.RegionProjectAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectLevel
import com.qdhc.ny.entity.Project
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_region_project_details.*

/**
 * 区域项目详情
 */
class RegionProjectDetailsActivity : BaseActivity() {

    lateinit var projectList: ArrayList<Project>

    override fun intiLayout(): Int {
        return R.layout.activity_region_project_details
    }

    override fun initView() {

    }

    override fun initClick() {
        backIv.setOnClickListener { finish() }
    }

    override fun initData() {
        if (intent.hasExtra("subProjects")) {
            projectList = intent.getSerializableExtra("subProjects") as ArrayList<Project>
            if (projectList.size > 0) {
                var level = ProjectLevel.getEnumType(projectList.get(0).level)
                tv_district.text = level.desc + "进度详情"
            }
        } else {
            projectList = ArrayList<Project>()
        }

        if (intent.hasExtra("project")) {
            var project = intent.getSerializableExtra("project") as Project
            tv_title.text = project.name
        }

        Log.e("传递数据-----》", "数据" + projectList.size)

        initRefresh()
    }

    lateinit var mAdapter: RegionProjectAdapter
    private fun initRefresh() {
        smrw!!.layoutManager = LinearLayoutManager(this)
        smrw!!.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(this, R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            if (projectList.size == 0) {
                return@setSwipeItemClickListener
            }

            var project = projectList.get(position)
            var intent = Intent(this, RegionProjectListActivity::class.java)
            intent.putExtra("regionProject", project)
            startActivity(intent)
        }

        mAdapter = RegionProjectAdapter(this, projectList)
        smrw.adapter = mAdapter
    }
}
