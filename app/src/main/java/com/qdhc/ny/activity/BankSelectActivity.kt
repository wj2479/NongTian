package com.qdhc.ny.activity

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.reflect.TypeToken
import com.qdhc.ny.R
import com.qdhc.ny.adapter.BankAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.BankInfo
import com.qdhc.ny.bean.HttpResult
import com.sj.core.net.RestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.GsonUtil
import com.sj.core.utils.NetWorkUtil
import com.sj.core.utils.SharedPreferencesUtil
import com.sj.core.utils.ToastUtil
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_bank_select.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import java.util.HashMap

/**
 * 选择银行
 * @author shenjian
 * @date 2019/4/16
 */
class BankSelectActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return (R.layout.activity_bank_select)
    }

    var datas = ArrayList<String>()
    override fun initView() {
        title_tv_title.text = "请选择银行"
        smrw.layoutManager = LinearLayoutManager(mContext)
        smrw.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(mContext, R.color.backgroundColor)))
        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            setResult(Activity.RESULT_OK, Intent().putExtra("name", datas[position]))
            finish()
        }
        smrw.adapter = BankAdapter(mContext, datas)
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        getData(intent.getBooleanExtra("isZhihang", false))
    }

    fun getData(isZhihang: Boolean) {
        var url = "api/BankData/GetZhongzhi"
        if (isZhihang) {
            url = "api/BankData/GetZhihang"
        }
        var params: java.util.HashMap<String, Any> = HashMap()
        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(mContext, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(mContext, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(mContext, "signature")
        RestClient.create()
                .params(params)
                .headers(headers)
                .url(url)
                .request(object : IRequest {
                    override fun onRequestStart() {
                        Log.e("request", "onRequestStart")
                    }

                    override fun onRequestEnd() {
                        Log.e("request", "onRequestEnd")
                    }
                }).success {
                    var data = GsonUtil.getInstance().fromJson<HttpResult<BankInfo>>(it,
                            object : TypeToken<HttpResult<BankInfo>>() {}.type)
                    if (data.isSuccess) {
                        if (isZhihang) {
                            data.data.zhihang.forEach {
                                datas.add(it)
                            }
                        } else {
                            data.data.fenhang.forEach {
                                datas.add(it)
                            }
                        }
                        smrw.adapter.notifyDataSetChanged()
                    }
                }.error { code, msg ->
                    ToastUtil.show(mContext, "请求错误code:$code$msg")
                }.failure {
                    if (NetWorkUtil.isNetworkConnected(mContext)) {
                        ToastUtil.show(mContext, resources.getString(R.string.net_error))
                    } else {
                        ToastUtil.show(mContext, resources.getString(R.string.net_no_worker))
                    }

                }
                .build()
                .post()
    }

}
