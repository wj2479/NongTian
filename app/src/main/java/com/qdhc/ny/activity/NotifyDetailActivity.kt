package com.qdhc.ny.activity

import android.util.Log
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.Notify
import com.qdhc.ny.entity.NotifyReceiver
import com.sj.core.net.Rx.RxRestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_notice_detail.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import org.json.JSONObject
import java.util.*

/**
 * 通知详情
 * @author shenjian
 * @date 2019/3/24
 */
class NotifyDetailActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return (R.layout.activity_notify_detail)
    }

    override fun initView() {
        title_tv_title.text = "通知详情"
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
    }

    var sb = StringBuffer()

    override fun initData() {
        if (intent.hasExtra("notify")) {
            var notify = intent.getSerializableExtra("notify") as Notify
            tv_title.text = notify.content
            tv_time.text = "发布于:" + notify.createTime
        } else
            if (intent.hasExtra("notifyReceiver")) {
                var notifyReceiver = intent.getSerializableExtra("notifyReceiver") as NotifyReceiver
                tv_title.text = notifyReceiver.content
                tv_time.text = "发布于:" + notifyReceiver.createTime
                if (!notifyReceiver.isRead) {
                    setNotifyRead(ProjectData.getInstance().userInfo.id, notifyReceiver.id)
                }
            }
    }

    /**
     *  获取通知
     */
    fun setNotifyRead(uid: Int, nid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["uid"] = uid
        params["nid"] = nid
        RxRestClient.create()
                .url("notify/setNotifyRead")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                Log.e("TAG", "设置已读成功:" + result)
                            } else {
                                Log.e("TAG", "设置已读失败:" + result)
                            }

                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }

}
