package com.qdhc.ny.activity

import android.content.Intent
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.google.gson.reflect.TypeToken
import com.qdhc.ny.LoginActivity
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.HttpResult
import com.qdhc.ny.common.Constant
import com.sj.core.net.RestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.GsonUtil
import com.sj.core.utils.NetWorkUtil
import com.sj.core.utils.SharedPreferencesUtil
import com.sj.core.utils.ToastUtil
import com.vondear.rxtool.RxDataTool
import kotlinx.android.synthetic.main.activity_update_phone.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import org.json.JSONObject
import java.util.HashMap

class UpdatePhoneActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return (R.layout.activity_update_phone)

    }

    var old_phone = ""
    var phone = ""
    override fun initClick() {
        title_iv_back.setOnClickListener({ finish() })
        tv_code.setOnClickListener({
            phone = edt_name.text.toString()
            if (phone.length == 11) {
                sendCode(phone)
            } else {
                ToastUtil.show(mContext, "手机号有误，请重新输入")
            }
        })
        //确定事件
        title_tv_right.setOnClickListener({
            var code = edt_code.text.toString()
            if (code.length == 6) {
                upData( phone, code)
            } else {
                ToastUtil.show(mContext, "验证码输入有误，请重新输入")
            }
        })
    }

    override fun initData() {

    }

    var timer: CountDownTimer? = null
    override fun initView() {
        old_phone = intent.getStringExtra("phone")
        tv_phone.text = "当前手机号：" + RxDataTool.hideMobilePhone4(old_phone)
        title_tv_title.text = "更改电话"
        title_tv_right.text = "确定"
        title_tv_right.visibility = View.VISIBLE
        title_tv_right.setTextColor(ContextCompat.getColor(mContext, R.color.themecolor))
        //发动验证码.
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                edt_name.isEnabled = false
                tv_code.text = "(" + millisUntilFinished / 1000 + "s)" + "重新获取"
                tv_code.isClickable = false
            }

            override fun onFinish() {
                tv_code.isClickable = true
                tv_code.text = "获取验证码"
                edt_name.isEnabled = true
            }
        }
    }

    //获取短信验证码
    fun sendCode(moblie: String) {
        var header = HashMap<String, Any>()
        header["Authorization"] = SharedPreferencesUtil.get(mContext, Constant.KEY_TOKEN)
        var parames = HashMap<String, Any>()
        parames["Phone"] = moblie
        RestClient.create()
                .params(parames)
                .url("api/User/SendCode")
                .request(object : IRequest {
                    override fun onRequestStart() {
                        Log.e("request", "onRequestStart")
                    }

                    override fun onRequestEnd() {
                        Log.e("request", "onRequestEnd")
                    }
                }).success {
                    var data = GsonUtil.getInstance().fromJson<HttpResult<JSONObject>>(it,
                            object : TypeToken<HttpResult<JSONObject>>() {}.type)
                    if (data.isSuccess) {
                        timer!!.start()
                    }
                    ToastUtil.show(mContext, data.message)
                }.failure {
                    if (NetWorkUtil.isNetworkConnected(mContext)) {
                        ToastUtil.show(mContext, resources.getString(R.string.net_error))
                    }
                }.error { code, msg ->
                    ToastUtil.show(mContext, "请求错误code:$code$msg")
                }
                .build()
                .post()
    }
    /***
     *
     *
     */
    fun upData( phone: String, code: String) {
        var params: java.util.HashMap<String, Any> = HashMap()
        params["phone"] = phone
        params["code"] = code
        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(mContext, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(mContext, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(mContext, "signature")
        RestClient.create()
                .params(params)
                .headers(headers)
                .url("/api/User/UserPhoneBind")
                .request(object : IRequest {
                    override fun onRequestStart() {
                        Log.e("request", "onRequestStart")
                    }

                    override fun onRequestEnd() {
                        Log.e("request", "onRequestEnd")
                    }
                }).success {
                    //获取token并保存
                    var data = GsonUtil.getInstance().fromJson<HttpResult<JSONObject>>(it,
                            object : TypeToken<HttpResult<JSONObject>>() {}.type)
                    if (data.isSuccess) {
                         finish()
                        startActivity(Intent(mContext,LoginActivity::class.java))
                    }
                    ToastUtil.show(mContext, data.message)
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
