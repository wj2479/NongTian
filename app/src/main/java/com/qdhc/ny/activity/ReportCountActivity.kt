package com.qdhc.ny.activity

import android.support.v4.app.Fragment
import com.qdhc.ny.R
import com.qdhc.ny.adapter.MyFragmentPagerAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.fragment.ReportDayCountFragment
import com.qdhc.ny.fragment.ReportMonthCountFragment
import com.qdhc.ny.fragment.ReportWeekCountFragment
import kotlinx.android.synthetic.main.activity_report_count.*

/**
 * 日报 周报统计
 */
class ReportCountActivity : BaseActivity() {
    lateinit var mAdapter: MyFragmentPagerAdapter

    val mFragments = ArrayList<Fragment>()
    lateinit var mTitles: Array<String>

    override fun intiLayout(): Int {
        return R.layout.activity_report_count
    }

    override fun initView() {
        mTitles = arrayOf("日统计", "周统计", "月统计")
        mFragments.add(ReportDayCountFragment())
        mFragments.add(ReportWeekCountFragment())
        mFragments.add(ReportMonthCountFragment())

        setupWithViewPager()
    }

    override fun initClick() {
        backIv.setOnClickListener { finish() }
    }

    override fun initData() {
    }

    /**
     * Description：初始化FragmentPagerAdapter适配器并给ViewPager设置上该适配器，最后关联TabLayout和ViewPager
     */
    private fun setupWithViewPager() {

        mAdapter = MyFragmentPagerAdapter(supportFragmentManager)
        mAdapter.addTitlesAndFragments(mTitles, mFragments)

        mViewPager.setAdapter(mAdapter) // 给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager) //关联TabLayout和ViewPager
    }
}
