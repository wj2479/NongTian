package com.qdhc.ny.activity

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.entity.User
import com.sj.core.net.Rx.RxRestClient
import com.sj.core.utils.ToastUtil
import com.vondear.rxtool.RxDataTool
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_update_phone.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import org.json.JSONObject
import java.util.*

class UpdatePhoneActivity : BaseActivity() {

    lateinit var user: User

    override fun intiLayout(): Int {
        return (R.layout.activity_update_phone)
    }

    var old_phone = ""
    var phone = ""
    override fun initClick() {
        title_iv_back.setOnClickListener({ finish() })
        title_tv_right.setOnClickListener({
            phone = edt_name.text.toString()
            if (phone.length == 11) {
                upPhone(phone)
            } else {
                ToastUtil.show(mContext, "手机号有误，请重新输入")
            }
        })
    }

    override fun initData() {

    }

    override fun initView() {
        user = intent.getSerializableExtra("user") as User
        old_phone = user.phone
        tv_phone.text = "当前手机号：" + RxDataTool.hideMobilePhone4(old_phone)
        title_tv_title.text = "更改手机号"
        title_tv_right.text = "确定"
        title_tv_right.visibility = View.VISIBLE
        title_tv_right.setTextColor(ContextCompat.getColor(mContext, R.color.themecolor))
    }

    /**
     *更新手机号码
     */
    fun upPhone(phone: String) {
        showDialog("正在提交更改...")
        var params: HashMap<String, Any> = HashMap()
        params["uid"] = user.id
        params["phone"] = phone

        RxRestClient.create()
                .url("user/updateInfo")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.e("User", result)
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                showDialog("更改成功...")
                            }

                            var intent = Intent()
                            intent.putExtra("phone", phone)
                            setResult(Activity.RESULT_OK, intent)

                            mHandler.postDelayed({
                                dismissDialogNow()
                                finish()
                            }, 1500)
                        },
                        { throwable ->
                            throwable.printStackTrace()
                            showDialog("更改失败...")
                            dismissDialog()
                        })
    }

}
