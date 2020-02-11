package com.qdhc.ny.fragment

import android.support.v4.app.Fragment
import com.qdhc.ny.adapter.MyFragmentPagerAdapter
import com.qdhc.ny.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_project_tab.*

class ProjectTwoTabsFragment : BaseFragment() {

    lateinit var mAdapter: MyFragmentPagerAdapter

    override fun intiLayout(): Int {
        return com.qdhc.ny.R.layout.fragment_project_tab
    }

    override fun initView() {
        setupWithViewPager()
    }

    /**
     * Description：初始化FragmentPagerAdapter适配器并给ViewPager设置上该适配器，最后关联TabLayout和ViewPager
     */
    private fun setupWithViewPager() {
        var mFragments = ArrayList<Fragment>()

        var mTitles = arrayOf("东港区", "莒县", "五莲县", "岚山区")
        mTitles?.forEachIndexed { index, title ->
            mTabLayout.addTab(mTabLayout.newTab().setText(title))
            var areaId = index + 1
            mFragments.add(ProjectTabFragment(areaId, false))
        }

        mAdapter = MyFragmentPagerAdapter(childFragmentManager)
        mAdapter.addTitlesAndFragments(mTitles, mFragments)

        mViewPager.setOffscreenPageLimit(0)
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
