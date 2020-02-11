package com.qdhc.ny.activity

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.gson.reflect.TypeToken
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ClientManagerAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.ClientManagerInfo
import com.qdhc.ny.bean.HttpResultList
import com.sj.core.net.RestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.GsonUtil
import com.sj.core.utils.SharedPreferencesUtil
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_client_manager.*
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 我的客户
 * 跟选择客户公用一个界面
 * @author shenjian
 * @date 2019/3/25
 */
class ClientManagerActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return R.layout.activity_client_manager
    }

    //是否是签到页面
    var isSelectManage = false

    override fun initView() {
        isSelectManage = intent.getBooleanExtra("isSignIn", false)
        if (!isSelectManage) {
            title_tv_title.text = "我的客户"
        } else {
            title_tv_title.text = "选择人员"
        }
        initRefresh()


        //搜索
        edt_sear.setOnEditorActionListener { textView, i, keyEvent ->
            page=1
            nameTxt=textView.text.toString()
            getData()
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                com.qdhc.ny.utils.BaseUtil.KeyBoardClose(textView)
            }
            false
        }
    }

    var page = 1
    var datas = ArrayList<ClientManagerInfo>()
    lateinit var mAdapter: ClientManagerAdapter
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
            if (isSelectManage) {
                setResult(Activity.RESULT_OK, Intent().putExtra("user", datas[position]))
                finish()
            }

        }

        // 使用默认的加载更多的View
        smrw.useDefaultLoadMore()
        // 加载更多的监听
        smrw.setLoadMoreListener {
            page++
            getData()

        }
        mAdapter = ClientManagerAdapter(this, datas)
        smrw.adapter = mAdapter

    }
var nameTxt=""
    //获取数据
    fun getData() {
        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(mContext, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(mContext, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(mContext, "signature")
        var params = java.util.HashMap<String, Any>()
        params["page"] = page
        params["pagesize"] = 30
        params["name"] = nameTxt
        RestClient.create()
                .params(params)
                .headers(headers)
                .url("api/User/GetPersonnelList")
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
                    var data = GsonUtil.getInstance().fromJson<HttpResultList<ClientManagerInfo>>(it,
                            object : TypeToken<HttpResultList<ClientManagerInfo>>() {}.type)
                    if (data.isSuccess) {
                        if (page == 1) {
                            datas.clear()
                            smrw.loadMoreFinish(false, true)
//                            pagerCount == page -> // 第一次加载数据：一定要调用这个方法，否则不会触发加载更多。
//                                // 第一个参数：表示此次数据是否为空，假如你请求到的list为空(== null || list.size == 0)，那么这里就要true。
//                                // 第二个参数：表示是否还有更多数据，根据服务器返回给你的page等信息判断是否还有更多，这样可以提供性能，如果不能判断则传true。
//                                //关闭上拉刷新
                        }else{
                            smrw.loadMoreFinish(false, true)
                        }
                        if (data.data.size < 30) {
                            smrw.loadMoreFinish(true, false)
                        }
                        datas.addAll(data.data)
                       mAdapter.notifyDataSetChanged()
                    } else {
                        if (page == 1) {

                            val emptyView = layoutInflater.inflate(R.layout.common_empty, null)
                            emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT)
                            emptyView.findViewById<TextView>(R.id.tv_empty).text = "暂无数据"
                            //添加空视图
                            mAdapter.emptyView = emptyView
                            datas.clear()
                            mAdapter.notifyDataSetChanged()
                        }else{
                            smrw.loadMoreFinish(true, false)
                        }
                    }
                    Log.e("clientManageer",data.data.size.toString())
                }
                .failure {
                    //                    if (NetWorkUtil.isNetworkConnected(mContext)) {
//                        ToastUtil.show(mContext, resources.getString(R.string.net_error))
//                    }

                }.error { code, msg ->
                         Log.e("clientManageer",code.toString()+"___"+msg)
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
