package com.qdhc.ny.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ImageAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.Media
import com.qdhc.ny.entity.User
import com.sj.core.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_contradiction_info.*
import kotlinx.android.synthetic.main.layout_title_theme.*

class ContradictionInfoActivity : BaseActivity() {


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

        }

        bt_comment.setOnClickListener {
            var comment = commentEt.text.trim().toString()
            if (comment.isEmpty()) {
                ToastUtil.show(this, "批示内容不能为空")
                commentEt.requestFocus()
                return@setOnClickListener
            }

            showDialog("正在上报批示...")

        }
    }

    override fun initData() {
        userInfo = ProjectData.getInstance().userInfo

    }


    fun getImags() {

    }
}
