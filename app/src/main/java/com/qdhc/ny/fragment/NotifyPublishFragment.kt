package com.qdhc.ny.fragment


import android.content.Intent
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.activity.NotifyDetailActivity
import com.qdhc.ny.adapter.NotifyAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.entity.Notify
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.sj.core.net.Rx.RxRestClient
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sign_in_sear.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set
import android.support.v7.widget.LinearLayoutManager as LinearLayoutManager1


/**
 * 发布出去的通知
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
        getPublishNotify(userInfo.id)
    }

    /**
     *  获取通知
     */
    fun getPublishNotify(uid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["uid"] = uid
        RxRestClient.create()
                .url("notify/getPublishNotify")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                var result = json.getJSONArray("result")
                                datas.clear()
                                for (index in 0 until result.length()) {
                                    var jobj = result.getJSONObject(index)
                                    var data = gson.fromJson(jobj.toString(), Notify::class.java)
                                    datas.add(data)
                                }
                                mAdapter.notifyDataSetChanged()
                            } else {
                                Log.e("TAG", "请求发布失败:" + result)
                            }

                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }

}
