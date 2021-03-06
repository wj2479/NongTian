package com.qdhc.ny.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.qdhc.ny.R
import com.qdhc.ny.adapter.RegionProjectAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.entity.Project
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_region_project_details.*
import java.util.*
import kotlin.collections.ArrayList

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

            //排序
            Collections.sort(projectList, object : Comparator<Project> {
                override fun compare(o1: Project?, o2: Project?): Int {
                    if (o1?.process!! > o2?.process!!) {
                        return -1
                    } else if (o1.process < o2.process) {
                        return 1
                    } else {
                        return 0
                    }
                }
            })

        } else {
            projectList = ArrayList()
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
            intent.putExtra("project", project)
            startActivity(intent)
        }

        mAdapter = RegionProjectAdapter(this, projectList)
        smrw.adapter = mAdapter
    }
}
