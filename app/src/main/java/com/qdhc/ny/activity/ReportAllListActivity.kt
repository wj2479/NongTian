package com.qdhc.ny.activity

import android.content.Intent
import com.baoyz.actionsheet.ActionSheet
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.Constant
import com.qdhc.ny.entity.Project
import com.qdhc.ny.entity.User
import com.qdhc.ny.fragment.ReportFragment
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 周报日报 月报 列表
 */
class ReportAllListActivity : BaseActivity(), ActionSheet.ActionSheetListener {
    lateinit var project: Project

    override fun intiLayout(): Int {
        return R.layout.activity_report_all_list
    }

    override fun initView() {

        val user = intent.getSerializableExtra("user") as User
//        if (user.role == 1) {
//            title_tv_right.visibility = View.VISIBLE
//            title_tv_right.text = "添加  "
//        }

        title_tv_title.text = "工程质量"
        project = intent.getSerializableExtra("project") as Project
        //viewpager加载adapter
        supportFragmentManager.beginTransaction().add(R.id.contentLayout, ReportFragment(project)).commitAllowingStateLoss()
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
        title_tv_right.setOnClickListener {
            ActionSheet.createBuilder(this, getSupportFragmentManager())
                    .setCancelButtonTitle("取消")
                    .setOtherButtonTitles("日报", "周报", "月报")
                    .setCancelableOnTouchOutside(true)
                    .setListener(this).show();
        }
    }

    override fun initData() {

    }

    override fun onOtherButtonClick(actionSheet: ActionSheet?, index: Int) {
        var intent = Intent(this, AddReportActivity::class.java)
        intent.putExtra("project", project)
        when (index) {
            0 -> {
                intent.setClass(this, UpdateDailyReportActivity::class.java)
            }
            1 -> intent.putExtra("type", Constant.REPORT_TYPE_WEEK)
            2 -> intent.putExtra("type", Constant.REPORT_TYPE_WEEK)
        }

        startActivity(intent)
    }

    override fun onDismiss(actionSheet: ActionSheet?, isCancel: Boolean) {
    }
}
