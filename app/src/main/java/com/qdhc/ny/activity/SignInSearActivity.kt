package com.qdhc.ny.activity

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.adapter.SignListAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.ScreenInfo
import com.qdhc.ny.bean.SignListInfo
import com.qdhc.ny.dialog.RxDialogWheelYearMonthDay
import com.qdhc.ny.popu.PopSignInView
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.sj.core.utils.SharedPreferencesUtil
import com.sj.core.utils.ToastUtil
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_sign_in_sear.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import java.text.SimpleDateFormat


/**
 * 日志搜素功能
 * @author shenjian
 * @date 2019/3/31
 */
class SignInSearActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return R.layout.activity_sign_in_sear
    }

    var datas = ArrayList<SignListInfo.DataListBean>()
    lateinit var mAdapter: SignListAdapter
    override fun initView() {
        title_tv_title.text = "日报"
        main_srl.setOnRefreshListener {
        }
        smrw!!.layoutManager = LinearLayoutManager(mContext)
        smrw.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(mContext!!, R.color.backgroundColor)))
        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            startActivityForResult(Intent(mContext, SignInDetailActivity::class.java)
                    .putExtra("id", datas[position].id), 1)

        }


        // 使用默认的加载更多的View
        smrw.useDefaultLoadMore()
        // 加载更多的监听
        smrw.setLoadMoreListener {
        }

        mAdapter = SignListAdapter(mContext, datas)
        smrw.adapter = mAdapter


        title_tv_right.text = "筛选"
        // title_tv_right.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.ic_screen), null, null, null)
        var it = SharedPreferencesUtil.get(mContext, "userJson")
        var userInfo = SharedPreferencesUtils.loadLogin(mContext)

        initPopVIew()
        initWheelYearMonthDayDialog()
    }

    var isStartTime = true
    lateinit var popupSingleView: PopSignInView
    lateinit var tv_start_time: TextView
    lateinit var tv_end_time: TextView
    lateinit var tv_subbranch: TextView
    private fun initPopVIew() {
        popupSingleView = PopSignInView(mContext, object : PopSignInView.onClickLisiner {
            override fun onClick(mdata: ScreenInfo) {

            }
        })

        tv_start_time = popupSingleView.getView()!!.findViewById<TextView>(R.id.tv_start_time)
        tv_end_time = popupSingleView.getView()!!.findViewById<TextView>(R.id.tv_end_time)
        tv_subbranch = popupSingleView.getView()!!.findViewById<TextView>(R.id.tv_subbranch)
        var tv_reset = popupSingleView.getView()!!.findViewById<TextView>(R.id.tv_reset)
        var tv_ok = popupSingleView.getView()!!.findViewById<TextView>(R.id.tv_ok)
        tv_start_time.setOnClickListener {
            mRxDialogWheelYearMonthDay.show()
            isStartTime = true
        }
        tv_end_time.setOnClickListener {
            mRxDialogWheelYearMonthDay.show()
            isStartTime = false
        }
        tv_subbranch.setOnClickListener {
            startActivityForResult(Intent(mContext, MyClientManageActivity::class.java), 1)
        }
        tv_reset.setOnClickListener {
            tv_start_time.text = "开始时间"
            tv_end_time.text = "结束时间"
            tv_subbranch.text = ""
        }
        tv_ok.setOnClickListener {
            var screenInfo = ScreenInfo()
            if (tv_start_time.text.toString() == "开始时间" && tv_end_time.text.toString() == "结束时间") {
                if (tv_subbranch.getTag(R.id.TAG) == null) {
                    popupSingleView.dismiss()
                    return@setOnClickListener
                }
            } else if (tv_start_time.text.toString() != "开始时间" || tv_end_time.text.toString() != "结束时间") {
                if (tv_start_time.text.toString() == "开始时间") {
                    ToastUtil.show(mContext, "请选择开始时间")
                    return@setOnClickListener
                }
                if (tv_end_time.text.toString() == "结束时间") {
                    ToastUtil.show(mContext, "请选择结束时间")
                    return@setOnClickListener
                }
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                if (sdf.parse(tv_start_time.text.toString()).before(sdf.parse(tv_end_time.text.toString()))) {

                    screenInfo.startTime = tv_start_time.text.toString()
                    screenInfo.endTime = tv_end_time.text.toString()
                } else {
                    ToastUtil.show(mContext, "结束时间不能早于开始时间")
                }
            }
            if (tv_subbranch.getTag(R.id.TAG) == null) {
                screenInfo.userId = ""
            } else {
                screenInfo.userId = tv_subbranch.getTag(R.id.TAG).toString()
            }
            tv_subbranch.setTag(R.id.TAG, null)
            popupSingleView.dismiss()
        }
    }

    lateinit var mRxDialogWheelYearMonthDay: RxDialogWheelYearMonthDay
    private fun initWheelYearMonthDayDialog() {
        // ------------------------------------------------------------------选择日期开始
        mRxDialogWheelYearMonthDay = RxDialogWheelYearMonthDay(this)
        mRxDialogWheelYearMonthDay.sureView.setOnClickListener(
                {
                    var date_txt = ("" +
                            mRxDialogWheelYearMonthDay.selectorYear + "-"
                            + mRxDialogWheelYearMonthDay.selectorMonth + "-"
                            + mRxDialogWheelYearMonthDay.selectorDay)
                    if (isStartTime) {
                        tv_start_time.text = date_txt
                    } else {
                        tv_end_time.text = date_txt
                    }

                    mRxDialogWheelYearMonthDay.cancel()
                })
        mRxDialogWheelYearMonthDay.cancleView.setOnClickListener(
                { mRxDialogWheelYearMonthDay.cancel() })
        // ------------------------------------------------------------------选择日期结束
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data.let {
            if (resultCode == Activity.RESULT_OK && requestCode == 1) {
//                tv_subbranch.text = it!!.getParcelableExtra<UserInfo>("user").nickname
//                tv_subbranch.setTag(R.id.TAG, it!!.getParcelableExtra<UserInfo>("user").userid)
            }
        }
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
        edt_sear.setOnEditorActionListener { textView, i, keyEvent ->

            if (i == EditorInfo.IME_ACTION_SEARCH) {
                com.qdhc.ny.utils.BaseUtil.KeyBoardClose(textView)
            }
            false
        }

        title_tv_right.setOnClickListener {
            popupSingleView.showAsDropDown(ll_parent)
        }
    }

    override fun initData() {
        edt_sear.setText(intent.getStringExtra("searStr"))

    }

}
