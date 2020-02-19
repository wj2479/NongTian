package com.qdhc.ny

import android.support.v4.app.Fragment
import android.widget.Toast
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.google.gson.Gson
import com.qdhc.ny.adapter.TabFragmentPagerAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.TabIconBean
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.User
import com.qdhc.ny.fragment.ContradictionListFragment
import com.qdhc.ny.fragment.MyFragment
import com.qdhc.ny.fragment.NotifyFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * 乡镇领导
 */
class Main4Activity : BaseActivity() {
    lateinit var userInfo: User

    var gson = Gson()
    override fun intiLayout(): Int {
        return (R.layout.activity_main2)
    }

    override fun initClick() {
    }

    override fun initData() {

    }

    //UI
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private val mIconUnselectIds = intArrayOf(R.drawable.ic_list,
            R.mipmap.icon_notice,
            R.mipmap.icon_wode)
    private val mIconSelectIds = intArrayOf(R.drawable.ic_list_select,
            R.mipmap.icon_notice_select,
            R.mipmap.icon_wode_select)

    override fun initView() {
        userInfo = ProjectData.getInstance().userInfo

        //获取数据 在values/arrays.xml中进行定义然后调用
        var tabTitle = resources.getStringArray(R.array.tab3_titles)
        //将fragment装进列表中
        var fragmentList = ArrayList<Fragment>()
        fragmentList.add(ContradictionListFragment(1, "", true))
        fragmentList.add(NotifyFragment())
        fragmentList.add(MyFragment())
        //viewpager加载adapter
        vp.adapter = TabFragmentPagerAdapter(supportFragmentManager, fragmentList, tabTitle)
        for (i in fragmentList.indices) {
            mTabEntities.add(TabIconBean(tabTitle[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }
        tl.setTabData(mTabEntities)
        initPager()
    }

    var tab_position = 0
    override fun onRestart() {
        super.onRestart()
        tl.currentTab = tab_position
    }

    private fun initPager() {
        tl.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                vp.currentItem = position
                tab_position = position
            }

            override fun onTabReselect(position: Int) {
                if (position == 0) {
                    //  tl.showMsg(0, 56)
                }
            }
        })

        //默认选中第一个
        vp.currentItem = 0
        vp.offscreenPageLimit = 5
        vp.setNoScroll(true)
    }


    private var clickTime: Long = 0 // 第一次点击的时间

    override fun onBackPressed() {
        if (System.currentTimeMillis() - clickTime > 2000) {
            Toast.makeText(this, "再按一次键退出", Toast.LENGTH_SHORT).show()
            clickTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }
}
