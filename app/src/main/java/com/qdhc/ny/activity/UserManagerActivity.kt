package com.qdhc.ny.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ContactsAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.entity.User
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_sign_in_sear.*
import kotlinx.android.synthetic.main.layout_title_theme.*

class UserManagerActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return R.layout.activity_user_manager
    }

    var datas = ArrayList<User>()
    lateinit var mAdapter: ContactsAdapter
    override fun initView() {

        title_tv_title.text = "用户列表"
        title_tv_right.text = "添加"
        title_tv_right.visibility = View.VISIBLE
        title_tv_right.setTextColor(ContextCompat.getColor(mContext, R.color.themecolor))

        smrw!!.layoutManager = LinearLayoutManager(this)
        smrw!!.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(this, R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            if (datas.size == 0) {
                return@setSwipeItemClickListener
            }
        }

        smrw.setSwipeItemLongClickListener { itemView, position ->
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("提示")
            dialog.setMessage("确定要删除当前用户？")
            dialog.setPositiveButton("删除") { dialog, which ->

            }.setNegativeButton("取消", null)
            dialog.create().show()
        }

        mAdapter = ContactsAdapter(this, datas)
        smrw.adapter = mAdapter
        val emptyView = layoutInflater.inflate(com.qdhc.ny.R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(com.qdhc.ny.R.id.tv_empty).text = "暂无数据"
        //添加空视图
        mAdapter.emptyView = emptyView
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
        title_tv_right.setOnClickListener { startActivity(Intent(this, UserAddActivity::class.java)) }
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    //获取数据
    fun getData() {
    }


}
