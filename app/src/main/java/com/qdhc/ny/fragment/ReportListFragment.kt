package com.qdhc.ny.fragment

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.qdhc.ny.adapter.ProjectWithReportAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.bmob.Project
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_sign_in_sear.smrw
import kotlinx.android.synthetic.main.fragment_contron_list.*

/**
 * 日志列表
 */
@SuppressLint("ValidFragment")
class ReportListFragment(areaId: Int, villageId: String, isShowTitle: Boolean) : BaseFragment() {

    var projectList = ArrayList<Project>()

    var areaId = 0
    var villageId: String
    var isShowTitle = true

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
        addIv.visibility = View.GONE
        titleTv.text = "工程质量"
        if (!isShowTitle)
            titleLayout.visibility = View.GONE
    }

    override fun initClick() {
    }

    override fun initData() {
        userInfo = SharedPreferencesUtils.loadLogin(context)
        initRefresh()
    }

    override fun lazyLoad() {

    }

    lateinit var mAdapter: ProjectWithReportAdapter
    private fun initRefresh() {
        smrw!!.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        smrw!!.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(activity!!, com.qdhc.ny.R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            if (projectList.size == 0) {
                return@setSwipeItemClickListener
            }
        }

        mAdapter = ProjectWithReportAdapter(activity, projectList)
        smrw.adapter = mAdapter
        val emptyView = layoutInflater.inflate(com.qdhc.ny.R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(com.qdhc.ny.R.id.tv_empty).text = "暂无质量数据"
        //添加空视图
        mAdapter.emptyView = emptyView
    }

    override fun onResume() {
        super.onResume()
        projectList.clear()
        getProjectData()
    }

    //获取数据
    fun getProjectData() {
        val categoryBmobQuery = BmobQuery<Project>()
        categoryBmobQuery.addWhereEqualTo("area", areaId)
        if (userInfo.role == 1) {
        } else {
            categoryBmobQuery.addWhereEqualTo("village", villageId)
        }
        categoryBmobQuery.order("-createdAt")
        categoryBmobQuery.findObjects(
                object : FindListener<Project>() {
                    override fun done(list: List<Project>?, e: BmobException?) {
                        if (e == null) {
                            Log.e("工程列表结果-----》", list?.size.toString())
                            projectList.addAll(list!!)
                            mAdapter.notifyDataSetChanged()
                        } else {
                            Log.e("异常-----》", e.toString())
                        }
                    }
                })
    }

}
