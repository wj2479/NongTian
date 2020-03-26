package com.qdhc.ny.fragment

import android.app.Activity
import android.content.Intent
import com.qdhc.ny.LoginActivity
import com.qdhc.ny.R
import com.qdhc.ny.activity.*
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.BaseUtil
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.sj.core.utils.AcitityManagerUtil
import com.vondear.rxtool.RxDataTool
import kotlinx.android.synthetic.main.fragment_my.*

class MyFragment : BaseFragment() {
    lateinit var userInfo: User

    override fun intiLayout(): Int {
        return R.layout.fragment_my
    }

    override fun initView() {
        userInfo = ProjectData.getInstance().userInfo
        tv_name.text = userInfo.nickName
        tv_job.text = userInfo.role.desc

        if (userInfo.phone.length == 11) {
            phoneTv.text = RxDataTool.hideMobilePhone4(userInfo.phone)
        }
        versionTv.text = BaseUtil.getAppVersionName(context)
//        if (userInfo.role == 0) {
//            ll_usermanager.visibility = View.VISIBLE
//        }

//        if (userInfo.avatar != null) {
//            ImageLoaderUtil.loadCorners(context, userInfo.avatar.url, iv_photo, -1, R.drawable.ic_defult_user)
//        }

    }

    override fun initClick() {

        btn_exit.setOnClickListener {
            //退出
            logOut()
        }
        ll_user.setOnClickListener {
            //更新个人信息
            startActivityForResult(Intent(activity, UserInfoActivity::class.java)
                    .putExtra("user", userInfo), 100)
        }
        ll_feedback.setOnClickListener {
            //意见反馈
            startActivity(Intent(activity, FeedbackActivity::class.java))
        }
        ll_name_vertify.setOnClickListener {
            //修改密码
            startActivity(Intent(activity, UpdatePwdActivity::class.java))
        }

        ll_notify.setOnClickListener {
            //用户通知
            startActivity(Intent(activity, NotifyActivity::class.java).putExtra("user", userInfo))
        }

        ll_phone.setOnClickListener {
            startActivityForResult(Intent(activity, UpdatePhoneActivity::class.java).putExtra("user", userInfo), 1002)
        }
    }

    override fun initData() {

    }

    override fun lazyLoad() {
    }

    /***
     * 退出
     */
    fun logOut() {
        ProjectData.getInstance().release()
        startActivity(Intent(activity, LoginActivity::class.java))
        AcitityManagerUtil.getInstance().finishAllActivity()
        SharedPreferencesUtils.removeLogin(context)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> initView()
                1002 -> {
                    var phone = data?.getStringExtra("phone")
                    userInfo.phone = phone
                    phoneTv.text = RxDataTool.hideMobilePhone4(userInfo.phone)
                    ProjectData.getInstance().userInfo = userInfo
                }
            }
        }
    }

}
