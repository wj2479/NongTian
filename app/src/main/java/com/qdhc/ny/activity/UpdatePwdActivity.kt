package com.qdhc.ny.activity

import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.User
import com.sj.core.net.Rx.RxRestClient
import com.sj.core.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_update_pwd.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import org.json.JSONObject
import java.util.*

class UpdatePwdActivity : BaseActivity() {

    lateinit var user: User

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

            var params: HashMap<String, Any> = HashMap()
            params["username"] = user.userName
            params["oldPwd"] = oldPwd
            params["newPwd"] = newPwd

            RxRestClient.create()
                    .url("user/changePassword")
                    .params(params)
                    .build()
                    .get()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result ->
                                var json = JSONObject(result)
                                if (json.getInt("code") == 1000) {
                                    showDialog("密码修改成功...")
                                }
                                dismissDialogAndFinish()
                            },
                            { throwable ->
                                throwable.printStackTrace()
                                showDialog("密码修改失败，请稍候再试...")
                                dismissDialog()
                            })
        }
    }

    override fun initData() {
        user = ProjectData.getInstance().userInfo
    }

    override fun initView() {
        title_tv_title.text = "修改密码"
    }
}
