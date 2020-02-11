package com.qdhc.ny.fragment

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import com.qdhc.ny.adapter.MyFragmentPagerAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.common.Constant
import com.qdhc.ny.entity.Project
import kotlinx.android.synthetic.main.fragment_report.*

@SuppressLint("ValidFragment")
class ReportFragment(project: Project) : BaseFragment() {

    lateinit var mAdapter: MyFragmentPagerAdapter

    var project: Project

    init {
        this.project = project
    }

    override fun intiLayout(): Int {
        return com.qdhc.ny.R.layout.fragment_report
    }

    override fun initView() {
        setupWithViewPager()
    }

    /**
     * Description：初始化FragmentPagerAdapter适配器并给ViewPager设置上该适配器，最后关联TabLayout和ViewPager
     */
    private fun setupWithViewPager() {
        var mTitles = context?.resources?.getStringArray(com.qdhc.ny.R.array.report_titles)!!
        mTitles.forEach { title ->
            mTabLayout.addTab(mTabLayout.newTab().setText(title))
        }

        val mFragments = ArrayList<Fragment>()
        mFragments.add(DayReportFragment(project))
        mFragments.add(WeekMonthReportFragment(project, Constant.REPORT_TYPE_WEEK))
        mFragments.add(WeekMonthReportFragment(project, Constant.REPORT_TYPE_MONTH))

        mAdapter = MyFragmentPagerAdapter(childFragmentManager)
        mAdapter.addTitlesAndFragments(mTitles, mFragments)

        mViewPager.setOffscreenPageLimit(mFragments.size)
        mViewPager.setAdapter(mAdapter) // 给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager) //关联TabLayout和ViewPager
    }

    override fun initData() {
    }

    override fun lazyLoad() {
        getData()
    }

    override fun initClick() {
//        addIv.setOnClickListener {
//
//        }
    }

    //获取数据
    fun getData() {

    }
}
