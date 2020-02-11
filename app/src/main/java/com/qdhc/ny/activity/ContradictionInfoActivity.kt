package com.qdhc.ny.activity

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ImageAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bmob.Contradiction
import com.qdhc.ny.bmob.UserInfo
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.Media
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.UserInfoUtils
import com.qdhc.ny.utils.UserInfoUtils.getInfoByObjectId
import com.sj.core.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_contradiction_info.*
import kotlinx.android.synthetic.main.layout_title_theme.*

class ContradictionInfoActivity : BaseActivity() {

    lateinit var contradiction: Contradiction

    lateinit var adapter: ImageAdapter

    lateinit var userInfo: User

    var selectList = ArrayList<Media>()

    override fun intiLayout(): Int {
        return R.layout.activity_contradiction_info
    }

    override fun initView() {
        title_tv_title.text = "信息详情"

        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = GridLayoutManager(this, 4) as RecyclerView.LayoutManager?
        adapter = ImageAdapter(this, selectList)
        adapter.setOnItemClickListener { position, v ->
            var url = selectList.get(position).url

            var intent = Intent(this@ContradictionInfoActivity, ImageActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }
        rlv.adapter = adapter
    }

    override fun initClick() {

        title_iv_back.setOnClickListener { finish() }

        bt_submit.setOnClickListener {
            var result = resultEt.text.trim().toString()
            if (result.isEmpty()) {
                ToastUtil.show(this, "处理结果不能为空")
                resultEt.requestFocus()
                return@setOnClickListener
            }

            showDialog("正在上报结果...")

            contradiction.result = result
            contradiction.status = "已处理"

            contradiction.update(contradiction.objectId, object : UpdateListener() {
                override fun done(e: BmobException?) {
                    if (e == null) {
                        showDialog("上报成功..")
                        android.os.Handler().postDelayed({
                            dismissDialogNow()
                            finish()
                        }, 1500)
                    } else {
                        showDialog("上报失败，请稍候再试")
                        Log.e("TAG", "上报出错->" + e.toString())
                    }
                }
            })
        }

        bt_comment.setOnClickListener {
            var comment = commentEt.text.trim().toString()
            if (comment.isEmpty()) {
                ToastUtil.show(this, "批示内容不能为空")
                commentEt.requestFocus()
                return@setOnClickListener
            }

            showDialog("正在上报批示...")

            contradiction.comment = comment

            contradiction.update(contradiction.objectId, object : UpdateListener() {
                override fun done(e: BmobException?) {
                    if (e == null) {
                        showDialog("批示成功..")
                        android.os.Handler().postDelayed({
                            dismissDialogNow()
                            finish()
                        }, 1500)
                    } else {
                        showDialog("批示失败，请稍候再试")
                        Log.e("TAG", "上报出错->" + e.toString())
                    }
                }
            })
        }
    }

    override fun initData() {
        userInfo = ProjectData.getInstance().userInfo

        contradiction = intent.getSerializableExtra("info") as Contradiction

        projectTv.text = contradiction.from
        villageTv.text = "上报位置: " + contradiction.district + contradiction.village
        levelTv.text = "程度: " + contradiction.level
        fromTv.text = "来源: " + contradiction.from
        typesTv.text = "上报类别: " + contradiction.type
        numberTv.text = "人数: " + contradiction.numbers

        descriptionTv.text = contradiction.description

        statusTv.text = contradiction.status
        if ("已处理".equals(contradiction.status)) {
            statusTv.setTextColor(Color.parseColor("#4698D1"))
            resultTv.visibility = View.VISIBLE
            resultTv.text = contradiction.result

            resultManLayout.visibility = View.VISIBLE

            getInfoByObjectId(contradiction.resultId, object : UserInfoUtils.IResult {
                override fun onReslt(userInfo: UserInfo?) {
                    resultManTv.text = if (userInfo == null) "未知" else userInfo.nickName
                }
            })

        } else {
            statusTv.setTextColor(Color.RED)
            statusTv.text = "未处理"
            // 可以处理
            if (userInfo.role == 2 || userInfo.role == 0) {
                resultEt.visibility = View.VISIBLE
                buttonLayout.visibility = View.VISIBLE
            } else if (userInfo.role == 3) {
                resultLayout.visibility = View.GONE
                buttonLayout.visibility = View.GONE
            } else {
                resultLayout.visibility = View.GONE
            }
        }

        if (TextUtils.isEmpty(contradiction.comment)) {
            // 可以处理
            if (userInfo.role == 3 || userInfo.role == 0) {
                commentEt.visibility = View.VISIBLE
                commentButLayout.visibility = View.VISIBLE
            } else {
                commentTv.visibility = View.VISIBLE
                commentTv.text = "暂无"
            }
        } else {
            commentTv.visibility = View.VISIBLE
            commentTv.text = contradiction.comment
        }

        if (userInfo.role != 1) {
            uploadLayout.visibility = View.VISIBLE

            getInfoByObjectId(contradiction.uploader, object : UserInfoUtils.IResult {
                override fun onReslt(userInfo: UserInfo?) {
                    uploaderTv.text = if (userInfo == null) "上报人: 未知" else "上报人: " + userInfo.nickName
                    uploadphoneTv.text = if (userInfo == null) "" else "电话: " + userInfo.mobilePhoneNumber
                }
            })
        }

        getImags()
    }


    fun getImags() {

    }
}
