package com.qdhc.ny.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.qdhc.ny.activity.AddProjectActivity
import com.qdhc.ny.activity.ProjectInfoActivity
import com.qdhc.ny.adapter.ProjectWithReportAndScheduleAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.entity.Project
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_sign_in_sear.smrw
import kotlinx.android.synthetic.main.fragment_contron_list.*


/**
 * 工程列表
 */
@SuppressLint("ValidFragment")
class ContradictionListFragment(areaId: Int, villageId: String, isShowTitle: Boolean) : BaseFragment() {

    var projectList = ArrayList<Project>()

    var areaId = 0

    var isShowTitle = true

    var villageId: String

    lateinit var userInfo: User

    init {
        this.areaId = areaId
        this.villageId = villageId
        this.isShowTitle = isShowTitle
    }

    override fun intiLayout(): Int {
        return com.qdhc.ny.R.layout.fragment_contron_list
    }

    override fun initView() {
        if (!isShowTitle)
            titleLayout.visibility = View.GONE
    }

    override fun initClick() {
        addIv.setOnClickListener { v ->
            var intent = Intent(context, AddProjectActivity::class.java)
            intent.putExtra("area", areaId)
            intent.putExtra("village", villageId)
            context?.startActivity(intent)
        }
    }

    override fun initData() {
        userInfo = SharedPreferencesUtils.loadLogin(context)
//        if (userInfo.role == 4) {
//            addIv.visibility = View.VISIBLE
//        } else {
//            addIv.visibility = View.GONE
//        }
        initRefresh()
    }

    override fun lazyLoad() {

    }

    lateinit var mAdapter: ProjectWithReportAndScheduleAdapter
    private fun initRefresh() {
        smrw!!.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        smrw!!.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(activity!!, com.qdhc.ny.R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            if (projectList.size == 0) {
                return@setSwipeItemClickListener
            }
//
            var project = projectList.get(position)
            var intent = Intent(context, ProjectInfoActivity::class.java)
            intent.putExtra("info", project)
            startActivity(intent)
        }

        mAdapter = ProjectWithReportAndScheduleAdapter(activity, projectList)
        smrw.adapter = mAdapter
        val emptyView = layoutInflater.inflate(com.qdhc.ny.R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(com.qdhc.ny.R.id.tv_empty).text = "暂无进度数据"
        //添加空视图
        mAdapter.emptyView = emptyView
        getProjectData()
    }

    // 记录请求的总次数
    var maxCount = 0
    var count = 0

    //获取数据
    fun getProjectData() {



    }

    /**
     * 获取项目的进度
     */
    fun getSchedule(project: Project) {
    }

}
