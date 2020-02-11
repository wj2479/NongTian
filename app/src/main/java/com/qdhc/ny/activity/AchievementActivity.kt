package com.qdhc.ny.activity

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.reflect.TypeToken
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ScrollablePanelAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.ATableTitle
import com.qdhc.ny.bean.AchievementInfo
import com.qdhc.ny.bean.HttpResult
import com.qdhc.ny.bean.ScreenInfo
import com.qdhc.ny.dialog.RxDialogWheelYearMonthDay
import com.qdhc.ny.popu.PopAchievementView
import com.sj.core.net.RestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.GsonUtil
import com.sj.core.utils.NetWorkUtil
import com.sj.core.utils.SharedPreferencesUtil
import com.sj.core.utils.ToastUtil
import com.vondear.rxui.view.dialog.RxDialogLoading
import kotlinx.android.synthetic.main.activity_achievement.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

/**
 * 业绩
 * @author shenjian
 * @date 2019/4/6
 */
class AchievementActivity : BaseActivity() {
    override fun intiLayout(): Int {
        return R.layout.activity_achievement
    }
    lateinit var rxDialogLoading: RxDialogLoading
    override fun initView() {
        title_tv_title.text = "业绩"
        title_tv_right.text = "筛选"
        title_tv_right.visibility = View.VISIBLE
        initWheelYearMonthDayDialog()
        initPopVIew()
        rxDialogLoading = RxDialogLoading(mContext)
        rxDialogLoading.textView.text = "正在获取数据"
    }

    lateinit var scrollablePanelAdapter: ScrollablePanelAdapter
    private fun initTable() {
        scrollablePanelAdapter = ScrollablePanelAdapter()
        scrollable_panel.setPanelAdapter(scrollablePanelAdapter)
        scrollablePanelAdapter.setsubClickListener { v, position ->
            Toast.makeText(
                    mContext, position.toString() + "", Toast.LENGTH_LONG).show()
        }
    }


    var isStartTime = true
    lateinit var popAchievementView: PopAchievementView
    lateinit var tv_start_time: TextView
    lateinit var tv_end_time: TextView
    lateinit var tv_subbranch: TextView //管理员
    lateinit var edt_retain: EditText //揽存号
    lateinit var tv_bank: TextView //中行
    lateinit var tv_bank_zhihang: TextView //支行
    lateinit var edt_client_name: EditText //客户经理名称
    private fun initPopVIew() {
        popAchievementView = PopAchievementView(mContext, object : PopAchievementView.onClickLisiner {
            override fun onClick(mdata: ScreenInfo) {

            }
        })

        tv_start_time = popAchievementView.getView()!!.findViewById(R.id.tv_start_time)
        tv_end_time = popAchievementView.getView()!!.findViewById(R.id.tv_end_time)
        tv_subbranch = popAchievementView.getView()!!.findViewById(R.id.tv_subbranch)
        edt_retain = popAchievementView.getView()!!.findViewById(R.id.edt_retain)
        tv_bank = popAchievementView.getView()!!.findViewById(R.id.tv_bank)
        tv_bank_zhihang = popAchievementView.getView()!!.findViewById(R.id.tv_bank_zhihang)
        edt_client_name = popAchievementView.getView()!!.findViewById(R.id.edt_client_name)


        var tv_reset = popAchievementView.getView()!!.findViewById<TextView>(R.id.tv_reset)
        var tv_ok = popAchievementView.getView()!!.findViewById<TextView>(R.id.tv_ok)
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
        tv_bank.setOnClickListener {
            startActivityForResult(Intent(mContext, BankSelectActivity::class.java), ZHONGHANG)
        }
        tv_bank_zhihang.setOnClickListener {
            startActivityForResult(Intent(mContext, BankSelectActivity::class.java)
                    .putExtra("isZhihang",true), ZHIHANG)
        }
        tv_reset.setOnClickListener {
            tv_start_time.text = "开始时间"
            tv_end_time.text = "结束时间"
            tv_subbranch.text = ""
            edt_retain.setText("")
            tv_bank.text = ""
            tv_bank_zhihang.text = ""
            edt_client_name.setText("")
        }
        tv_ok.setOnClickListener {
            var screenInfo = ScreenInfo()
            if (tv_start_time.text.toString() == "开始时间" && tv_end_time.text.toString() == "结束时间") {
                if (tv_subbranch.getTag(R.id.TAG) == null) {
                    popAchievementView.dismiss()
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
            userId=SharedPreferencesUtil.get(mContext, "userId")
            } else {
                userId= tv_subbranch.getTag(R.id.TAG).toString()
            }
            tv_subbranch.setTag(R.id.TAG, null)
            popAchievementView.dismiss()
            getData(screenInfo)
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

    //选择管理员
    val USER = 1
    val ZHONGHANG = 2
    val ZHIHANG = 3
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data.let {

        }
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
        title_tv_right.setOnClickListener { popAchievementView.showAsDropDown(ll_parent) }
    }

    //标题
    var titles = ArrayList<ATableTitle>()
    //表格内容
    var cloumnData = ArrayList<ArrayList<ATableTitle>>()
    //侧边姓名
    var names = ArrayList<String>()

    var userId=""
    override fun initData() {
        userId=SharedPreferencesUtil.get(mContext, "userId")
        getData()

    }
    /***
     * 获取数据
     */
    fun getData(screenInfo: ScreenInfo = ScreenInfo()) {
        var params: java.util.HashMap<String, Any> = HashMap()
        params["userId"] = userId
        Log.e("achievment207", SharedPreferencesUtil.get(mContext, "userId"))
        params["personNum"] = edt_retain.text.toString()
        params["personName"] = edt_client_name.text.toString()
        params["zhihang"] = tv_bank_zhihang.text.toString()
        params["fenhang"] = tv_bank.text.toString()
        params["startdate"] = screenInfo.startTime
        params["enddate"] = screenInfo.endTime
        params["page"] = 1
        params["pagesize"] = 10000
        var headers = java.util.HashMap<String, Any>()
        headers["appkey"] = SharedPreferencesUtil.get(mContext, "appkey")
        headers["timestamp"] = SharedPreferencesUtil.get(mContext, "timestamp")
        headers["signature"] = SharedPreferencesUtil.get(mContext, "signature")
        RestClient.create()
                .params(params)
                .headers(headers)
                .url("api/BankData/GetDataList")
                .request(object : IRequest {
                    override fun onRequestStart() {
                        rxDialogLoading.show()
                    }

                    override fun onRequestEnd() {
                        rxDialogLoading.cancel()
                    }
                }).success {
                    var data = GsonUtil.getInstance().fromJson<HttpResult<AchievementInfo>>(it,
                            object : TypeToken<HttpResult<AchievementInfo>>() {}.type)
                    if (data.isSuccess) {

                        bindVIew(data.data)
                    }
                }.error { code, msg ->
                    ToastUtil.show(mContext, "请求错误code:$code$msg")
                }.failure {
                    if (NetWorkUtil.isNetworkConnected(mContext)) {
                        ToastUtil.show(mContext, resources.getString(R.string.net_error))
                    } else {
                        ToastUtil.show(mContext, resources.getString(R.string.net_no_worker))
                    }

                }
                .build()
                .post()
    }

    private fun bindVIew(data: AchievementInfo) {
        titles.clear()
        cloumnData.clear()
        names.clear()

        //本月累积
        tv_month.text = data.personBalanceALL.toString()
        // 月日均
        tv_day.text = data.averMonthBalanceALL.toString()
        // 本年累积
        tv_year.text = data.lastMonthBalanceAll.toString()
        // 客户经理总数
        tv_customer.text = data.personCount.toString()
        if (data.dataList.size==0){
            ToastUtil.show(mContext,"没有查询到您要的数据")
            scrollable_panel.visibility=View.GONE
            return
        }else{
            scrollable_panel.visibility=View.VISIBLE
        }
        initTable()
        val title = arrayOf("揽存号", "运管员", "中支名称", "支行名称", "余额",
                "月日均", "比上月", "年/月")
        //头部标题数据
        for (i in title.indices) {
            val date = title[i]
            titles.add(ATableTitle(date, 0))
        }
        scrollablePanelAdapter.setDateInfoList(titles)
        //里面数据
        for (i in data.dataList.indices) {
            //左侧数据
            names.add(data.dataList[i].personName)
            var datas = ArrayList<ATableTitle>()
//            for (j in title.indices) {
//                val date = "内容$i$j"
//                datas.add(ATableTitle(date, 0))
//            }
            //表格内容
            datas.add(ATableTitle(data.dataList[i].personNum, 0))
            datas.add(ATableTitle(data.dataList[i].nickname, 0))
            datas.add(ATableTitle(data.dataList[i].fenhang, 0))
            datas.add(ATableTitle(data.dataList[i].zhihang, 0))
            datas.add(ATableTitle(data.dataList[i].personBalanceStr,0))
            datas.add(ATableTitle(data.dataList[i].averMonthBalanceStr,0))
            datas.add(ATableTitle(data.dataList[i].lastMonthBalanceStr, data.dataList[i].lastMonthBalance.toInt()))
            datas.add(ATableTitle(data.dataList[i].personDate, 0))
            cloumnData.add(datas)
        }
        scrollablePanelAdapter.setRoomInfoList(names)
        scrollablePanelAdapter.setOrdersList(cloumnData)
    }

}
