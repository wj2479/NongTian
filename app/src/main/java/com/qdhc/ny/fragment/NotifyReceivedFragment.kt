package com.qdhc.ny.fragment

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.activity.NotifyDetailActivity
import com.qdhc.ny.adapter.NotifyReceivedAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.entity.NotifyReceiver
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.sj.core.net.Rx.RxRestClient
import com.vondear.rxui.view.dialog.RxDialogSureCancel
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sign_in_sear.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

/**
 * 接收到的通知
 */
class NotifyReceivedFragment : BaseFragment() {

    lateinit var userInfo: User

    var isShow = false

    override fun intiLayout(): Int {
        return R.layout.fragment_notify_received
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

    var notifyReceivers = ArrayList<NotifyReceiver>()
    lateinit var mAdapter: NotifyReceivedAdapter

    private fun initRefresh() {
        smrw!!.layoutManager = LinearLayoutManager(activity)
        smrw!!.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(activity!!, R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            if (notifyReceivers.size <= position) {
                return@setSwipeItemClickListener
            }
            var notifyReceiver = notifyReceivers[position]
            var intent = Intent(context, NotifyDetailActivity::class.java)
            intent.putExtra("notifyReceiver", notifyReceiver)
            startActivity(intent)
        }

        mAdapter = NotifyReceivedAdapter(activity, notifyReceivers)
        smrw.adapter = mAdapter
        val emptyView = layoutInflater.inflate(R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(R.id.tv_empty).text = "没有新通知"
        //添加空视图
        mAdapter.emptyView = emptyView
    }

    override fun onResume() {
        super.onResume()
        getReceivedNotify(userInfo.id)
    }

    /**
     *  获取通知
     */
    fun getReceivedNotify(uid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["uid"] = uid
        RxRestClient.create()
                .url("notify/getReceivedNotify")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            Log.e("TAG", "通知接收成功:" + result)
                            if (json.getInt("code") == 1000) {
                                var jArray = json.getJSONArray("result")
                                Log.e("TAG", "请求接收成功:" + jArray.length().toString())
                                notifyReceivers.clear()
                                for (index in 0 until jArray.length()) {
                                    var jobj = jArray.getJSONObject(index)
                                    var data = gson.fromJson(jobj.toString(), NotifyReceiver::class.java)
                                    notifyReceivers.add(data)

                                    // 显示新通知
                                    if (!isShow && !data.isRead) {
                                        initDialog(data)
                                        isShow = true
                                    }
                                }
                                mAdapter.notifyDataSetChanged()
                            } else {
                                Log.e("TAG", "请求接收失败:" + result)
                            }

                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }

    /**
     * 初始化通知的对话框
     */
    private fun initDialog(notifyReceiver: NotifyReceiver) {
        var sb = StringBuffer()
        sb.append(notifyReceiver.content.replace("\n", "<br>"))
//        sb.append("<br>")
//
//        sb.append("<font color=\"#808080\">")
//        sb.append("发布于: ")
//        sb.append(notify.createdAt.substring(0, 10))
//        sb.append("</font>")

        var rxDialogSureCancel = RxDialogSureCancel(context)
        rxDialogSureCancel.contentView.text = Html.fromHtml(sb.toString())
        rxDialogSureCancel.contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f)
        rxDialogSureCancel.contentView.gravity = Gravity.LEFT
        rxDialogSureCancel.titleView.text = "您有新的通知"
        rxDialogSureCancel.titleView.textSize = 16.0f
        rxDialogSureCancel.setSure("知道了")
        rxDialogSureCancel.sureView.setOnClickListener {
            rxDialogSureCancel.cancel()
        }
        rxDialogSureCancel.setCancel("查看详情")
        rxDialogSureCancel.cancelView.setOnClickListener {
            rxDialogSureCancel.cancel()
            var intent = Intent(context, NotifyDetailActivity::class.java)
            intent.putExtra("notifyReceiver", notifyReceiver)
            startActivity(intent)
        }
        rxDialogSureCancel.show()
    }

}