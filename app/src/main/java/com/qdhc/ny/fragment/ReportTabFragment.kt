package com.qdhc.ny.fragment

import android.annotation.SuppressLint
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.View
import com.qdhc.ny.adapter.MyFragmentPagerAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.common.ProjectData
import kotlinx.android.synthetic.main.fragment_project_tab.*


@SuppressLint("ValidFragment")
class ReportTabFragment(areaId: Int, isShowTitle: Boolean) : BaseFragment() {

    lateinit var mAdapter: MyFragmentPagerAdapter

    var isShowTitle = true
    var areaId = 0

    init {
        this.areaId = areaId
        this.isShowTitle = isShowTitle
    }

    override fun intiLayout(): Int {
        return com.qdhc.ny.R.layout.fragment_report_tab
    }

    override fun initView() {
        if (!isShowTitle)
            titleLayout.visibility = View.GONE
        setupWithViewPager()
    }

    /**
     * Description：初始化FragmentPagerAdapter适配器并给ViewPager设置上该适配器，最后关联TabLayout和ViewPager
     */
    private fun setupWithViewPager() {
        val mFragments = ArrayList<Fragment>()

        var mTitleList = ArrayList<String>()
        ProjectData.getInstance().villages.forEach { village ->
            if (village.areaId == areaId) {
                mTabLayout.addTab(mTabLayout.newTab().setText(village.name))
                mFragments.add(ReportListFragment(areaId, village.objectId, false))
                mTitleList.add(village.name)
            }
        }
        var mTitles = arrayOfNulls<String>(mTitleList.size)
        mTitleList.toArray(mTitles)


        mAdapter = MyFragmentPagerAdapter(childFragmentManager)
        mAdapter.addTitlesAndFragments(mTitles, mFragments)

        mViewPager.setOffscreenPageLimit(mFragments.size)
        mViewPager.setAdapter(mAdapter) // 给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager) //关联TabLayout和ViewPager
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    override fun initData() {
    }

    override fun lazyLoad() {
        getData()
    }

    override fun initClick() {
//        addIv.setOnClickListener {
//            ActionSheet.createBuilder(context, activity?.getSupportFragmentManager())
//                    .setCancelButtonTitle("取消")
//                    .setOtherButtonTitles("日报", "周报", "月报")
//                    .setCancelableOnTouchOutside(true)
//                    .setListener(this).show();
//        }
    }

    //获取数据
    fun getData() {

    }
}
