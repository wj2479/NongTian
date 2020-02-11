package com.qdhc.ny.activity

import com.bumptech.glide.Glide
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.layout_title_theme.*

class ImageActivity : BaseActivity() {

    override fun intiLayout(): Int {
        return R.layout.activity_image
    }

    override fun initView() {
        title_tv_title.text = "照片详情"
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        var url = intent.getStringExtra("url")
        Glide.with(this).load(url).into(img)
    }
}
