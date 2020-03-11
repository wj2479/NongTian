package com.qdhc.ny.fragment


import android.content.Intent
import android.support.v4.app.Fragment
import android.view.View
import com.qdhc.ny.R
import com.qdhc.ny.activity.NotifyPublishActivity
import com.qdhc.ny.adapter.MyFragmentPagerAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.entity.Role
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.fragment_notify.*


/**
 * 通知
 * A simple [Fragment] subclass.
 */
class NotifyFragment : BaseFragment() {

    lateinit var mAdapter: MyFragmentPagerAdapter
    lateinit var user: User

    val mFragments = ArrayList<Fragment>()
    lateinit var mTitles: Array<String>

    override fun intiLayout(): Int {
        return R.layout.fragment_notify
    }

    override fun initView() {

        user = SharedPreferencesUtils.loadLogin(context)
        when (user.role.code) {
            Role.CODE.SUPERVISOR.value -> {
                mTitles = arrayOf("我收到的")
                mTabLayout.visibility = View.GONE
                mFragments.add(NotifyReceivedFragment())
            }
            else -> {
                title_tv_right.setText("发布通知")
                title_tv_right.visibility = View.VISIBLE

                mTitles = arrayOf("我收到的", "我发出的")
                mFragments.add(NotifyReceivedFragment())
                mFragments.add(NotifyPublishFragment())
            }
        }
        setupWithViewPager()
    }

    override fun initClick() {
        title_tv_right.setOnClickListener {
            //发布通知
            startActivity(Intent(context, NotifyPublishActivity::class.java).putExtra("user", user))
        }
    }

    override fun initData() {
    }

    override fun lazyLoad() {
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
