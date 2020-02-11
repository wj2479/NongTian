package com.qdhc.ny.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.qdhc.ny.R
import com.qdhc.ny.activity.*
import com.qdhc.ny.adapter.UltraPagerAdapter
import com.qdhc.ny.base.BaseFragment
import com.qdhc.ny.bean.IndexInfo
import com.qdhc.ny.bmob.Banner
import com.qdhc.ny.bmob.Notice
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.tmall.ultraviewpager.UltraViewPager
import kotlinx.android.synthetic.main.fragment_index.*

class IndexFragment : BaseFragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): IndexFragment {
            val fragment = IndexFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun intiLayout(): Int {
        return R.layout.fragment_index
    }

    lateinit var userInfo: User
    override fun initView() {
        initRXText()
        userInfo = SharedPreferencesUtils.loadLogin(context)
//        tv_notice.text = ("团队轨迹")
//        tv_notice.setCompoundDrawablesWithIntrinsicBounds(
//                null, ContextCompat.getDrawable(activity!!, R.drawable.ic_team), null, null)
    }

    private fun initImagePage(imageinfo: List<IndexInfo.BannerBean>) {
        // 绑定数据
        var ultraViewPager: UltraViewPager = ultra_viewpager
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL)
        //UltraPagerAdapter 绑定子view到UltraViewPager
        val adapter = UltraPagerAdapter(activity, false, imageinfo)
        ultraViewPager.adapter = adapter
        //内置indicator初始化
        ultraViewPager.initIndicator()
        //设置indicator样式
        ultraViewPager.indicator
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(ContextCompat.getColor(activity!!, R.color.themecolor))
                .setNormalColor(Color.WHITE)
                .setIndicatorPadding(10)
                .setRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics).toInt())
        //设置indicator对齐方式
        ultraViewPager.indicator.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        ultraViewPager.indicator.setMargin(0, 0, 0, 20)
        //构造indicator,绑定到UltraViewPager
        ultraViewPager.indicator.build()
        //设定页面循环播放
        ultraViewPager.setInfiniteLoop(true)
        //设定页面自动切换  间隔 秒
        ultraViewPager.setAutoScroll(3000)
    }

    override fun initClick() {
        //更多公告
        tv_notice_more.setOnClickListener { startActivity(Intent(activity, NoticeActivity::class.java)) }
        //签到记录
        tv_sign_in.setOnClickListener { startActivity(Intent(activity, SignInListActivity::class.java).putExtra("userInfo", userInfo)) }
        //运动轨迹
        tv_achievement.setOnClickListener {
            startActivity(Intent(activity, TraceRecordActivity::class.java).putExtra("userInfo", userInfo))
        }
        //我的客户
        tv_customer.setOnClickListener {
            startActivity(Intent(activity, ClientManagerActivity::class.java))
        }
        //客户评价
        tv_evaluate.setOnClickListener { startActivity(Intent(activity, SignInActivity::class.java).putExtra("isCount", true)) }
        //日报
        tv_journal.setOnClickListener { startActivity(Intent(activity, SignInSearActivity::class.java)) }
        //公告
        tv_notice.setOnClickListener {
            startActivity(Intent(activity, NoticeActivity::class.java))
        }

        //案例分享
        tv_share.setOnClickListener {
            startActivity(Intent(activity, ShareSubmitActivity::class.java))
        }
        //投诉建议
        tv_proposal.setOnClickListener {
            startActivity(Intent(activity, FeedbackActivity::class.java))
        }
        //月度考核榜
        iv.setOnClickListener {
            startActivity(Intent(activity, AchievementRankingActivity::class.java))
        }
    }

    override fun initData() {

    }

    override fun lazyLoad() {
        getDatas()
    }

    fun getDatas() {
        val categoryBmobQuery = BmobQuery<Banner>()
        categoryBmobQuery.findObjects(
                object : FindListener<Banner>() {
                    override fun done(list: List<Banner>?, e: BmobException?) {
                        if (e == null) {
                            var bannerList = ArrayList<IndexInfo.BannerBean>()

                            list?.forEach { it ->
                                var banner = IndexInfo.BannerBean()
                                banner.imgUrl = it.path.url
                                bannerList.add(banner)
                            }
                            initImagePage(bannerList)
                        } else {
                            Log.i("异常-----》", e.toString())
                        }
                    }
                })

        var bmobQuery = BmobQuery<Notice>();
        bmobQuery.order("-createdAt")

        bmobQuery.findObjects(object : FindListener<Notice>() {
            override fun done(list: MutableList<Notice>?, e: BmobException?) {
                if (e == null) {
                    list?.forEach { notice ->
                        titleList.add(notice.title)
                    }
                    noticeInfos.addAll(list!!)
                    ProjectData.getInstance().notices = list
                    rxtext.setTextList(titleList)
                }
            }
        })
    }

    private val titleList = ArrayList<String>()
    override fun onResume() {
        super.onResume()
        rxtext.startAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        rxtext.stopAutoScroll()
    }

    var noticeInfos = ArrayList<Notice>()

    private fun initRXText() {
        rxtext.setText(13f, 5, ContextCompat.getColor(activity!!, R.color.hui))//设置属性
        rxtext.setTextStillTime(5000)//设置停留时长间隔
        rxtext.setAnimTime(500)//设置进入和退出的时间间隔
        rxtext.setOnItemClickListener({
            startActivity(Intent(activity, NoticeDetailActivity::class.java).putExtra("notice", noticeInfos[it]))
        })
    }
}
