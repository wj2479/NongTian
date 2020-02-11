package com.qdhc.ny.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.luck.picture.lib.PictureSelector
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ImageAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bmob.Project
import com.qdhc.ny.bmob.Report
import com.qdhc.ny.common.Constant
import com.qdhc.ny.entity.Media
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.qdhc.ny.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_report_details.*
import kotlinx.android.synthetic.main.layout_title_theme.*


/**
 * 日志详情
 */
class ReportDetailsActivity : BaseActivity() {

    lateinit var adapter: ImageAdapter

    var selectList = ArrayList<Media>()

    override fun intiLayout(): Int {
        return R.layout.activity_report_details
    }

    override fun initView() {
        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = GridLayoutManager(this, 4) as RecyclerView.LayoutManager?
        adapter = ImageAdapter(this, selectList)
        adapter.setOnItemClickListener { position, v ->
            var url = selectList.get(position).url

            if (url.endsWith("mp4", true)) {
                PictureSelector.create(this@ReportDetailsActivity).externalPictureVideo(url);
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
        var project = intent.getSerializableExtra("project") as Project

        var report = intent.getSerializableExtra("report") as Report
        var type = report.type

        when (type) {
            Constant.REPORT_TYPE_DAY -> title_tv_title.text = "日报详情"
            Constant.REPORT_TYPE_WEEK -> title_tv_title.text = "周报详情"
            Constant.REPORT_TYPE_MONTH -> title_tv_title.text = "月报详情"
        }

        nameTv.text = project.name
        locationTv.text = report.address
        timeTv.text = report.createdAt
        edt_work.text = report.worktoday
        edt_question.text = report.question
        edt_method.text = report.method
        edt_check.text = report.check

        var userInfo = SharedPreferencesUtils.loadLogin(this)

        if (userInfo.id.equals(report.uid)) {
            personLayout.visibility = View.GONE
            locationLayout.visibility = View.GONE
        } else {
            UserInfoUtils.getInfoByObjectId(report.uid, { userInfo ->
                if (userInfo != null) {
                    personTv.text = userInfo.nickName
                } else {
                    personLayout.visibility = View.GONE
                }
            })
        }

        getImags(report)
    }

    fun getImags(report: Report) {
        if (report == null || report.objectId == null) {
            photoLayout.visibility = View.GONE
            return
        }

    }

}
