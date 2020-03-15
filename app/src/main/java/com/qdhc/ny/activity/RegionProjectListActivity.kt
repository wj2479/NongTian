package com.qdhc.ny.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ProjectAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.entity.Project
import com.sj.core.net.Rx.RxRestClient
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_region_project_list.*
import kotlinx.android.synthetic.main.activity_sign_in_sear.smrw
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

/**
 * 区域工程列表
 */
class RegionProjectListActivity : BaseActivity() {

    lateinit var project: Project
    var projectList = ArrayList<Project>()

    override fun intiLayout(): Int {
        return R.layout.activity_region_project_list
    }

    override fun initView() {
    }

    override fun initClick() {
        backIv.setOnClickListener { finish() }
    }

    override fun initData() {
        if (intent.hasExtra("project")) {
            project = intent.getSerializableExtra("project") as Project

            tv_title.text = project.name + "工程列表"

            if (intent.hasExtra("subProject")) {
                projectList = intent.getSerializableExtra("subProject") as ArrayList<Project>
            } else {
                getSubProjects(project.id)
            }
            initRefresh()
        }
    }

    lateinit var mAdapter: ProjectAdapter
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
            getClickSubProjects(project)
        }

        mAdapter = ProjectAdapter(this, projectList)
        smrw.adapter = mAdapter
        val emptyView = layoutInflater.inflate(com.qdhc.ny.R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(com.qdhc.ny.R.id.tv_empty).text = "暂无数据"
        //添加空视图
        mAdapter.emptyView = emptyView
    }

    /**
     * 获取项目的子项目
     */
    fun getSubProjects(pid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["pid"] = pid
        RxRestClient.create()
                .url("project/getSubProjectsByParentId")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                var jArray = json.getJSONArray("result")
                                Log.e("TAG", "请求22成功:" + jArray.toString())

                                for (index in 0 until jArray.length()) {
                                    val jsonObject = jArray.getJSONObject(index)
                                    val project = gson.fromJson(jsonObject.toString(), Project::class.java)
                                    projectList.add(project)
                                }
                                mAdapter.notifyDataSetChanged()
                            } else {
                                Log.e("TAG", "请求22失败:" + result)
                            }

                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }

    fun getClickSubProjects(project: Project) {
        getSubProjects(project.id, object : Consumer<String> {
            override fun accept(result: String?) {
                var json = JSONObject(result)
                if (json.getInt("code") == 1000) {
                    var jArray = json.getJSONArray("result")
                    Log.e("TAG", "请求22成功:" + jArray.toString())
                    var subProjectList = ArrayList<Project>()
                    for (index in 0 until jArray.length()) {
                        val jsonObject = jArray.getJSONObject(index)
                        val project = gson.fromJson(jsonObject.toString(), Project::class.java)
                        subProjectList.add(project)
                    }

                    var intent = Intent()
                    intent.putExtra("project", project)
                    if (subProjectList.size == 0) {
                        intent.setClass(this@RegionProjectListActivity, ProjectInfoActivity::class.java)
                    } else {
                        intent.setClass(this@RegionProjectListActivity, RegionProjectListActivity::class.java)
                        intent.putExtra("subProject", subProjectList)
                    }
                    startActivity(intent)
                } else {
                    Log.e("TAG", "请求22失败:" + result)
                }
            }
        }, object : Consumer<Throwable> {
            override fun accept(t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }


    /**
     * 获取项目的子项目
     */
    fun getSubProjects(pid: Int, onNext: Consumer<String>, onError: Consumer<Throwable>) {
        var params: HashMap<String, Any> = HashMap()
        params["pid"] = pid
        RxRestClient.create()
                .url("project/getSubProjectsByParentId")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError)
    }


}
