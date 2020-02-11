package com.qdhc.ny.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.TextView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.qdhc.ny.R
import com.qdhc.ny.adapter.SignAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bmob.Sign
import com.qdhc.ny.entity.User
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_sign_in_sear.*
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 签到记录
 */
class SignInListActivity : BaseActivity() {

    lateinit var userInfo: User

    override fun intiLayout(): Int {
        return R.layout.activity_sign_in_list
    }

    override fun initView() {
        initRefresh()
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        userInfo = intent.getSerializableExtra("userInfo") as User
        title_tv_title.text = userInfo.nickName + "的签到记录"
        getData(userInfo.id.toString())
    }

    var datas = ArrayList<Sign>()
    lateinit var mAdapter: SignAdapter

    private fun initRefresh() {
        smrw!!.layoutManager = LinearLayoutManager(this)
        smrw!!.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(this, com.qdhc.ny.R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            if (datas.size == 0) {
                return@setSwipeItemClickListener
            }
            var sign = datas.get(position)
            var intent = Intent(this@SignInListActivity, SignInDetailActivity::class.java)
            intent.putExtra("sign", sign)
            startActivity(intent)
        }

        mAdapter = SignAdapter(this, datas)
        smrw.adapter = mAdapter
        val emptyView = layoutInflater.inflate(com.qdhc.ny.R.layout.common_empty, null)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.findViewById<TextView>(com.qdhc.ny.R.id.tv_empty).text = "暂无记录"
        //添加空视图
        mAdapter.emptyView = emptyView
    }

    fun getData(uid: String) {
        var bmobQuery = BmobQuery<Sign>();
        bmobQuery.addWhereEqualTo("uid", uid)
//        bmobQuery.order("locationTime")
        bmobQuery.order("-createdAt")

        bmobQuery.findObjects(object : FindListener<Sign>() {
            override fun done(list: MutableList<Sign>?, e: BmobException?) {
                if (e == null) {
                    datas.clear()
                    datas.addAll(list!!)
                    mAdapter.notifyDataSetChanged()
                }
            }
        })
    }

}
