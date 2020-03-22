package com.qdhc.ny.fragment


import android.content.Intent
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.amap.api.location.AMapLocation
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.qdhc.ny.R
import com.qdhc.ny.activity.ProjectInfoActivity
import com.qdhc.ny.activity.RegionProjectDetailsActivity
import com.qdhc.ny.activity.RegionProjectListActivity
import com.qdhc.ny.adapter.ProjectPageAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.common.ProjectLevel
import com.qdhc.ny.entity.LiveWeather
import com.qdhc.ny.entity.Project
import com.qdhc.ny.entity.User
import com.qdhc.ny.view.MyAxisValueFormatter
import com.qdhc.ny.view.MyValueFormatter
import com.qdhc.ny.view.XAxisValueFormatter
import com.sj.core.net.Rx.RxRestClient
import com.sj.core.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_project_into_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.text.SimpleDateFormat
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

    val mHandler = Handler()

    override fun intiLayout(): Int {
        return R.layout.fragment_project_into_list
    }

    override fun initView() {
        mChart.setNoDataText("暂无数据")
//        initBarChart()
//        setBarChartData(entryList)
    }

    override fun initClick() {
        detailsIv.setOnClickListener {
            if (project != null) {
                var intent = Intent(context, RegionProjectDetailsActivity::class.java)
                intent.putExtra("project", project)
                intent.putExtra("subProjects", subProjectList)
                startActivity(intent)
            } else {
                ToastUtil.show(context, "您还没有项目")
            }
        }
    }

    override fun initData() {
        userInfo = ProjectData.getInstance().userInfo
        helloTv.text = "您好, " + userInfo.nickName
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy年MM月dd日")
        val str1 = arrayOf("", "日", "一", "二", "三", "四", "五", "六")
        dateTv.text = "今天是 " + simpleDateFormat.format(Date()) + "\t星期" + str1[calendar.get(Calendar.DAY_OF_WEEK)]

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

                                //点击事件
                                tv_title.setOnClickListener {
                                    var intent = Intent(context, ProjectInfoActivity::class.java)
                                    intent.putExtra("project", project)
                                    startActivity(intent)
                                }

                                EventBus.getDefault().post(project)

                                var level = ProjectLevel.getEnumType(project!!.level + 1)
                                tv_district.text = level.desc + "进度表"
                                getInitSubProjects(project!!.id)
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
     * 获取初始化的子项目
     */
    fun getInitSubProjects(pid: Int) {
        getSubProjects(pid, object : Consumer<String> {
            override fun accept(result: String?) {
                var json = JSONObject(result)
                if (json.getInt("code") == 1000) {
                    var jArray = json.getJSONArray("result")
                    Log.e("TAG", "请求22成功:" + jArray.toString())
                    if (jArray.length() > 0) {
                        var xTitles = mutableListOf<String>()

                        for (index in 0 until jArray.length()) {
                            val jsonObject = jArray.getJSONObject(index)
                            val project = gson.fromJson(jsonObject.toString(), Project::class.java)
                            if (project.area.regionLevel == 4) {
                                xTitles.add(project.name)
                            } else {
                                xTitles.add(project.area.name)
                            }
                            entryList.add(BarEntry(index.toFloat(), project.process.toFloat(), project))
                            subProjectList.add(project)
                        }

                        initBarChart(xTitles.toTypedArray())
                        setBarChartData(entryList)

                        //设置一页显示的数据条数，超出的数量需要滑动查看：
                        mChart.setVisibleXRangeMaximum(5.0f);//需要在设置数据源后生效
                        mChart.notifyDataSetChanged(); // let the chart know it's data changed
                        mChart.invalidate(); // refresh
                    }
                } else {
                    Log.e("TAG", "请求22失败:" + result)
                }
            }
        }, object : Consumer<Throwable> {
            override fun accept(t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }

    /**
     * 获取项目的子项目
     */
    fun getSubProjects(pid: Int, onNext: Consumer<String>, onError: Consumer<Throwable>) {
        var params: HashMap<String, Any> = HashMap()
        params["pid"] = pid
        RxRestClient.create()
                .url("project/getSubProjectsByParentId")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(location: AMapLocation) {
        Log.e("TAG", "定位事件")
        if (null != location) {
            getWeather(location.adCode)
        }
    }

    /**
     * 通过高德地图的WebApi获取天气
     */
    private fun getWeather(code: String) {
        var params: HashMap<String, Any> = HashMap()
        params["city"] = code
        params["key"] = "eb11935d19133c09530dc67e63cb1393"
        // 可选值：base/all
        // base:返回实况天气
        // all:返回预报天气
        params["extensions"] = "base"

        RxRestClient.create()
                .url("https://restapi.amap.com/v3/weather/weatherInfo")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.e("TAG", "天气:" + result)
                            var weather = gson.fromJson(result, LiveWeather::class.java)
                            tempTv.text = weather.lives[0].temperature + "℃"
                            weatherTv.text = weather.lives[0].weather
                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }

    var entryList = ArrayList<BarEntry>()

    /**
     * 柱状图选中的项目
     */
    var selectProject: Project? = null
    /**
     * 是不是被触摸按下后抬起
     */
    var isTouchUp = false
    /**
     * 是不是按下时被选择
     */
    var isDownSelect = false

    /**
     * 初始化柱形图控件属性
     */
    private fun initBarChart(datas: Array<String>) {
//        mChart.setOnChartValueSelectedListener(this)
        mChart.setDrawBarShadow(false)
        mChart.setDrawValueAboveBar(true)
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

        mChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
                selectProject = null
            }

            override fun onValueSelected(entry: Entry?, h: Highlight?) {
                if (entry != null) {
                    selectProject = entry.data as Project
                    Log.e("TAG", "Selected:")
                    if (isTouchUp) {
                        getClickSubProjects(selectProject!!)
                    } else {
                        isDownSelect = true
                    }
                }

            }
        })

        // 解决触摸出现的问题
        mChart.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_UP) {
//                    Log.e("TAG", "抬起:" + System.currentTimeMillis())
                    isTouchUp = true
                    if (isDownSelect) {
                        getClickSubProjects(selectProject!!)
                    }
                } else if (event?.action == MotionEvent.ACTION_DOWN) {
//                    Log.e("TAG", "按下:" + System.currentTimeMillis())
                    isTouchUp = false
                    isDownSelect = false
                }
                return false;
            }
        })
    }

    /**
     * 点击项目的事件
     */
    fun getClickSubProjects(project: Project) {
        showDialog("正在获取项目数据...")
        getSubProjects(project.id, object : Consumer<String> {
            override fun accept(result: String?) {
                dismissDialogNow()
                var json = JSONObject(result)
                if (json.getInt("code") == 1000) {
                    var jArray = json.getJSONArray("result")
                    Log.e("getClickSubProjects", "请求成功:" + jArray.toString())
                    var subProjectList = ArrayList<Project>()
                    for (index in 0 until jArray.length()) {
                        val jsonObject = jArray.getJSONObject(index)
                        val project = gson.fromJson(jsonObject.toString(), Project::class.java)
                        subProjectList.add(project)
                    }

                    var intent = Intent()
                    intent.putExtra("project", project)
                    if (subProjectList.size == 0) {
                        intent.setClass(context, ProjectInfoActivity::class.java)
                    } else {
                        intent.setClass(context, RegionProjectListActivity::class.java)
                        intent.putExtra("subProject", subProjectList)
                    }
                    startActivity(intent)
                } else {
                    Log.e("getClickSubProjects", "请求22失败:" + result)
                }
            }
        }, object : Consumer<Throwable> {
            override fun accept(t: Throwable?) {
                dismissDialogNow()
                t?.printStackTrace()
            }
        })
    }

    /**
     * 设置柱状图数据
     *
     */
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
            data.setValueTextSize(12.0f);
//                data.setValueTypeface(mTfLight);//可以去掉，没什么用
            data.setBarWidth(0.45f);
            mChart.setData(data);
        }
    }

}
