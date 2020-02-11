package com.qdhc.ny.activity

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.reflect.TypeToken
import com.qdhc.ny.R
import com.qdhc.ny.adapter.AchievementAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.HttpResultList
import com.qdhc.ny.bmob.UserInfo
import com.sj.core.net.RestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.GsonUtil
import com.sj.core.utils.SharedPreferencesUtil
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_achievement_ranking.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

/**
 * 业绩排行榜
 * @author shenjian
 * @date 2019/4/6
 */
class AchievementRankingActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return R.layout.activity_achievement_ranking
    }

    override fun initView() {
        title_iv_back.setOnClickListener { finish() }
        initRefresh()
    }

    var datas = ArrayList<UserInfo>()
    lateinit var mAdapter: AchievementAdapter
    private fun initRefresh() {
        main_srl.setOnRefreshListener {
            //刷新服务
            getDatas(true)
        }
        smrw!!.layoutManager = LinearLayoutManager(mContext)
        smrw.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(mContext, R.color.backgroundColor)))
        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->

        }

        // 使用默认的加载更多的View
        smrw.useDefaultLoadMore()
        // 加载更多的监听
        smrw.setLoadMoreListener {
            getDatas(false)
        }
        mAdapter = AchievementAdapter(mContext, datas)
        smrw.adapter = mAdapter


    }

    override fun initClick() {
    }

    override fun initData() {
        getDatas(true)
    }

    var page = 1
    fun getDatas(isResfrsh: Boolean) {
        if (isResfrsh) {
            page = 1
        } else {
            page++
        }
        var params = HashMap<String, Any>()
        params["page"] = page
        params["pagesize"] = 40
        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(mContext, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(mContext, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(mContext, "signature")
        RestClient.create()
                .params(params)
                .headers(headers)
                .url("api/BankData/GetSingleList")
                .request(object : IRequest {
                    override fun onRequestStart() {
                        Log.e("request", "onRequestStart")
                    }

                    override fun onRequestEnd() {
                        if (page == 1) {
                            main_srl.isRefreshing = false
                            smrw.loadMoreFinish(false, true)
                        }

                    }
                }).success {
                    var data = GsonUtil.getInstance().fromJson<HttpResultList<UserInfo>>(it,
                            object : TypeToken<HttpResultList<UserInfo>>() {}.type)
                    if (data.isSuccess) {
                        if (page == 1) {
                            datas.clear()
                        } else if (data.data.size < 40) {
                            smrw.loadMoreFinish(true, false)
                        } else {
                            smrw.loadMoreFinish(false, true)
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


}
