package com.qdhc.ny.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ProjectAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.bmob.Project
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_sign_in_sear.*


/**
 * 考核
 */
class CheckFragment : BaseFragment() {

    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): CheckFragment {
            val fragment = CheckFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun intiLayout(): Int {
        return R.layout.fragment_check
    }

    override fun initView() {
    }

    override fun initClick() {
    }

    override fun initData() {
        initRefresh()
    }

    override fun lazyLoad() {
    }

    var datas = ArrayList<Project>()
    lateinit var mAdapter: ProjectAdapter
    private fun initRefresh() {
        smrw!!.layoutManager = LinearLayoutManager(activity)
        smrw!!.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(activity!!, com.qdhc.ny.R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            if (datas.size == 0) {
                return@setSwipeItemClickListener
            }

        }

        mAdapter = ProjectAdapter(activity, datas)
        smrw.adapter = mAdapter
        val emptyView = layoutInflater.inflate(com.qdhc.ny.R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(com.qdhc.ny.R.id.tv_empty).text = "暂无考核数据"
        //添加空视图
        mAdapter.emptyView = emptyView
    }

}
