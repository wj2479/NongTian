package com.qdhc.ny.fragment

import android.support.v4.app.Fragment
import com.qdhc.ny.R
import com.qdhc.ny.adapter.MyFragmentPagerAdapter
import com.qdhc.ny.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_notify.*

/**
 *  待办事项
 */
class ToDoListFragment : BaseFragment() {
    lateinit var mAdapter: MyFragmentPagerAdapter

    val mFragments = ArrayList<Fragment>()
    lateinit var mTitles: Array<String>

    override fun lazyLoad() {
    }

    override fun initClick() {
    }

    override fun intiLayout(): Int {
        return R.layout.fragment_to_do_list
    }

    override fun initData() {
    }

    override fun initView() {
        mTitles = arrayOf("不合格记录", "关注列表")
        mFragments.add(UnqualifiedReportFragment())
        mFragments.add(AttentionFragment())

        setupWithViewPager()
    }

    /**
     * Description：初始化FragmentPagerAdapter适配器并给ViewPager设置上该适配器，最后关联TabLayout和ViewPager
     */
    private fun setupWithViewPager() {
        mAdapter = MyFragmentPagerAdapter(childFragmentManager)
        mAdapter.addTitlesAndFragments(mTitles, mFragments)

        mViewPager.setAdapter(mAdapter) // 给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager) //关联TabLayout和ViewPager
    }
}
