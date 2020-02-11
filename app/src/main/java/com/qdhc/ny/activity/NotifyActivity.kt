package com.qdhc.ny.activity

import android.content.Intent
import android.support.v4.app.Fragment
import android.view.View
import com.qdhc.ny.R
import com.qdhc.ny.adapter.MyFragmentPagerAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bmob.Notice
import com.qdhc.ny.bmob.UserInfo
import com.qdhc.ny.fragment.NotifyPublishFragment
import com.qdhc.ny.fragment.NotifyReceivedFragment
import kotlinx.android.synthetic.main.fragment_project_tab.*
import kotlinx.android.synthetic.main.layout_title_theme.*

/**
 * 公告列表
 * @author shenjian
 * @date 2019/3/24
 */
class NotifyActivity : BaseActivity() {

    lateinit var mAdapter: MyFragmentPagerAdapter
    lateinit var user: UserInfo
    var datas = ArrayList<Notice>()

    val mFragments = ArrayList<Fragment>()
    lateinit var mTitles: Array<String>

    override fun intiLayout(): Int {
        return R.layout.activity_notify
    }

    override fun initView() {
        user = intent.getSerializableExtra("user") as UserInfo

        when (user.role) {
            1 -> {
                title_tv_title.text = "我收到的通知"
                mTitles = arrayOf("我收到的")
                mTabLayout.visibility = View.GONE
                mFragments.add(NotifyReceivedFragment())
            }
            4 -> {
                title_tv_title.text = "我的通知"
                title_tv_right.setText("发布通知")
                title_tv_right.visibility = View.VISIBLE

                mTitles = arrayOf("我收到的", "我发出的")
                mFragments.add(NotifyReceivedFragment())
                mFragments.add(NotifyPublishFragment())
            }
            2 -> {
                title_tv_title.text = "我的通知"
                title_tv_right.setText("发布通知")
                title_tv_right.visibility = View.VISIBLE
                mTitles = arrayOf("我收到的", "我发出的")
                mFragments.add(NotifyReceivedFragment())
                mFragments.add(NotifyPublishFragment())
            }
            3 -> {
                title_tv_title.text = "我发出的通知"
                title_tv_right.setText("发布通知")
                title_tv_right.visibility = View.VISIBLE
                mTitles = arrayOf("我发出的")
                mTabLayout.visibility = View.GONE
                mFragments.add(NotifyPublishFragment())
            }
        }
        setupWithViewPager()
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
        title_tv_right.setOnClickListener {
            //发布通知
            startActivity(Intent(this, NotifyPublishActivity::class.java).putExtra("user", user))
        }
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
