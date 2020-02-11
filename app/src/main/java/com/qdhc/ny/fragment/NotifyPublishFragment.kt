package com.qdhc.ny.fragment


import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.qdhc.ny.R
import com.qdhc.ny.activity.NotifyDetailActivity
import com.qdhc.ny.adapter.NotifyAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.bmob.Notify
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_sign_in_sear.*
import android.support.v7.widget.LinearLayoutManager as LinearLayoutManager1


/**
 * A simple [Fragment] subclass.
 */
class NotifyPublishFragment : BaseFragment() {

    lateinit var userInfo: User

    override fun intiLayout(): Int {
        return R.layout.fragment_notify_publish
    }

    override fun initView() {
        initRefresh()
    }

    override fun initClick() {
    }

    override fun initData() {
        userInfo = SharedPreferencesUtils.loadLogin(context)
    }

    override fun lazyLoad() {
    }

    var datas = ArrayList<Notify>()
    lateinit var mAdapter: NotifyAdapter

    private fun initRefresh() {
        smrw!!.layoutManager = LinearLayoutManager1(activity)
        smrw!!.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(activity!!, R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            var notify = datas[position]
            var intent = Intent(context, NotifyDetailActivity::class.java)
            intent.putExtra("notify", notify)
            startActivity(intent)
        }

        mAdapter = NotifyAdapter(activity, datas)
        smrw.adapter = mAdapter
        val emptyView = layoutInflater.inflate(R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(R.id.tv_empty).text = "没有发出的通知"
        //添加空视图
        mAdapter.emptyView = emptyView
    }

    override fun onResume() {
        super.onResume()
        datas.clear()
        getData()
    }

    /**
     *  获取通知
     */
    fun getData() {
        val categoryBmobQuery = BmobQuery<Notify>()
        categoryBmobQuery.addWhereEqualTo("publish", userInfo.id)
        categoryBmobQuery.order("-createdAt")
        categoryBmobQuery.findObjects(
                object : FindListener<Notify>() {
                    override fun done(list: List<Notify>?, e: BmobException?) {
                        if (e == null) {
                            Log.e("通知列表-----》", list?.toString())
                            datas.addAll(list!!)
                            mAdapter.notifyDataSetChanged()
                        } else {
                            Log.e("异常-----》", e.toString())
                        }
                    }
                })
    }

}
