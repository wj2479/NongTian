package com.qdhc.ny.activity

import android.content.Intent
import android.view.View
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.User
import com.qdhc.ny.fragment.NotifyFragment
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 通知列表
 * @author shenjian
 * @date 2019/3/24
 */
class NotifyActivity : BaseActivity() {

    lateinit var user: User

    override fun intiLayout(): Int {
        return R.layout.activity_notify
    }

    override fun initView() {
        supportFragmentManager.beginTransaction().add(R.id.contentLayout, NotifyFragment()).commitAllowingStateLoss()
        title_tv_title.text = "通  知"

        title_tv_right.text = "发布通知"
        title_tv_right.visibility = View.VISIBLE
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
        title_tv_right.setOnClickListener {
            //发布通知
            startActivity(Intent(this, NotifyPublishActivity::class.java).putExtra("user", user))

        }
    }

    override fun initData() {
        user = ProjectData.getInstance().userInfo
    }

}
