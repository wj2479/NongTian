package com.qdhc.ny.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ImageAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.Media
import com.qdhc.ny.entity.Project
import com.qdhc.ny.entity.User
import kotlinx.android.synthetic.main.activity_project_schedule_info.*
import kotlinx.android.synthetic.main.layout_title_theme.*

class ProjectScheduleInfoActivity : BaseActivity() {

    lateinit var project: Project

    lateinit var adapter: ImageAdapter

    lateinit var userInfo: User

    var selectList = ArrayList<Media>()

    override fun intiLayout(): Int {
        return R.layout.activity_project_schedule_info
    }

    override fun initView() {
        title_tv_title.text = "工程进度详情"

        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = GridLayoutManager(this, 4) as RecyclerView.LayoutManager?
        adapter = ImageAdapter(this, selectList)
        adapter.setOnItemClickListener { position, v ->
            var url = selectList.get(position).url

            var intent = Intent(this, ImageActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }
        rlv.adapter = adapter
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }

    }

    override fun initData() {
        userInfo = ProjectData.getInstance().userInfo
        project = intent.getSerializableExtra("project") as Project

        nameTv.text = project.name
    }

}
