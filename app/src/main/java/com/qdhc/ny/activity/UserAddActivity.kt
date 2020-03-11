package com.qdhc.ny.activity

import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.sj.core.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_user_add.*
import kotlinx.android.synthetic.main.layout_title_theme.*

class UserAddActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return R.layout.activity_user_add
    }

    override fun initView() {
        title_tv_title.text = "添加新监理"
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
        bt_submit.setOnClickListener {
            var userName = usernameEt.text.trim().toString()
            if (userName.isEmpty()) {
                ToastUtil.show(this, "用户名不能为空")
                usernameEt.requestFocus()
                return@setOnClickListener
            }

            var password = pwdEt.text.trim().toString()
            if (password.length < 6) {
                ToastUtil.show(this, "密码长度不合法")
                pwdEt.requestFocus()
                return@setOnClickListener
            }
            var nickName = nickNameEt.text.trim().toString()
            if (nickName.isEmpty()) {
                ToastUtil.show(this, "昵称不能为空")
                nickNameEt.requestFocus()
                return@setOnClickListener
            }

            var phone = phoneEt.text.trim().toString()

            if (!phone.startsWith("1")) {
                ToastUtil.show(this, "手机号码不合法")
                phoneEt.requestFocus()
                return@setOnClickListener
            }
            if (phone.length != 11) {
                ToastUtil.show(this, "手机号码长度不合法")
                phoneEt.requestFocus()
                return@setOnClickListener
            }


        }
    }

    override fun initData() {
    }

}
