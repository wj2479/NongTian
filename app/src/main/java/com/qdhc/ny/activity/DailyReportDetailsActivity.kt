package com.qdhc.ny.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.luck.picture.lib.PictureSelector
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ImageAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.entity.DailyReport
import com.qdhc.ny.entity.Media
import com.qdhc.ny.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_daily_report_details.*
import kotlinx.android.synthetic.main.layout_title_theme.*


/**
 * 日志详情
 */
class DailyReportDetailsActivity : BaseActivity() {

    lateinit var adapter: ImageAdapter

    var selectList = ArrayList<Media>()

    override fun intiLayout(): Int {
        return R.layout.activity_daily_report_details
    }

    override fun initView() {
        title_tv_title.text = "日报详情"

        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = GridLayoutManager(this, 4) as RecyclerView.LayoutManager?
        adapter = ImageAdapter(this, selectList)
        adapter.setOnItemClickListener { position, v ->
            var url = selectList.get(position).url

            if (url.endsWith("mp4", true)) {
                PictureSelector.create(this@DailyReportDetailsActivity).externalPictureVideo(url);
            } else {
                var intent = Intent(this, ImageActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
            }
        }
        rlv.adapter = adapter
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        var report = intent.getSerializableExtra("report") as DailyReport

        Log.e("TAG", "日志数据--> " + gson.toJson(report))

        nameTv.text = report.title
        locationTv.text = report.address
        timeTv.text = report.createTime
        contentTv.text = report.content

        when (report.check) {
            0 -> checkTv.text = "合格"
            1 -> checkTv.text = "一般"
            2 -> checkTv.text = "不合格"
        }

        var userInfo = SharedPreferencesUtils.loadLogin(this)

        if (userInfo.id.equals(report.uid)) {
            personLayout.visibility = View.GONE
            locationLayout.visibility = View.GONE
        } else {

        }

        if (report.mediaList.size > 0) {
            selectList.addAll(report.mediaList)
            adapter.notifyDataSetChanged()
        } else {
            photoLayout.visibility = View.GONE
        }
    }


}
