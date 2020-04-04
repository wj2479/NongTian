package com.qdhc.ny.fragment

import android.support.v4.content.ContextCompat
import android.util.Log
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.dialog.RxDialogWheelYearMonth
import com.qdhc.ny.entity.UserReportCount
import com.qdhc.ny.view.MyAxisValueFormatter
import com.qdhc.ny.view.MyOnlyValueFormatter
import com.qdhc.ny.view.XAxisValueFormatter
import com.sj.core.net.Rx.RxRestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_report_day_count.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日报月统计
 */
class ReportMonthCountFragment : BaseFragment() {

    val sdf = SimpleDateFormat("yyyy-MM");
    var countList = ArrayList<UserReportCount>()

    override fun lazyLoad() {
    }

    override fun initClick() {
        tv_date.setOnClickListener { mRxDialogWheelYearMonth.show() }
    }

    override fun intiLayout(): Int {
        return R.layout.fragment_report_month_count
    }

    override fun initData() {
        var date = sdf.format(Date())
        tv_date.text = date
        getReportCount(ProjectData.getInstance().userInfo.id, date)
    }

    override fun initView() {
        mChart.setNoDataText("暂无统计数据")
        initWheelYearMonthDialog()
    }

    lateinit var mRxDialogWheelYearMonth: RxDialogWheelYearMonth
    private fun initWheelYearMonthDialog() {
        // ------------------------------------------------------------------选择日期开始
        mRxDialogWheelYearMonth = RxDialogWheelYearMonth(context)
        mRxDialogWheelYearMonth.sureView.setOnClickListener {
            var date_txt = ("" +
                    mRxDialogWheelYearMonth.selectorYear + "-"
                    + mRxDialogWheelYearMonth.selectorMonth)
            tv_date.text = date_txt

            getReportCount(ProjectData.getInstance().userInfo.id, date_txt)
            mRxDialogWheelYearMonth.cancel()
        }
        mRxDialogWheelYearMonth.cancleView.setOnClickListener(
                { mRxDialogWheelYearMonth.cancel() })
        // ------------------------------------------------------------------选择日期结束
    }


    fun getReportCount(uid: Int, date: String) {
        var params: HashMap<String, Any> = HashMap()
        params["uid"] = uid
        params["date"] = date
        RxRestClient.create()
                .url("report/getReportCount")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<String> {
                    override fun accept(result: String?) {
                        var json = JSONObject(result)
                        if (json.getInt("code") == 1000) {
                            var xTitles = mutableListOf<String>()

                            var countEntryList = ArrayList<BarEntry>()
                            var sumEntryList = ArrayList<BarEntry>()
                            var jArray = json.getJSONArray("result")
                            Log.e("TAG", "获取月统计结果成功:" + jArray.toString())
                            if (jArray.length() > 0) {
                                for (index in 0 until jArray.length()) {
                                    val jsonObject = jArray.getJSONObject(index)
                                    val reportCount = gson.fromJson(jsonObject.toString(), UserReportCount::class.java)
                                    xTitles.add(reportCount.nickName)
                                    countEntryList.add(BarEntry(index.toFloat() + 0.35f, reportCount.count.toFloat() + 0.1f, reportCount))
                                    sumEntryList.add(BarEntry(index.toFloat(), reportCount.sum.toFloat() + 0.1f, reportCount))
                                    countList.add(reportCount)
                                }

                                initBarChart(xTitles.toTypedArray())
                                setBarChartData(countEntryList, sumEntryList)

                                mChart.setVisibleXRangeMaximum(10.0f);//需要在设置数据源后生效
                                mChart.notifyDataSetChanged(); // let the chart know it's data changed
                                mChart.invalidate(); // refresh

                                mChart.moveViewTo(1f, jArray.length().toFloat(), YAxis.AxisDependency.LEFT)
                            }
                        } else {
                            Log.e("TAG", "获取月统计结果失败:" + result)
                        }
                    }
                },
                        object : Consumer<Throwable> {
                            override fun accept(t: Throwable?) {
                                t?.printStackTrace()
                            }
                        })
    }

    /**
     * 初始化柱形图控件属性
     */
    private fun initBarChart(datas: Array<String>) {
//        mChart.setOnChartValueSelectedListener(this)
        mChart.setDrawBarShadow(false)
        mChart.setDrawValueAboveBar(true)
        //禁用描述
        mChart.getDescription().setEnabled(false)
        mChart.setTouchEnabled(true); // 设置是否可以触摸
        mChart.setScaleEnabled(false); // 打开或关闭x，y轴的缩放
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60)
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false)  // 如果设置为true，挤压缩放被打开。如果设置为false，x和y轴可以被单独挤压缩放。
        mChart.setDrawGridBackground(false)

        //自定义坐标轴适配器，配置在X轴，xAxis.setValueFormatter(xAxisFormatter);
        val xAxisFormatter = XAxisValueFormatter(datas)

        val xAxis = mChart.getXAxis()
        //取消x轴竖线
        xAxis.setDrawGridLines(false);
        //设置x轴位于底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
//        xAxis.setTypeface(mTfLight)//可以去掉，没什么用
        xAxis.setDrawAxisLine(false)
        xAxis.setGranularity(1f)
        xAxis.setLabelCount(10)
        xAxis.textSize = 14.0f
        xAxis.setValueFormatter(xAxisFormatter)

        //自定义坐标轴适配器，配置在Y轴。leftAxis.setValueFormatter(custom);
        val custom = MyAxisValueFormatter()
        var right = mChart.getAxisRight();
        right.setDrawGridLines(false);
        right.setAxisMinimum(0f);
//        right.setAxisMaximum(100f);
        right.setValueFormatter(custom)
        right.setEnabled(false)

        //在定义y轴的时候,需要两边都进行设置，而不是直接setEnabled禁用。否则将产生数据偶尔不显示的问题
        var left = mChart.getAxisLeft();
        left.setAxisMinimum(0f);
//        left.setAxisMaximum(100f);
        left.setEnabled(false)

        //图例设置
        val legend = mChart.getLegend()
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)

        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(9f);
        legend.setTextSize(11f);
        legend.setXEntrySpace(4f);
    }

    /**
     * 设置柱状图数据
     *
     */
    private fun setBarChartData(countEntryList: List<BarEntry>, sumEntryList: List<BarEntry>) {
        var set1 = BarDataSet(countEntryList, "日报总数量");
        //是否显示顶部的值
        set1.setDrawValues(true);
        // 设置顶部的格式方式
        set1.setValueFormatter(MyOnlyValueFormatter())
        val startColor1 = ContextCompat.getColor(context!!, R.color.text_color_green)
        set1.setColors(startColor1)
        set1.isHighlightEnabled = false   // 不允许高亮

        var set2 = BarDataSet(sumEntryList, "照片和视频总数量");
        set2.setDrawValues(true);
        set2.setValueFormatter(MyOnlyValueFormatter())
        val startColor2 = ContextCompat.getColor(context!!, R.color.text_color_orange)
        set2.setColors(startColor2)
        set2.isHighlightEnabled = false   // 不允许高亮

        var dataSets = ArrayList<IBarDataSet>();
        dataSets.add(set1)
        dataSets.add(set2)

        var data = BarData(dataSets);
        data.setValueTextSize(12.0f);
        data.setBarWidth(0.3f);
        mChart.setData(data);
    }

}
