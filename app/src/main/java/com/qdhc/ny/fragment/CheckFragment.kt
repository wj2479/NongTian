package com.qdhc.ny.fragment

import android.content.Intent
import com.qdhc.ny.R
import com.qdhc.ny.activity.LocationGatherActivity
import com.qdhc.ny.activity.NotifyActivity
import com.qdhc.ny.activity.ReportCountActivity
import com.qdhc.ny.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_check.*


/**
 * 考核
 */
class CheckFragment : BaseFragment() {

    override fun intiLayout(): Int {
        return R.layout.fragment_check
    }

    override fun initView() {
    }

    override fun initClick() {
        countLayout.setOnClickListener {
            startActivity(Intent(activity, ReportCountActivity::class.java))
        }

        trackLayout.setOnClickListener {
            startActivity(Intent(activity, LocationGatherActivity::class.java))
        }
        notifyLayout.setOnClickListener {
            startActivity(Intent(activity, NotifyActivity::class.java))
        }
    }

    override fun initData() {

    }

    override fun lazyLoad() {
    }

}
