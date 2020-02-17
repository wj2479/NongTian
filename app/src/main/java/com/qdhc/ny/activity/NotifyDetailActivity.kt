package com.qdhc.ny.activity

import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bmob.NotifyReceiver
import com.qdhc.ny.entity.Notify
import kotlinx.android.synthetic.main.activity_notice_detail.*
import kotlinx.android.synthetic.main.layout_title_theme.*

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

    var MAX_COUNT = 0
    var count = 0

    var sb = StringBuffer()

    override fun initData() {
        var notify = intent.getSerializableExtra("notify") as Notify
        tv_title.text = notify.content
        tv_time.text = "发布于:" + notify.createTime
        if (intent.hasExtra("notifyReceiver")) {
            var notifyReceiver = intent.getSerializableExtra("notifyReceiver") as NotifyReceiver
            if (!notifyReceiver.isRead) {
                notifyReceiver.isRead = true
                notifyReceiver.update(object : UpdateListener() {
                    override fun done(p0: BmobException?) {
                    }
                })
            }

        } else {

        }
    }

}
