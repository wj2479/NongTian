package com.qdhc.ny.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.qdhc.ny.R
import com.qdhc.ny.activity.SignInDetailActivity
import com.qdhc.ny.adapter.SignListAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.bean.SignListInfo
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.fragment_sign_count.*

/**
 * 签到统计
 * @author shenjian
 * @date 2019/3/22
 */
class SignCountFragment : BaseFragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): SignCountFragment {
            val fragment = SignCountFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun intiLayout(): Int {
        return R.layout.fragment_sign_count
    }

    var datas = ArrayList<SignListInfo.DataListBean>()

    override fun initView() {
        main_srl.setOnRefreshListener {
            page = 1
            //刷新服务
        }
        smrw!!.layoutManager = LinearLayoutManager(activity)
        smrw.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(activity!!, R.color.backgroundColor)))
        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            startActivityForResult(Intent(activity, SignInDetailActivity::class.java)
                    .putExtra("id", datas[position].id), 1)

        }

        // 使用默认的加载更多的View
        smrw.useDefaultLoadMore()
        // 加载更多的监听
        smrw.setLoadMoreListener {
            page++

        }
        // 加载更多的监听

        var mAdapter = SignListAdapter(activity, datas)
        smrw.adapter = mAdapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            }
        }
    }

    override fun initClick() {
    }

    override fun initData() {

    }

    override fun lazyLoad() {

    }

    var page = 1

}
