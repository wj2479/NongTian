package com.qdhc.ny.activity

import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.fragment.SignInFragment
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 签到页面
 * @author shenjian
 * @date 2019/3/22
 */
class SignInActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return R.layout.activity_sign_in
    }

    override fun initView() {

        //viewpager加载adapter
        supportFragmentManager.beginTransaction().add(R.id.contentLayout, SignInFragment()).commitAllowingStateLoss()
        title_tv_title.text = "拍照上传"
    }


    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
    }

    override fun initData() {

    }

}
