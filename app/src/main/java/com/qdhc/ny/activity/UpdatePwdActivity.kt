package com.qdhc.ny.activity

import android.os.Handler
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.qdhc.ny.base.BaseActivity
import com.sj.core.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_update_pwd.*
import kotlinx.android.synthetic.main.layout_title_theme.*

class UpdatePwdActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return (com.qdhc.ny.R.layout.activity_update_pwd)
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }

        bt_comment.setOnClickListener {
            var oldPwd = edt_oldPwd.text.toString().trim()
            var newPwd = edt_pwd.text.toString().trim()
            var newPwd2 = edt_pwd_confirm.text.toString().trim()

            if (oldPwd.length < 6) {
                ToastUtil.show(this@UpdatePwdActivity, "旧密码长度不合法")
                edt_oldPwd.requestFocus()
                return@setOnClickListener
            }

            if (newPwd.length < 6) {
                ToastUtil.show(this@UpdatePwdActivity, "新密码长度不合法")
                edt_pwd.requestFocus()
                return@setOnClickListener
            }

            if (!newPwd2.equals(newPwd)) {
                ToastUtil.show(this@UpdatePwdActivity, "两次密码输入不同")
                edt_pwd.requestFocus()
                return@setOnClickListener
            }

            showDialog("正在修改密码...")
            //
            BmobUser.updateCurrentUserPassword(oldPwd, newPwd, object : UpdateListener() {
                override fun done(e: BmobException?) {
                    if (e == null) {
                        showDialog("修改密码成功..")
                        Handler().postDelayed({
                            dismissDialogNow()
                            finish()
                        }, 1500)
                    } else {
                        showDialog("修改密码失败...")
                        dismissDialog()
                    }
                }
            })

        }
    }

    override fun initData() {

    }

    override fun initView() {
        title_tv_title.text = "修改密码"
    }
}
