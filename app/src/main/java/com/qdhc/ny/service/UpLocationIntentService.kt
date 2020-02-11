package com.qdhc.ny.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.sj.core.net.RestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.SharedPreferencesUtil
import org.json.JSONObject

class UpLocationIntentService : IntentService("UpLocationIntentService") {
    companion object {
        val UPLOAD_ACTION = "UPLOAD_ACTION"
    }

    val gson = Gson()

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (UPLOAD_ACTION.equals(action)) {
                var set = SharedPreferencesUtil.getStringSet(this, "uplocation");
                var iterator = set.iterator();
                while (iterator.hasNext()) {
                    var info = iterator.next();
                    Log.e("UpLocationIntentService", "保存的数据：" + info)
                    upLocation(info)
                }
            }
        }
    }

    fun upLocation(info: String) {

        var json = JSONObject(info);
        var params = java.util.HashMap<String, Any>()
        params.put("location", json.get("location"))
        params.put("locatetime", json.get("locatetime"))
        params.put("speed", json.get("speed"))
        params.put("direction", json.get("direction"))
        params.put("height", json.get("height"))
        params.put("accuracy", json.get("accuracy"))
        params.put("address", json.get("address"))
        params.put("remark", json.get("remark"))

        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(this, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(this, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(this, "signature")
        RestClient.create()
                .params(params)
                .headers(headers)
                .url("api/Position/Upload")
                .request(object : IRequest {
                    override fun onRequestStart() {

                    }

                    override fun onRequestEnd() {

                    }
                }).success {
                    Log.e("main_locat", "结果:" + it)
                    SharedPreferencesUtil.removeStringSet(this, "uplocation", info);
                }.failure {

                }.error { code, msg ->

                }
                .build()
                .post()
    }

}
