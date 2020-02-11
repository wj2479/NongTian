package com.qdhc.ny.activity

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.reflect.TypeToken
import com.qdhc.ny.R
import com.qdhc.ny.adapter.SelectManageAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.HttpResultList
import com.qdhc.ny.bmob.UserInfo
import com.sj.core.net.RestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.GsonUtil
import com.sj.core.utils.SharedPreferencesUtil
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_select_manage.*
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 选择运管员
 * @author shenjian
 * @date 2019/3/23
 */
class MyClientManageActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return R.layout.activity_select_manage
    }

    override fun initView() {
        title_tv_title.text = "选择人员"
        initRefresh()
    }

    var page = 1
    var datas = ArrayList<UserInfo>()
    lateinit var mAdapter: SelectManageAdapter
    private fun initRefresh() {
        main_srl.setOnRefreshListener {
            //刷新服务
            page = 1
            getData()
        }
        smrw!!.layoutManager = LinearLayoutManager(mContext)
        smrw!!.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(mContext, R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            setResult(Activity.RESULT_OK, Intent().putExtra("user", datas[position]))
            finish()
        }

        // 使用默认的加载更多的View
        smrw.useDefaultLoadMore()
        // 加载更多的监听
        smrw.setLoadMoreListener {
            page++
            getData()

        }
        mAdapter = SelectManageAdapter(this, datas)
        smrw.adapter = mAdapter


    }

    //获取数据
    fun getData() {
        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(mContext, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(mContext, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(mContext, "signature")
        var params = java.util.HashMap<String, Any>()
        params["page"] = page
        params["pagesize"] = 40
        RestClient.create()
                .params(params)
                .headers(headers)
                .url("api/User/GetUserList")
                .request(object : IRequest {
                    override fun onRequestStart() {
                        Log.e("request", "onRequestStart")
                    }

                    override fun onRequestEnd() {
                        if (page == 1) {
                            main_srl.isRefreshing = false
                        }
                    }
                }).success {
                    var data = GsonUtil.getInstance().fromJson<HttpResultList<UserInfo>>(it,
                            object : TypeToken<HttpResultList<UserInfo>>() {}.type)
                    if (data.isSuccess) {
                        datas.addAll(data.data)
                        var data = GsonUtil.getInstance().fromJson<HttpResultList<UserInfo>>(it,
                                object : TypeToken<HttpResultList<UserInfo>>() {}.type)
                        if (page == 1) {
                            main_srl.isRefreshing = false
                            datas.clear()
                            if (data.data.size < 40) {
                                smrw.loadMoreFinish(true, false)
                            } else {
                                smrw.loadMoreFinish(false, true)
                            }
                        } else {
                            if (data.data.size < 40) {
                                smrw.loadMoreFinish(true, false)
                            }
                        }
                        datas.addAll(data.data)
                        smrw.adapter.notifyDataSetChanged()
                    } else {
                        if (page == 1) {
                            val emptyView = layoutInflater.inflate(R.layout.common_empty, null)
                            emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT)
                            emptyView.findViewById<TextView>(R.id.tv_empty).text = "暂无数据"
                            //添加空视图
                            mAdapter.emptyView = emptyView
                        } else
                            smrw.loadMoreFinish(true, false)
                    }
                }.failure {
                    //                    if (NetWorkUtil.isNetworkConnected(mContext)) {
//                        ToastUtil.show(mContext, resources.getString(R.string.net_error))
//                    }

                }.error { code, msg ->

                }
                .build()
                .post()
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        getData()
    }


}
