package com.qdhc.ny.fragment


import android.content.Intent
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
import com.qdhc.ny.activity.RegionProjectDetailsActivity
import com.qdhc.ny.adapter.ProjectPageAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.Project
import com.qdhc.ny.entity.User
import com.qdhc.ny.eventbus.RegionEvent
import com.qdhc.ny.view.MyAxisValueFormatter
import com.qdhc.ny.view.MyValueFormatter
import com.qdhc.ny.view.XAxisValueFormatter
import com.sj.core.net.Rx.RxRestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_project_into_list.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


/**
 * 工程信息列表
 */
class ProjectInfoListFragment : BaseFragment() {

    var subProjectList = ArrayList<Project>()
    lateinit var mAdapter: ProjectPageAdapter
    lateinit var userInfo: User

//    lateinit var mChart: BarChart

    var project: Project? = null

    override fun intiLayout(): Int {
        return R.layout.fragment_project_into_list
    }

    override fun initView() {
        mChart.setNoDataText("暂无数据")
//        initBarChart()
//        setBarChartData(entryList)

        tv_district.text = "区县进度表"
    }

    override fun initClick() {
        detailsIv.setOnClickListener {
            var intent = Intent(context, RegionProjectDetailsActivity::class.java)
            intent.putExtra("subProjects", subProjectList)
            startActivity(intent)
        }
    }

    override fun initData() {
        userInfo = ProjectData.getInstance().userInfo
        getProjectData(userInfo.id)
    }

    override fun lazyLoad() {

    }

    /**
     * 获取项目基本信息
     */
    fun getProjectData(uid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["uid"] = uid
        RxRestClient.create()
                .url("project/getProjectByUserId")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                var jsonObject = json.getJSONObject("result")
                                project = gson.fromJson(jsonObject.toString(), Project::class.java)
                                tv_title.text = project!!.name
                                total_procss_tv.text = project!!.process.toString() + "%"
                                Log.e("TAG", "请求成功:" + project.toString())
                                getSubProjects(project!!.id)
                            } else {
                                Log.e("TAG", "请求失败:" + result)
                                tv_title.text = "您还没有项目"
                            }

                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }


    /**
     * 获取项目的子项目
     */
    fun getSubProjects(pid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["pid"] = pid
        RxRestClient.create()
                .url("project/getSubProjectsByParentId")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                var jArray = json.getJSONArray("result")
                                Log.e("TAG", "请求22成功:" + jArray.toString())

                                var xTitles = mutableListOf<String>()

                                for (index in 0 until jArray.length()) {
                                    val jsonObject = jArray.getJSONObject(index)
                                    val project = gson.fromJson(jsonObject.toString(), Project::class.java)
                                    xTitles.add(project.area.name)
                                    entryList.add(BarEntry(index.toFloat(),project. process.toFloat()))
                                    subProjectList.add(project)
                                }

                                initBarChart(xTitles.toTypedArray())
                                setBarChartData(entryList)

                                mChart.notifyDataSetChanged(); // let the chart know it's data changed
                                mChart.invalidate(); // refresh
                            } else {
                                Log.e("TAG", "请求22失败:" + result)
                            }

                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: RegionEvent) {
        Log.e("下级结果onMessageEvent", ProjectData.getInstance().subRegion.toString())

        initBarChart()
    }


    var entryList = ArrayList<BarEntry>()

    private fun initBarChart() {

    }

    /**
     * 初始化柱形图控件属性
     */
    private fun initBarChart(datas: Array<String>) {
//        mChart.setOnChartValueSelectedListener(this)
        mChart.setDrawBarShadow(false)
        mChart.setDrawValueAboveBar(true)
        mChart.getDescription().setEnabled(false)
        mChart.setTouchEnabled(false); // 设置是否可以触摸
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60)
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false)
        mChart.setDrawGridBackground(false)

        //自定义坐标轴适配器，配置在X轴，xAxis.setValueFormatter(xAxisFormatter);
        val xAxisFormatter = XAxisValueFormatter(datas)

        val xAxis = mChart.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
//        xAxis.setTypeface(mTfLight)//可以去掉，没什么用
        xAxis.setDrawAxisLine(false)
        xAxis.setGranularity(1f)
        xAxis.setValueFormatter(xAxisFormatter)

        //自定义坐标轴适配器，配置在Y轴。leftAxis.setValueFormatter(custom);
        val custom = MyAxisValueFormatter()

        //获取到图形左边的Y轴
        val leftAxis = mChart.getAxisLeft()
//        leftAxis.addLimitLine(limitLine)
//        leftAxis.setTypeface(mTfLight)//可以去掉，没什么用
        leftAxis.setLabelCount(5, false)
        leftAxis.setValueFormatter(custom)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setSpaceTop(10f)
        leftAxis.setAxisMinimum(0f)
        leftAxis.setAxisMaxValue(100f)

        //获取到图形右边的Y轴，并设置为不显示
        mChart.getAxisRight().setEnabled(false)

        //图例设置
        val legend = mChart.getLegend()
        //不显示图例
        legend.setForm(Legend.LegendForm.NONE);

        //如果点击柱形图，会弹出pop提示框.XYMarkerView为自定义弹出框
//        val mv = XYMarkerView(this, xAxisFormatter)
//        mv.setChartView(mChart)
//        mChart.setMarker(mv)
//        setBarChartData()
    }

    private fun setBarChartData(entryList: List<BarEntry>) {
        var set1: BarDataSet;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = mChart.getData().getDataSetByIndex(0) as BarDataSet;
            set1.setValues(entryList);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = BarDataSet(entryList, "");
            //是否显示顶部的值
            set1.setDrawValues(true);
            // 设置顶部的格式方式
            set1.setValueFormatter(MyValueFormatter())

            val startColor1 = ContextCompat.getColor(context!!, R.color.text_color_green)
            val startColor2 = ContextCompat.getColor(context!!, R.color.text_color_orange)
            set1.setColors(startColor1, startColor2)

            var dataSets = ArrayList<IBarDataSet>();
            dataSets.add(set1);

            var data = BarData(dataSets);
            data.setValueTextSize(10f);
//                data.setValueTypeface(mTfLight);//可以去掉，没什么用
            data.setBarWidth(0.3f);
            mChart.setData(data);
        }
    }

}
