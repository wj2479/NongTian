package com.qdhc.ny.activity

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.reflect.TypeToken
import com.qdhc.ny.R
import com.qdhc.ny.adapter.TraceAnalysisAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.*
import com.sj.core.net.RestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.GsonUtil
import com.sj.core.utils.SharedPreferencesUtil
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_trace_analysis.*
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 路线分析
 * @author shenjian
 * @date 2019/3/29
 */
class TraceAnalysisActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return R.layout.activity_trace_analysis
    }

    override fun initView() {

    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
        initRefresh()
    }
   var userId=0
    var date=""
    override fun initData() {
        title_tv_title.text = intent.getStringExtra("title")
        userId=intent.getIntExtra("userId", 0)
        date= intent.getStringExtra("date")
        getPointReport(userId,date )


    }
    var datas = ArrayList<TraceAnalysisInfo>()
    private fun initRefresh() {
        main_srl.setOnRefreshListener {
            //刷新服务
            getPointReport(userId,date                )
        }
        smrw!!.layoutManager = LinearLayoutManager(mContext)
        smrw.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(mContext, R.color.white)))
        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->

        }

        // 使用默认的加载更多的View
        smrw.useDefaultLoadMore()
        // 加载更多的监听
//        smrw.setLoadMoreListener {
//            page++
//            getDatas()
//
//        }
        var mAdapter = TraceAnalysisAdapter(this, datas)
        smrw.adapter = mAdapter
        val emptyView = layoutInflater.inflate(R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(R.id.tv_empty).text="暂无数据"
        //添加空视图
        mAdapter.emptyView = emptyView

    }

    /***
     *获取最后一个位置
     * [userid] 查询的用户id
     * [date] 查询当天时间
     */
    fun getPointReport(userId: Int, date: String) {
        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(mContext, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(mContext, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(mContext, "signature")
        var params = java.util.HashMap<String, Any>()
        params["userId"] = userId
        params["date"] = date
        RestClient.create()
                .params(params)
                .headers(headers)
                .url("api/Position/GetPointReport")
                .request(object : IRequest {
                    override fun onRequestStart() {
                        Log.e("request", "onRequestStart")
                    }

                    override fun onRequestEnd() {
                        main_srl.isRefreshing = false
                    }
                }).success {
                    var data = GsonUtil.getInstance().fromJson<HttpResultList<TraceAnalysisInfo>>(it,
                            object : TypeToken<HttpResultList<TraceAnalysisInfo>>() {}.type)
                    if (data.isSuccess) {
                        datas.clear()
                        datas.addAll(data.data)
                        smrw.adapter.notifyDataSetChanged()
                    }
                }.failure {
                }.error { code, msg ->
                }
                .build()
                .post()
    }
}
