package com.qdhc.ny.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.adapter.MessageAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.bean.MessageInfo
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_sign_in_sear.*


class MessageFragment : BaseFragment() {

    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): MessageFragment {
            val fragment = MessageFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun intiLayout(): Int {
        return R.layout.fragment_message
    }

    override fun initView() {
        initRefresh()
    }

    override fun initClick() {
    }

    override fun initData() {

    }

    override fun lazyLoad() {
    }

    var page = 1
    var datas = ArrayList<MessageInfo>()
    lateinit var mAdapter: MessageAdapter
    private fun initRefresh() {
        main_srl.setOnRefreshListener {
            //刷新服务
            page = 1
            getData()
        }
        smrw!!.layoutManager = LinearLayoutManager(activity)
        smrw!!.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(activity!!, R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            if (datas.size == 0) {
                return@setSwipeItemClickListener
            }


        }

        // 使用默认的加载更多的View
        smrw.useDefaultLoadMore()
        // 加载更多的监听
        smrw.setLoadMoreListener {
            page++
            getData()
        }

        mAdapter = MessageAdapter(activity, datas)
        smrw.adapter = mAdapter
        val emptyView = layoutInflater.inflate(R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(R.id.tv_empty).text = "没有通知消息"
        //添加空视图
        mAdapter.emptyView = emptyView
    }


    //获取数据
    fun getData() {}
}
