package com.qdhc.ny.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.adapter.NoticeAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bmob.Notice
import com.qdhc.ny.common.ProjectData
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 公告列表
 * @author shenjian
 * @date 2019/3/24
 */
class NoticeActivity : BaseActivity() {

    var datas = ArrayList<Notice>()
    override fun intiLayout(): Int {
        return R.layout.activity_notice
    }

    override fun initView() {
        title_tv_title.text = "公告"
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
    }

    lateinit var mAdapter: NoticeAdapter
    private fun initRefresh() {
        main_srl.setOnRefreshListener {
            //刷新服务
        }
        smrw!!.layoutManager = LinearLayoutManager(mContext)
        smrw.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(mContext, R.color.backgroundColor)))
        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            startActivity(Intent(mContext, NoticeDetailActivity::class.java).putExtra("notice", datas[position]))
            finish()
        }

        // 使用默认的加载更多的View
        smrw.useDefaultLoadMore()
        // 加载更多的监听
        smrw.setLoadMoreListener {
        }
        mAdapter = NoticeAdapter(this, datas)
        smrw.adapter = mAdapter

        val emptyView = layoutInflater.inflate(com.qdhc.ny.R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(com.qdhc.ny.R.id.tv_empty).text = "暂无公告"
        //添加空视图
        mAdapter.emptyView = emptyView
    }

    override fun initData() {
        datas.addAll(ProjectData.getInstance().notices)
        initRefresh()
    }

}
