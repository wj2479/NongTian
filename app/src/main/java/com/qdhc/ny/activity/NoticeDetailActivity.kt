package com.qdhc.ny.activity

import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bmob.Notice
import kotlinx.android.synthetic.main.activity_notice_detail.*
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 公告详情
 * @author shenjian
 * @date 2019/3/24
 */
class NoticeDetailActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return (R.layout.activity_notice_detail)
    }

    override fun initView() {
        title_tv_title.text = "公告详情"
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        var notice = intent.getSerializableExtra("notice") as Notice

        tv_title.text = notice.title
        tv_time.text = notice.createdAt.substring(0,10)
        tv_content.text = notice.content
    }

}
