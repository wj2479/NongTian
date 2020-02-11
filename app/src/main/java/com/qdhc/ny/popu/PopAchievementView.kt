package com.qdhc.ny.popu

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.PopupWindow
import com.qdhc.ny.R
import com.qdhc.ny.bean.ScreenInfo
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.sj.core.utils.SharedPreferencesUtil

/**
 * 业绩筛选
 * @author shenjian
 * @date 2019/4/5
 */
class PopAchievementView(private val mContext: Activity, private val mOnClicListener: onClickLisiner) : PopupWindow() {
    private var mViewGroup: ViewGroup? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    init {
        //计算宽度和高度
        calWidthAndHeight(mContext)
        mViewGroup = LayoutInflater.from(mContext).inflate(
                R.layout.pop_achievement, null) as ViewGroup
        //设置SelectPicPopupWindow弹出窗体的宽
        this.width = mWidth
        //设置SelectPicPopupWindow弹出窗体的高
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        //设置SelectPicPopupWindow弹出窗体可点击
        this.isFocusable = true
        this.isOutsideTouchable = true
        mViewGroup!!.isFocusableInTouchMode = true
        //不添加drawable外侧不消失
        val dw = ColorDrawable(0x66000000)
        this.setBackgroundDrawable(dw)
        contentView = mViewGroup
        setTouchInterceptor { v, event ->
            //点击PopupWindow以外区域时PopupWindow消失
            if (event.action === MotionEvent.ACTION_OUTSIDE) {
                dismiss()
            }
            false
        }
        var viewClose = mViewGroup!!.findViewById<View>(R.id.view_close) as View
        viewClose.setOnClickListener {
            dismiss()
        }
        initDataView()
    }

    private fun initDataView() {
        var it = SharedPreferencesUtil.get(mContext, "userJson")
        var userInfo = SharedPreferencesUtils.loadLogin(mContext)
    }
    public fun getView(): ViewGroup? {
        return mViewGroup
    }

    interface onClickLisiner {
        fun onClick(mdata: ScreenInfo)
    }

    override fun showAsDropDown(anchor: View) {
        Log.e("tag", "sa1231231231")
        if (Build.VERSION.SDK_INT >= 24) {
            val rect = Rect()
            anchor.getGlobalVisibleRect(rect)
            val h = anchor.resources.displayMetrics.heightPixels - rect.bottom
            height = h
        }
        super.showAsDropDown(anchor)
    }

    /**
     * 设置PopupWindow的大小
     * @param context
     */
    private fun calWidthAndHeight(context: Context) {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.getDefaultDisplay().getMetrics(metrics)

        mWidth = metrics.widthPixels
        //设置高度为全屏高度的百分比
        mHeight = (metrics.heightPixels * 1.0).toInt()
    }

}
